package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author yingshi
 */
@Entity
public class CreditPackage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditPackageId;

    @Column(nullable = false, precision = 18, scale = 0)
    private BigDecimal creditAmount;

    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(length = 32, nullable = false, unique = true)
    private String name;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal realMoney;

    @ManyToMany(mappedBy = "packagePurchased")
    private List<Customer> customers;

    public CreditPackage() {
        this.isEnabled = true;
    }

    public CreditPackage(BigDecimal creditAmount, String name, BigDecimal realMoney) {
        this.creditAmount = creditAmount;
        this.name = name;
        this.realMoney = realMoney;
        this.isEnabled = true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getCreditPackageId() != null ? getCreditPackageId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditPackage)) {
            return false;
        }
        CreditPackage other = (CreditPackage) object;
        if ((this.getCreditPackageId() == null && other.getCreditPackageId() != null) || (this.getCreditPackageId() != null && !this.creditPackageId.equals(other.creditPackageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CreditPackage[ id=" + getCreditPackageId() + " ]";
    }

    /**
     * @return the creditPackageId
     */
    public Long getCreditPackageId() {
        return creditPackageId;
    }

    /**
     * @param creditPackageId the creditPackageId to set
     */
    public void setCreditPackageId(Long creditPackageId) {
        this.creditPackageId = creditPackageId;
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

    /**
     * @return the creditAmount
     */
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    /**
     * @param creditAmount the creditAmount to set
     */
    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the realMoney
     */
    public BigDecimal getRealMoney() {
        return realMoney;
    }

    /**
     * @param realMoney the realMoney to set
     */
    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }

    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
