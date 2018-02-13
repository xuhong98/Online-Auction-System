package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import util.enumeration.BidType;
import util.enumeration.BiddingStatus;

/**
 *
 * @author mango
 */
@Entity
public class Bid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date placedTime;  //last managed time

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal biddingAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BiddingStatus status;

    @Column(precision = 18, scale = 2)
    private BigDecimal proxyAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BidType bidtype;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AuctionListing auctionListing;

    //construtor
    public Bid() {
        this.status = BiddingStatus.PENDING;
    }

    public Bid(Date placedTime, BigDecimal biddingAmount, BidType type) {
        this.placedTime = placedTime;
        this.biddingAmount = biddingAmount;
        this.bidtype = type;
        this.status = BiddingStatus.PENDING;
    }

    public Long getBidId() {
        return bidId;
    }

    public Date getPlacedTime() {
        return placedTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public AuctionListing getAuctionListing() {
        return auctionListing;
    }

    public BigDecimal getBiddingAmount() {
        return biddingAmount;
    }

    public void setPlacedTime(Date placedTime) {
        this.placedTime = placedTime;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAuctionListing(AuctionListing auctionListing) {
        this.auctionListing = auctionListing;
    }

    public void setBiddingAmount(BigDecimal biddingAmount) {
        this.biddingAmount = biddingAmount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getBidId() != null ? getBidId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        if ((this.getBidId() == null && other.getBidId() != null) || (this.getBidId() != null && !this.bidId.equals(other.bidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Bid[ id=" + getBidId() + " ]";
    }

    /**
     * @return the status
     */
    public BiddingStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(BiddingStatus status) {
        this.status = status;
    }

    /**
     * @param bidId the bidId to set
     */
    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    /**
     * @return the proxyAmount
     */
    public BigDecimal getProxyAmount() {
        return proxyAmount;
    }

    /**
     * @param proxyAmount the proxyAmount to set
     */
    public void setProxyAmount(BigDecimal proxyAmount) {
        this.proxyAmount = proxyAmount;
    }

    /**
     * @return the bidtype
     */
    public BidType getBidtype() {
        return bidtype;
    }

    /**
     * @param bidtype the bidtype to set
     */
    public void setBidtype(BidType bidtype) {
        this.bidtype = bidtype;
    }

}
