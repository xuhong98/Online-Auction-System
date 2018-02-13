
package ws.customerWebService;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for bid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auctionListing" type="{http://ws.session.ejb/}auctionListing" minOccurs="0"/>
 *         &lt;element name="bidId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="biddingAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="bidtype" type="{http://ws.session.ejb/}bidType" minOccurs="0"/>
 *         &lt;element name="customer" type="{http://ws.session.ejb/}customer" minOccurs="0"/>
 *         &lt;element name="placedTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="proxyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="status" type="{http://ws.session.ejb/}biddingStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bid", propOrder = {
    "auctionListing",
    "bidId",
    "biddingAmount",
    "bidtype",
    "customer",
    "placedTime",
    "proxyAmount",
    "status"
})
public class Bid {

    protected AuctionListing auctionListing;
    protected Long bidId;
    protected BigDecimal biddingAmount;
    protected BidType bidtype;
    protected Customer customer;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar placedTime;
    protected BigDecimal proxyAmount;
    protected BiddingStatus status;

    /**
     * Gets the value of the auctionListing property.
     * 
     * @return
     *     possible object is
     *     {@link AuctionListing }
     *     
     */
    public AuctionListing getAuctionListing() {
        return auctionListing;
    }

    /**
     * Sets the value of the auctionListing property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuctionListing }
     *     
     */
    public void setAuctionListing(AuctionListing value) {
        this.auctionListing = value;
    }

    /**
     * Gets the value of the bidId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBidId() {
        return bidId;
    }

    /**
     * Sets the value of the bidId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBidId(Long value) {
        this.bidId = value;
    }

    /**
     * Gets the value of the biddingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBiddingAmount() {
        return biddingAmount;
    }

    /**
     * Sets the value of the biddingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBiddingAmount(BigDecimal value) {
        this.biddingAmount = value;
    }

    /**
     * Gets the value of the bidtype property.
     * 
     * @return
     *     possible object is
     *     {@link BidType }
     *     
     */
    public BidType getBidtype() {
        return bidtype;
    }

    /**
     * Sets the value of the bidtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link BidType }
     *     
     */
    public void setBidtype(BidType value) {
        this.bidtype = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setCustomer(Customer value) {
        this.customer = value;
    }

    /**
     * Gets the value of the placedTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlacedTime() {
        return placedTime;
    }

    /**
     * Sets the value of the placedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlacedTime(XMLGregorianCalendar value) {
        this.placedTime = value;
    }

    /**
     * Gets the value of the proxyAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProxyAmount() {
        return proxyAmount;
    }

    /**
     * Sets the value of the proxyAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProxyAmount(BigDecimal value) {
        this.proxyAmount = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link BiddingStatus }
     *     
     */
    public BiddingStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiddingStatus }
     *     
     */
    public void setStatus(BiddingStatus value) {
        this.status = value;
    }

}
