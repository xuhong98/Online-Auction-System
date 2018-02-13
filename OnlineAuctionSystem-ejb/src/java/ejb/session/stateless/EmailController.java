package ejb.session.stateless;

import entity.AuctionListing;
import entity.Bid;
import entity.Customer;
import javax.ejb.AsyncResult;
import java.util.concurrent.Future;
import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import util.email.EmailManager;

/**
 *
 * @author yingshi
 */
@Stateless
@Local(EmailControllerLocal.class)
@Remote(EmailControllerRemote.class)

public class EmailController implements EmailControllerRemote, EmailControllerLocal {

    private EmailManager emailManager = new EmailManager("yingshi", "Llcnnzcxa52");

    @Asynchronous
    @Override
    public Future<Boolean> emailWonNotificationAsync(AuctionListing auctionListing, String fromEmailAddress, String toEmailAddress) throws InterruptedException {
        System.err.println("***Going to send emailWonNotification Async");
        Boolean result = emailManager.emailWonBiddingNotification(auctionListing, fromEmailAddress, toEmailAddress);
        return new AsyncResult<>(result);
    }

    @Asynchronous
    @Override
    public Future<Boolean> emailOutOfBidNotificationAsync(AuctionListing auctionListing, String fromEmailAddress, Customer customer, Bid myBid) throws InterruptedException {
        System.err.println("***Going to send emailOutOfBidNotification Async");
        Boolean result = emailManager.emailOutOfBidNotification(auctionListing, fromEmailAddress, customer, myBid);
        return new AsyncResult<>(result);
    }

}
