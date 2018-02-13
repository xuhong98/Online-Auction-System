package ejb.session.stateless;

import entity.AuctionListing;
import entity.Bid;
import entity.CreditTransaction;
import entity.Customer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AuctionListingNotFoundException;
import util.exception.GeneralException;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import util.enumeration.AuctionListingStatus;
import util.enumeration.BidType;
import util.enumeration.BiddingStatus;
import util.enumeration.TransactionType;
import util.exception.AuctionListingExistException;
import util.exception.EmptyListException;

/**
 *
 * @author yingshi
 */
@Stateless
@Local(AuctionListingControllerLocal.class)
@Remote(AuctionListingControllerRemote.class)
public class AuctionListingController implements AuctionListingControllerRemote, AuctionListingControllerLocal {

    @EJB
    private CreditTransactionControllerLocal creditTransactionControllerLocal;

    @EJB
    private EmailControllerLocal emailControllerLocal;

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Resource
    TimerService timerService;

    @Override
    public AuctionListing createNewAuctionListing(AuctionListing auctionListing) throws AuctionListingExistException, GeneralException {
        try {
            em.persist(auctionListing);
            em.flush();
            em.refresh(auctionListing);

            //start from now
            if (auctionListing.getStatus().equals(AuctionListingStatus.ONGOING)) {
                createTimer(auctionListing.getAuctionListingId(), auctionListing.getEndTime());
            } else {//start from a future time point
                if (auctionListing.getStatus().equals(AuctionListingStatus.INVISIBLE)) {
                    createTimer(auctionListing.getAuctionListingId(), auctionListing.getStartTime());
                }
            }
            return auctionListing;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new AuctionListingExistException("Auction Listing with same name already exist");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    //changeEndTime = true: made changes on date -> updateTimer; 
    //changeStartTime = true: made changes on date -> updateTimer;
    @Override
    public void updateAuctionListing(AuctionListing auctionListing, Boolean changeStartTime, Boolean changeEndTime) {
        if (changeStartTime) {
            updateTimer(auctionListing.getAuctionListingId(), auctionListing.getStartTime());
        }

        if (changeEndTime) {
            updateTimer(auctionListing.getAuctionListingId(), auctionListing.getEndTime());
        }

        em.merge(auctionListing);
    }

    @Override
    public AuctionListing retrieveAuctionListingById(Long id, Boolean fetchBids, Boolean fetchWinner) throws AuctionListingNotFoundException {
        AuctionListing auction = em.find(AuctionListing.class, id);
        if (auction != null) {
            if (fetchBids) {
                auction.getBiddings().size();
            }
            if (fetchWinner) {
                auction.getWinner();
            }
            return auction;
        } else {
            throw new AuctionListingNotFoundException("Auction Listing ID " + id + " does not exist!");
        }
    }

    @Override
    public List<AuctionListing> retrieveAllAuctionListings() throws EmptyListException {
        Query query = em.createQuery("SELECT a FROM AuctionListing a");
        List<AuctionListing> auctionListings = query.getResultList();
        if (auctionListings.isEmpty()) {
            throw new EmptyListException("No Auction Listing!");
        }
        return auctionListings;
    }

    @Override
    public void deleteAuctionListing(Long auctionListingId) throws AuctionListingNotFoundException {

        AuctionListing toDelete = em.find(AuctionListing.class, auctionListingId);
        List<Bid> biddings = toDelete.getBiddings();

        if (biddings.isEmpty()) {// if it's not used
            em.remove(toDelete);
        } else {//it's used, i.e. there're biddings associated with it
            for (Bid eachBid : biddings) {
                //change status
                eachBid.setStatus(BiddingStatus.CANCEL);

                //refund
                Customer refundee = eachBid.getCustomer();
                String description = "Refund when auction listing ID: " + auctionListingId + " is cancelled : " + eachBid.getBiddingAmount() + ", [Bid_ID = " + eachBid.getBidId() + "]";
                configureTransaction(refundee, eachBid.getBiddingAmount(), description);

            }
            toDelete.setStatus(AuctionListingStatus.CANCELLED);
            deleteTimer(auctionListingId);//delete corresponding timer

        }
    }

    @Override
    //retrieve the auctions that needs manual intervention(below/equal to reserve price)
    public List<AuctionListing> retrieveNeedManualAuction() throws EmptyListException {
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.status = util.enumeration.AuctionListingStatus.NEEDINTERVENED");
        List<AuctionListing> result = query.getResultList();

        if (!result.isEmpty()) {
            return result;
        } else {
            throw new EmptyListException("No Auction Listing needs Intervention");
        }
    }

    @Override
    public List<AuctionListing> retrieveOngoingAuction() throws EmptyListException {
        Query query = em.createQuery("SELECT a FROM AuctionListing a WHERE a.status = util.enumeration.AuctionListingStatus.ONGOING");
        List<AuctionListing> result = query.getResultList();

        if (!result.isEmpty()) {
            return result;
        } else {
            throw new EmptyListException("No Ongoing Auction Listing");
        }

    }

    @Override
    public void closeAuctionListingAutomate(Long id) {
        AuctionListing auctionToClose = em.find(AuctionListing.class, id);

        List<Bid> biddings = auctionToClose.getBiddings();

        if (biddings.isEmpty()) {//No Bid, directly change to Closed
            auctionToClose.setStatus(AuctionListingStatus.CLOSED);
        } else {

            if (auctionToClose.getCurrentMaxBiddingPrice().compareTo(auctionToClose.getReservePrice()) <= 0) {//less than or equal to reserve price
                auctionToClose.setStatus(AuctionListingStatus.NEEDINTERVENED);
            } else {//Automatic assign winning bid

                for (Bid eachBid : biddings) {

                    if (eachBid.getBidId().equals(auctionToClose.getMaxBidId())) {//This is the winning bid
                        //Assign Winning bid to this bid & Customer

                        auctionToClose.setWinningprice(eachBid.getBiddingAmount());
                        Customer winner = eachBid.getCustomer();
                        auctionToClose.setWinner(winner);
                        eachBid.setStatus(BiddingStatus.WON);// update bid
                        winner.getWonAuctionListings().add(auctionToClose);

                        if (!eachBid.getBidtype().equals(BidType.NORMAL)) {//proxy or sniping, charge service fee
                            BigDecimal serviceCharge = eachBid.getBiddingAmount().multiply(BigDecimal.valueOf(0.05));
                            String description = "Additional service fee when using Premium service winning " + eachBid.getBidtype() + " Bid.";
                            configureTransaction(winner, serviceCharge.negate(), description);
                        }

                        try {
                            //Email Notification to winner
                            emailControllerLocal.emailWonNotificationAsync(auctionToClose, "yingshi@sunfire.comp.nus.edu.sg", winner.getEmail());

                        } catch (InterruptedException ex) {
                            System.err.println("***Email sending failed!");
                        }
                    } else {//Assign Losing Bid, refund
                        Customer loser = eachBid.getCustomer();
                        BigDecimal bidAmount = eachBid.getBiddingAmount();
                        String description = "Refund when losing  bid for Product [" + auctionToClose.getProductName() + "] : " + bidAmount.toString() + ", [Bid_ID = " + eachBid.getBidId() + "]";
                        configureTransaction(loser, bidAmount, description);

                        try {
                            emailControllerLocal.emailOutOfBidNotificationAsync(auctionToClose, "yingshi@sunfire.comp.nus.edu.sg", loser, eachBid);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }

                        //Set Bid status;
                        eachBid.setStatus(BiddingStatus.LOST);
                    }
                }
                //Change AuctionListing Status;
                auctionToClose.setStatus(AuctionListingStatus.CLOSED);
            }

        }

    }

    //After assign winning bid manually by the employee
    //if parameter customerId == 0: don't assign any winning bid, then every bid will be refunded, else assign the highest bid
    @Override
    public void closeAuctionListingManually(Long auctionId, Long customerId) {
        AuctionListing auctionToClose = em.find(AuctionListing.class, auctionId);

        List<Bid> bidding = auctionToClose.getBiddings();
        for (Bid each : bidding) {
            if (each.getCustomer().getCustomerId().equals(customerId)) {//This is the winning bid
                //each: winning Bid
                auctionToClose.setWinningprice(each.getBiddingAmount());
                Customer winner = each.getCustomer();

                each.setStatus(BiddingStatus.WON);// update Each
                auctionToClose.setWinner(winner);
                winner.getWonAuctionListings().add(auctionToClose);

                if (!each.getBidtype().equals(BidType.NORMAL)) {//proxy or sniping, charge service fee
                    BigDecimal serviceCharge = each.getBiddingAmount().multiply(BigDecimal.valueOf(0.05));
                    String description = "Additional service fee when using Premium service winning " + each.getBidtype() + " Bid.";
                    configureTransaction(winner, serviceCharge.negate(), description);
                }
                
                try {
                    //Email Notification:
                    emailControllerLocal.emailWonNotificationAsync(auctionToClose, "yingshi@sunfire.comp.nus.edu.sg", winner.getEmail());
                } catch (InterruptedException ex) {
                    System.err.println("***Email sending failed!");
                }

            } else {//Assign Losing Bid, refund, add more transaction;
                Customer loser = each.getCustomer();
                BigDecimal bidAmount = each.getBiddingAmount();

                //Create Credit Transaction
                String description = "Refund when losing bid for [" + auctionToClose.getProductName() + "] : " + bidAmount.toString() + " credit(s), [Bid_ID = " + each.getBidId() + "]";
                each.setStatus(BiddingStatus.LOST);
                configureTransaction(loser, bidAmount, description);

                try {
                    emailControllerLocal.emailOutOfBidNotificationAsync(auctionToClose, loser.getEmail(), loser, each);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        //Assign AuctionListing Status;
        auctionToClose.setStatus(AuctionListingStatus.CLOSED);

    }

    //create programmatic timer
    private void createTimer(Long id, Date dateTime) {
        TimerConfig timerConfig = new TimerConfig(id, true);
        Timer timer = timerService.createSingleActionTimer(dateTime, timerConfig);
    }

    @Timeout
    private void timeOut(Timer timer) {
        System.err.println("******TimerOut: " + timer.getInfo() + "******");
        try {
            Long auctionId = (Long) timer.getInfo();
            AuctionListing auction = retrieveAuctionListingById(auctionId, false, false);

            //ONGOING timer timeout, prepare to close bid
            if (auction.getStatus().equals(AuctionListingStatus.ONGOING)) {
                closeAuctionListingAutomate(auctionId);
            } else {
                //INVISIBLE timer timeout, start a new timer
                if (auction.getStatus().equals(AuctionListingStatus.INVISIBLE)) {
                    auction.setStatus(AuctionListingStatus.ONGOING);
                    createTimer(auctionId, auction.getEndTime());
                }
            }
        } catch (AuctionListingNotFoundException ex) {
        }
    }

    private void updateTimer(Long auctionId, Date newDateTime) {
        Collection<Timer> timers = timerService.getTimers();
        for (Timer eachTimer : timers) {
            Long timerAuctionId = (Long) eachTimer.getInfo();
            if (timerAuctionId.equals(auctionId)) {
                eachTimer.cancel();
                createTimer(auctionId, newDateTime);
                break;
            }
        }
    }

    private void deleteTimer(Long auctionId) {
        Collection<Timer> timers = timerService.getTimers();
        for (Timer eachTimer : timers) {
            Long timerAuctionId = (Long) eachTimer.getInfo();
            if (timerAuctionId.equals(auctionId)) {
                eachTimer.cancel();
                break;
            }
        }
    }

    private void configureTransaction(Customer customer, BigDecimal amount, String description) {
        CreditTransaction transaction;
        if (amount.compareTo(BigDecimal.ZERO) > 0) {//it's a refund type(refund is a debit to credit balance)
            transaction = new CreditTransaction(amount, TransactionType.REFUND, new Date(), description);
        } else {//it's to charge service fee, 
            transaction = new CreditTransaction(amount, TransactionType.OTHERS, new Date(), description);

        }

        try {
            transaction = creditTransactionControllerLocal.createNewCreditTransaction(transaction, customer.getCustomerId());
            //amount
            customer.setCreditBalance(customer.getCreditBalance().add(amount));

            //AddTransaction
            customer.getCreditTransactionHistory().add(transaction);

        } catch (GeneralException ex) {
        }

    }

}
