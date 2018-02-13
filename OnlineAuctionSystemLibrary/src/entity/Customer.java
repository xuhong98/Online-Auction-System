package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import util.enumeration.CustomerType;

/**
 *
 * @author yingshi
 */
@Entity

public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(length = 32, nullable = false, unique = true)
    private String username;

    @Column(length = 130, nullable = false)

    private String password;

    @Column(length = 32, nullable = false)
    private String firstName;

    @Column(length = 32, nullable = false)
    private String lastName;

    @Column(length = 13, nullable = false)
    private String phoneNumber;

    @Column(length = 32, nullable = false, unique = true)
    private String email;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal creditBalance;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private List<CreditTransaction> creditTransactionHistory; //

    @OneToMany(mappedBy = "customer")
    private List<Bid> biddings;

    @ManyToMany
    private List<CreditPackage> packagePurchased; //must get associated? Or cannot detect if the package has someone to use it. But don't need to fetch anytime

    @OneToMany(mappedBy = "winner")
    private List<AuctionListing> wonAuctionListings;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @XmlElement
    private CustomerType customerType;

    public Customer() {

        this.customerType = CustomerType.NORMAL;
        this.creditBalance = BigDecimal.ZERO;
    }

    public Customer(String username, String password, String firstName, String lastName, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.customerType = CustomerType.NORMAL;
        this.creditBalance = BigDecimal.ZERO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getCustomerId() != null ? getCustomerId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.getCustomerId() == null && other.getCustomerId() != null) || (this.getCustomerId() != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + getCustomerId() + " ]";
    }

    /**
     * @return the customerId
     */
    //@XmlElement(name = "customerId")
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the creditBalance
     */
    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    /**
     * @param creditBalance the creditBalance to set
     */
    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
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
     * @return the wonAuctionListings
     */
    public List<AuctionListing> getWonAuctionListings() {
        return wonAuctionListings;
    }

    /**
     * @param wonAuctionListings the wonAuctionListings to set
     */
    public void setWonAuctionListings(List<AuctionListing> wonAuctionListings) {
        this.wonAuctionListings = wonAuctionListings;
    }

    /**
     * @return the customerType
     */
    //@XmlElement(name = "customerType")
    public Enum getCustomerType() {
        return customerType;
    }

    /**
     * @param customerType the customerType to set
     */
    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    /**
     * @return the creditTransactionHistory
     */
    public List<CreditTransaction> getCreditTransactionHistory() {
        return creditTransactionHistory;
    }

    /**
     * @param creditTransactionHistory the creditTransactionHistory to set
     */
    public void setCreditTransactionHistory(List<CreditTransaction> creditTransactionHistory) {
        this.creditTransactionHistory = creditTransactionHistory;
    }

    /**
     * @return the packagePurhased
     */
    public List<CreditPackage> getPackagePurchased() {
        return packagePurchased;
    }

    /**
     * @param packagePurhased the packagePurhased to set
     */
    public void setPackagePurchased(List<CreditPackage> packagePurhased) {
        this.packagePurchased = packagePurhased;
    }

    /**
     * @return the addresses
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

}
