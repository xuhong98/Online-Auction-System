package util.helperClass;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author yingshi
 */
public class SnipBidInfo implements Serializable {

    private Long customerId;

    private Long auctionListingId;

    private BigDecimal maxAmount;

    private Date expireDate;

    public SnipBidInfo() {
    }

    public SnipBidInfo(Long customerId, Long auctionListingId, BigDecimal maxAmount, Date expireDate) {
        this.customerId = customerId;
        this.auctionListingId = auctionListingId;
        this.maxAmount = maxAmount;
        this.expireDate = expireDate;
    }

    /**
     * Get the value of expireDate
     *
     * @return the value of expireDate
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * Set the value of expireDate
     *
     * @param expireDate new value of expireDate
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * Get the value of maxAmount
     *
     * @return the value of maxAmount
     */
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    /**
     * Set the value of maxAmount
     *
     * @param maxAmount new value of maxAmount
     */
    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    /**
     * Get the value of auctionListingId
     *
     * @return the value of auctionListingId
     */
    public Long getAuctionListingId() {
        return auctionListingId;
    }

    /**
     * Set the value of auctionListingId
     *
     * @param auctionListingId new value of auctionListingId
     */
    public void setAuctionListingId(Long auctionListingId) {
        this.auctionListingId = auctionListingId;
    }

    /**
     * Get the value of customerId
     *
     * @return the value of customerId
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Set the value of customerId
     *
     * @param customerId new value of customerId
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

}
