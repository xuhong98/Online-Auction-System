package ejb.session.stateless;

import entity.AuctionListing;
import entity.Bid;
import entity.Customer;
import java.util.concurrent.Future;

public interface EmailControllerLocal {

    Future<Boolean> emailWonNotificationAsync(AuctionListing auctionListing, String fromEmailAddress, String toEmailAddress) throws InterruptedException;

    Future<Boolean> emailOutOfBidNotificationAsync(AuctionListing auctionListing, String fromEmailAddress, Customer customer, Bid myBid) throws InterruptedException;

}
