package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlElement;
import util.enumeration.AuctionListingStatus;

/**
 *
 * @author yingshi
 */
@Entity
public class AuctionListing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement
    private Long auctionListingId;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuctionListingStatus status;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal reservePrice;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal startingPrice;

    @Column(precision = 18, scale = 2)
    private BigDecimal winningprice;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal currentMaxBiddingPrice;

    @Column(length = 64, nullable = false, unique = true)
    private String productName;

    @Column(length = 128, nullable = false)
    private String productDescription;

    private Boolean isShipped;

    @Column(nullable = false)
    private Long maxBidId;

    @ManyToOne
    private Address shippedAddress;

    @ManyToOne
    private Customer winner;

    @OneToMany(mappedBy = "auctionListing")
    private List<Bid> biddings;

    //default, currentMaxBiddingPrice = 0, maxBidId=0;isShipped = false;
    public AuctionListing() {
        this.isShipped = false;
        this.maxBidId = 0l;
        this.currentMaxBiddingPrice = BigDecimal.ZERO;
    }

    public AuctionListing(Date startTime, Date endTime, AuctionListingStatus status, BigDecimal reservePrice, BigDecimal startingPrice, String productName, String productDescription) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.reservePrice = reservePrice;
        this.startingPrice = startingPrice;
        this.productName = productName;
        this.productDescription = productDescription;
        this.currentMaxBiddingPrice = BigDecimal.ZERO;
        this.isShipped = false;
        this.maxBidId = 0l;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getAuctionListingId() != null ? getAuctionListingId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionListing)) {
            return false;
        }
        AuctionListing other = (AuctionListing) object;
        if ((this.getAuctionListingId() == null && other.getAuctionListingId() != null) || (this.getAuctionListingId() != null && !this.auctionListingId.equals(other.auctionListingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AuctionListing[ id=" + getAuctionListingId() + " ]";
    }

    /**
     * @return the auctionListingId
     */
    public Long getAuctionListingId() {
        return auctionListingId;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the status
     */
    public AuctionListingStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(AuctionListingStatus status) {
        this.status = status;
    }

    /**
     * @return the reservePrice
     */
    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    /**
     * @param reservePrice the reservePrice to set
     */
    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    /**
     * @return the startingPrice
     */
    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    /**
     * @param startingPrice the startingPrice to set
     */
    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    /**
     * @return the winningprice
     */
    public BigDecimal getWinningprice() {
        return winningprice;
    }

    /**
     * @param winningprice the winningprice to set
     */
    public void setWinningprice(BigDecimal winningprice) {
        this.winningprice = winningprice;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDescription the productDescription to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * @return the biddings
     */
    public List<Bid> getBiddings() {
        return biddings;
    }

    /**
     * @param biddings the biddings to set
     */
    public void setBiddings(List<Bid> biddings) {
        this.biddings = biddings;
    }

    /**
     * @return the winner
     */
    public Customer getWinner() {
        return winner;
    }

    /**
     * @param winner the winner to set
     */
    public void setWinner(Customer winner) {
        this.winner = winner;
    }

    /**
     * @return the currentMaxBiddingPrice
     */
    public BigDecimal getCurrentMaxBiddingPrice() {
        return currentMaxBiddingPrice;
    }

    /**
     * @param currentMaxBiddingPrice the currentMaxBiddingPrice to set
     */
    public void setCurrentMaxBiddingPrice(BigDecimal currentMaxBiddingPrice) {
        this.currentMaxBiddingPrice = currentMaxBiddingPrice;
    }

    /**
     * @return the isShipped
     */
    public Boolean getIsShipped() {
        return isShipped;
    }

    /**
     * @param isShipped the isShipped to set
     */
    public void setIsShipped(Boolean isShipped) {
        this.isShipped = isShipped;
    }

    /**
     * @return the shippedAddress
     */
    public Address getShippedAddress() {
        return shippedAddress;
    }

    /**
     * @param shippedAddress the shippedAddress to set
     */
    public void setShippedAddress(Address shippedAddress) {
        this.shippedAddress = shippedAddress;
    }

    /**
     * @return the maxBidId
     */
    public Long getMaxBidId() {
        return maxBidId;
    }

    /**
     * @param maxBidId the maxBidId to set
     */
    public void setMaxBidId(Long maxBidId) {
        this.maxBidId = maxBidId;
    }

}
