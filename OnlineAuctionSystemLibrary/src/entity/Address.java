package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author yingshi
 */
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(length = 6, nullable = false)
    private String postcode;

    @Column(length = 64, nullable = false)
    private String addressLine1;

    @Column(length = 64)
    private String addressLine2;

    @Column(length = 32, nullable = false)
    private String city;

    @Column(length = 32, nullable = false)
    private String state;

    @Column(length = 32, nullable = false)
    private String country;

    @Column(nullable = false)
    private Boolean isEnabled;
    
    @ManyToOne(optional = false)
    private Customer customer;//didn't put the joint column because when customer delete the address(with assigned winning bid) we just set the address to diabled

    @OneToMany(mappedBy = "shippedAddress")
    private List<AuctionListing> shippedAuctions;

    public Address() {
        this.isEnabled = true;
    }

    public Address(String postcode, String addressLine1, String addressLine2, String city, String state, String country, Customer customer) {
        this.postcode = postcode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.customer = customer;
        this.isEnabled = true;
    }

    /**
     * @return the addressId
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1 the addressLine1 to set
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return the addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2 the addressLine2 to set
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getAddressId() != null ? getAddressId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.getAddressId() == null && other.getAddressId() != null) || (this.getAddressId() != null && !this.addressId.equals(other.addressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Address[ id=" + getAddressId() + " ]";
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the shippedAuctions
     */
    public List<AuctionListing> getShippedAuctions() {
        return shippedAuctions;
    }

    /**
     * @param shippedAuctions the shippedAuctions to set
     */
    public void setShippedAuctions(List<AuctionListing> shippedAuctions) {
        this.shippedAuctions = shippedAuctions;
    }

    /**
     * @return the isEnabled
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled the isEnabled to set
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
