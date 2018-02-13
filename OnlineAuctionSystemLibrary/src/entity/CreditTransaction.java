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
import util.enumeration.TransactionType;

/**
 *
 * @author yingshi
 */
@Entity
public class CreditTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditTransactionId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date transactionTime;

    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;

    public CreditTransaction() {
    }

    public CreditTransaction(BigDecimal amount, TransactionType transactionType, Date transactionTime, String description) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getCreditTransactionId() != null ? getCreditTransactionId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditTransaction)) {
            return false;
        }
        CreditTransaction other = (CreditTransaction) object;
        if ((this.getCreditTransactionId() == null && other.getCreditTransactionId() != null) || (this.getCreditTransactionId() != null && !this.creditTransactionId.equals(other.creditTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CreditTransaction[ id=" + getCreditTransactionId() + " ]";
    }

    /**
     * @return the creditTransactionId
     */
    public Long getCreditTransactionId() {
        return creditTransactionId;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the transactionType
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return the transactionTime
     */
    public Date getTransactionTime() {
        return transactionTime;
    }

    /**
     * @param transactionTime the transactionTime to set
     */
    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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

}
