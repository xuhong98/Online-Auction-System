package ejb.session.stateless;

import entity.Bid;
import java.math.BigDecimal;
import java.util.Date;
import util.exception.AuctionListingAlreadyClosedException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
public interface BidControllerRemote {

    public Bid createNewBid(Bid bid, Long auctionId, Long customerId) throws GeneralException, AuctionListingAlreadyClosedException;

    public void incrementBid(Long bidId, BigDecimal finalAmount, BigDecimal increment) throws GeneralException;

    public void createProxyBid(BigDecimal maxAmount, Long customerId, Long auctionId) throws GeneralException;

    public void createSnippingBid(Long customerId, Long auctionId, BigDecimal maxAMount, Date expire);

}
