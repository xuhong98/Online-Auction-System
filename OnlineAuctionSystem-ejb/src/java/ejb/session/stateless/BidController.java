package ejb.session.stateless;

import entity.AuctionListing;
import entity.Bid;
import entity.CreditTransaction;
import entity.Customer;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.enumeration.BidType;
import util.enumeration.TransactionType;
import util.exception.AuctionListingAlreadyClosedException;
import util.exception.AuctionListingNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;
import util.helperClass.SnipBidInfo;

/**
 *
 * @author yingshi
 */
@Stateless
@Local(BidControllerLocal.class)
@Remote(BidControllerRemote.class)
public class BidController implements BidControllerRemote, BidControllerLocal {

    @EJB
    private EmailControllerLocal emailControllerLocal;

    @EJB
    private CreditTransactionControllerLocal creditTransactionControllerLocal;

    @EJB
    private CustomerControllerLocal customerControllerLocal;

    @EJB
    private AuctionListingControllerLocal auctionListingControllerLocal;

    @Resource
    TimerService timerService;

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    //connect to customer. update in client ma?
    @Override
    public Bid createNewBid(Bid bid, Long auctionId, Long customerId) throws GeneralException, AuctionListingAlreadyClosedException {
        try {
            AuctionListing thisAuction = auctionListingControllerLocal.retrieveAuctionListingById(auctionId, true, false);

            em.persist(bid);

            thisAuction.getBiddings().add(bid);
            bid.setAuctionListing(thisAuction);

            Customer thisCustomer = customerControllerLocal.retrieveCustomerByCustomerId(customerId, true, false, false, false, false);

            thisCustomer.getBiddings().add(bid);
            bid.setCustomer(thisCustomer);

            em.flush();
            em.refresh(bid);

            return bid;
        } catch (PersistenceException | CustomerNotFoundException | AuctionListingNotFoundException ex) {
            throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
        }
    }

    //increment thisBid by "increment". create new transaction, deduct, all here
    @Override
    public void incrementBid(Long bidId, BigDecimal finalAmount, BigDecimal increment) throws GeneralException {

        Bid thisBid = em.find(Bid.class, bidId);

        thisBid.setBiddingAmount(finalAmount);

        AuctionListing auction = thisBid.getAuctionListing();
        Customer customer = thisBid.getCustomer();

        auction.setCurrentMaxBiddingPrice(finalAmount);

        Long secondLargest = auction.getMaxBidId();
        auction.setMaxBidId(bidId);

        CreditTransaction newTransaction = new CreditTransaction();
        newTransaction.setAmount(increment.negate());

        String description = "Deduct when placing advance " + thisBid.getBidtype() + " bid for [" + auction.getProductName() + "],[Bid_ID = " + thisBid.getBidId() + "]";

        newTransaction.setDescription(description);
        newTransaction.setTransactionTime(new Date());
        newTransaction.setTransactionType(TransactionType.PLACEBID);
        newTransaction = creditTransactionControllerLocal.createNewCreditTransaction(newTransaction, customer.getCustomerId());

        customer.setCreditBalance(customer.getCreditBalance().subtract(increment));
        em.flush();

        em.refresh(thisBid);
        configureProxyBid(thisBid, secondLargest);

    }

    //this method needs to be simplified
    private void configureProxyBid(Bid largestBid, Long secondLargest) {

        //no bid before that
        if (secondLargest.equals(0l)) {
            return;
        }

        Bid secondBid = em.find(Bid.class, secondLargest);

        if (secondBid.getBidtype().equals(BidType.PROXY)) {
            BigDecimal min = getMinBid(largestBid.getBiddingAmount());
            if (secondBid.getProxyAmount().compareTo(min) >= 0) {//this proxyBid can still increase
                try {
                    incrementBid(secondBid.getBidId(), min, min.subtract(secondBid.getBiddingAmount()));
                } catch (GeneralException ex) {
                    System.err.println("***" + ex.getMessage());
                }
            } else {
                try {//Email Notification:
                    emailControllerLocal.emailOutOfBidNotificationAsync(secondBid.getAuctionListing(), "yingshi@sunfire.comp.nus.edu.sg", secondBid.getCustomer(), secondBid);
                } catch (InterruptedException ex) {
                    System.err.println("***Email sending failed!");
                }
            }
        }
    }

    @Override
    public void createProxyBid(BigDecimal maxAmount, Long customerId, Long auctionId) throws GeneralException {
        try {
            AuctionListing auction = auctionListingControllerLocal.retrieveAuctionListingById(auctionId, Boolean.TRUE, Boolean.FALSE);

            BigDecimal min;
            if (auction.getBiddings().isEmpty()) {
                min = getMinBid(auction.getStartingPrice());
            } else {
                min = getMinBid(auction.getCurrentMaxBiddingPrice());
            }

            Customer customer = em.find(Customer.class, customerId);

            Boolean biddedBefore = false;

            for (Bid each : customer.getBiddings()) {
                if (each.getAuctionListing().equals(auction)) {//previously had bidded for this auction / no matter 

                    if (each.getBidId().equals(auction.getAuctionListingId())) {//and you are the current max bid
                        each.setProxyAmount(maxAmount);
                        each.setBidtype(BidType.PROXY);
                    } else {//you are not the current Max bid;
                        each.setProxyAmount(maxAmount);
                        each.setBidtype(BidType.PROXY);
                        incrementBid(each.getBidId(), min, min.subtract(each.getBiddingAmount()));
                    }
                    biddedBefore = true;
                }
            }

            if (!biddedBefore) {
                Bid newBid = new Bid(new Date(), min, BidType.PROXY);
                newBid.setProxyAmount(maxAmount);
                newBid = createNewBid(newBid, auctionId, customerId);
                incrementBid(newBid.getBidId(), min, min);
            }
        } catch (AuctionListingNotFoundException | GeneralException | AuctionListingAlreadyClosedException ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    @Override
    public void createSnippingBid(Long customerId, Long auctionId, BigDecimal maxAMount, Date expire) {
        SnipBidInfo newSnipBid = new SnipBidInfo(customerId, auctionId, maxAMount, expire);
        createTimer(newSnipBid);
    }

    private void createTimer(SnipBidInfo bid) {
        TimerConfig timerConfig = new TimerConfig(bid, true);
        timerService.createSingleActionTimer(bid.getExpireDate(), timerConfig);
    }

    @Timeout
    private void timeout(Timer timer) {
        try {
            SnipBidInfo snipBidInfo = (SnipBidInfo) timer.getInfo();

            AuctionListing auction = auctionListingControllerLocal.retrieveAuctionListingById(snipBidInfo.getAuctionListingId(), Boolean.TRUE, Boolean.FALSE);

            BigDecimal min = getMinBid(auction.getCurrentMaxBiddingPrice());

            if (snipBidInfo.getMaxAmount().compareTo(min) >= 0) {
                Long customerId = snipBidInfo.getCustomerId();
                Customer customer = em.find(Customer.class, customerId);
                Boolean biddedBefore = false;

                for (Bid each : customer.getBiddings()) {
                    if (each.getAuctionListing().equals(auction)) {//previously had bidded for this auction / no matter 
                        if (each.getBidId().equals(auction.getMaxBidId())) {//and you are the current max bid
                            //do nothing
                        } else {//you are not the current Max bid;
                            each.setBidtype(BidType.SNIPING);
                            incrementBid(each.getBidId(), snipBidInfo.getMaxAmount(), snipBidInfo.getMaxAmount().subtract(each.getBiddingAmount()));
                        }
                        biddedBefore = true;
                    }
                }

                if (!biddedBefore) {
                    Bid newBid = new Bid(new Date(), min, BidType.SNIPING);
                    newBid = createNewBid(newBid, snipBidInfo.getAuctionListingId(), snipBidInfo.getCustomerId());
                    incrementBid(newBid.getBidId(), snipBidInfo.getMaxAmount(), snipBidInfo.getMaxAmount());
                }
            } else {
                //outbid, don't place for him, but send email;
            }
        } catch (GeneralException | AuctionListingAlreadyClosedException | AuctionListingNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private BigDecimal getMinBid(BigDecimal currentPrice) {
        double priceDouble = currentPrice.doubleValue();

        if (priceDouble < 1) {
            return BigDecimal.valueOf(priceDouble + 0.05);
        }

        if (priceDouble < 5) {
            return BigDecimal.valueOf(priceDouble + 0.25);
        }

        if (priceDouble < 25) {
            return BigDecimal.valueOf(priceDouble + 0.5);
        }

        if (priceDouble < 100) {
            return BigDecimal.valueOf(priceDouble + 1);
        }

        if (priceDouble < 250) {
            return BigDecimal.valueOf(priceDouble + 2.5);
        }

        if (priceDouble < 500) {
            return BigDecimal.valueOf(priceDouble + 5);
        }

        if (priceDouble < 1000) {
            return BigDecimal.valueOf(priceDouble + 10);
        }

        if (priceDouble < 2500) {
            return BigDecimal.valueOf(priceDouble + 25);
        }

        if (priceDouble < 5000) {
            return BigDecimal.valueOf(priceDouble + 50);
        }

        return BigDecimal.valueOf(priceDouble + 100);
    }
}
