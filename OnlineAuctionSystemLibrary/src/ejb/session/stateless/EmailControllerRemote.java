package ejb.session.stateless;

import entity.AuctionListing;
import entity.Bid;
import entity.Customer;
import java.util.concurrent.Future;

public interface EmailControllerRemote {

    Future<Boolean> emailWonNotificationAsync(AuctionListing auctionListing, String fromEmailAddress, String toEmailAddress) throws InterruptedException;

    Future<Boolean> emailOutOfBidNotificationAsync(AuctionListing auctionListing, String fromEmailAddress, Customer customer, Bid mybid) throws InterruptedException;

}
