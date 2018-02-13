package ejb.session.stateless;

import entity.AuctionListing;
import java.util.List;
import util.exception.AuctionListingExistException;
import util.exception.AuctionListingNotFoundException;
import util.exception.EmptyListException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
public interface AuctionListingControllerRemote {

    public AuctionListing createNewAuctionListing(AuctionListing auctionListing) throws GeneralException, AuctionListingExistException;

    public AuctionListing retrieveAuctionListingById(Long id, Boolean fetchBids, Boolean fetchWinner) throws AuctionListingNotFoundException;

    public List<AuctionListing> retrieveAllAuctionListings() throws EmptyListException;

    public void deleteAuctionListing(Long auctionListingId) throws AuctionListingNotFoundException;

    public List<AuctionListing> retrieveNeedManualAuction() throws EmptyListException;

    public void updateAuctionListing(AuctionListing auctionListing, Boolean changeStartTime, Boolean changeEndTime);

    public void closeAuctionListingAutomate(Long id);

    public void closeAuctionListingManually(Long auctionId, Long customerId);

    public List<AuctionListing> retrieveOngoingAuction() throws EmptyListException;

}
