package ejb.session.stateless;

import entity.CreditTransaction;
import entity.Customer;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
@Stateless
@Local(CreditTransactionControllerLocal.class)
@Remote(CreditTransactionControllerRemote.class)
public class CreditTransactionController implements CreditTransactionControllerRemote, CreditTransactionControllerLocal {

    @EJB
    private CustomerControllerLocal customerControllerLocal;

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public CreditTransaction createNewCreditTransaction(CreditTransaction creditTransaction, Long customerId) throws GeneralException {
        try {
            Customer customer = customerControllerLocal.retrieveCustomerByCustomerId(customerId, false, false, true, false, false);//all false
            em.persist(creditTransaction);

            creditTransaction.setCustomer(customer);
            customer.getCreditTransactionHistory().add(creditTransaction);

            em.flush();
            em.refresh(creditTransaction);
            em.refresh(customer);

            return creditTransaction;
        } catch (PersistenceException | CustomerNotFoundException ex) {
            throw new GeneralException("***An unexpected error has occured when creating new transaction***" + ex.getMessage());
        }

    }

}
