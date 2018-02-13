
package ws.auctionlistingWebService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for auctionListing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="auctionListing">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auctionListingId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="bidding" type="{http://ws.session.ejb/}bid" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="currentMaxBiddingPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="isShipped" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="maxBidId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="productDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reservePrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="shippedAddress" type="{http://ws.session.ejb/}address" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="startingPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="status" type="{http://ws.session.ejb/}auctionListingStatus" minOccurs="0"/>
 *         &lt;element name="winner" type="{http://ws.session.ejb/}customer" minOccurs="0"/>
 *         &lt;element name="winningprice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auctionListing", propOrder = {
    "auctionListingId",
    "bidding",
    "currentMaxBiddingPrice",
    "endTime",
    "isShipped",
    "maxBidId",
    "productDescription",
    "productName",
    "reservePrice",
    "shippedAddress",
    "startTime",
    "startingPrice",
    "status",
    "winner",
    "winningprice"
})
public class AuctionListing {

    protected Long auctionListingId;
    @XmlElement(nillable = true)
    protected List<Bid> bidding;
    protected BigDecimal currentMaxBiddingPrice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    protected Boolean isShipped;
    protected Long maxBidId;
    protected String productDescription;
    protected String productName;
    protected BigDecimal reservePrice;
    protected Address shippedAddress;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    protected BigDecimal startingPrice;
    protected AuctionListingStatus status;
    protected Customer winner;
    protected BigDecimal winningprice;

    /**
     * Gets the value of the auctionListingId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAuctionListingId() {
        return auctionListingId;
    }

    /**
     * Sets the value of the auctionListingId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAuctionListingId(Long value) {
        this.auctionListingId = value;
    }

    /**
     * Gets the value of the bidding property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bidding property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBidding().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bid }
     * 
     * 
     */
    public List<Bid> getBidding() {
        if (bidding == null) {
            bidding = new ArrayList<Bid>();
        }
        return this.bidding;
    }

    /**
     * Gets the value of the currentMaxBiddingPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurrentMaxBiddingPrice() {
        return currentMaxBiddingPrice;
    }

    /**
     * Sets the value of the currentMaxBiddingPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurrentMaxBiddingPrice(BigDecimal value) {
        this.currentMaxBiddingPrice = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the isShipped property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsShipped() {
        return isShipped;
    }

    /**
     * Sets the value of the isShipped property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsShipped(Boolean value) {
        this.isShipped = value;
    }

    /**
     * Gets the value of the maxBidId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaxBidId() {
        return maxBidId;
    }

    /**
     * Sets the value of the maxBidId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaxBidId(Long value) {
        this.maxBidId = value;
    }

    /**
     * Gets the value of the productDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the value of the productDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductDescription(String value) {
        this.productDescription = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * Gets the value of the reservePrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    /**
     * Sets the value of the reservePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReservePrice(BigDecimal value) {
        this.reservePrice = value;
    }

    /**
     * Gets the value of the shippedAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getShippedAddress() {
        return shippedAddress;
    }

    /**
     * Sets the value of the shippedAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setShippedAddress(Address value) {
        this.shippedAddress = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the startingPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    /**
     * Sets the value of the startingPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStartingPrice(BigDecimal value) {
        this.startingPrice = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AuctionListingStatus }
     *     
     */
    public AuctionListingStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuctionListingStatus }
     *     
     */
    public void setStatus(AuctionListingStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the winner property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getWinner() {
        return winner;
    }

    /**
     * Sets the value of the winner property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setWinner(Customer value) {
        this.winner = value;
    }

    /**
     * Gets the value of the winningprice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWinningprice() {
        return winningprice;
    }

    /**
     * Sets the value of the winningprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWinningprice(BigDecimal value) {
        this.winningprice = value;
    }

}
