package ejb.session.stateless;

import entity.CreditPackage;
import entity.CreditTransaction;
import entity.Customer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enumeration.TransactionType;
import util.exception.CreditPackageCannotDeleteException;
import util.exception.CreditPackageExistException;
import util.exception.CreditPackageNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.EmptyListException;
import util.exception.GeneralException;

/**
 *
 * @author mango
 */
@Stateless
@Local(CreditPackageControllerLocal.class)
@Remote(CreditPackageControllerRemote.class)
public class CreditPackageController implements CreditPackageControllerRemote, CreditPackageControllerLocal {

    @EJB
    private CreditTransactionControllerLocal creditTransactionControllerLocal;

    @EJB
    private CustomerControllerLocal customerControllerLocal;

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public CreditPackage createNewCreditPackage(CreditPackage creditPackage) throws CreditPackageExistException, GeneralException {
        try {
            em.persist(creditPackage);
            em.flush();
            em.refresh(creditPackage);

            return creditPackage;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CreditPackageExistException("Credit package with same name already exist");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    @Override
    public void updateCreditPackage(CreditPackage creditPackage) {
        em.merge(creditPackage);
    }

    @Override
    public CreditPackage retrieveCreditPackageByCreditPackageId(Long creditPackageId, Boolean fetchPurchasedBy) throws CreditPackageNotFoundException {
        CreditPackage creditPackage = em.find(CreditPackage.class, creditPackageId);

        if (creditPackage != null) {
            if (fetchPurchasedBy) {
                creditPackage.getCustomers().size();
            }
            return creditPackage;
        } else {
            throw new CreditPackageNotFoundException("Credit Package ID " + creditPackageId + " does not exist");
        }

    }

    @Override
    public List<CreditPackage> retrieveAllEnabledPackage() throws EmptyListException {
        Query query = em.createQuery("SELECT c FROM CreditPackage c");
        List<CreditPackage> packageList = query.getResultList();
        for (CreditPackage each : packageList) {
            if (each.getIsEnabled() == false) {
                packageList.remove(each);
            }
        }
        if (packageList.isEmpty()) {
            throw new EmptyListException("No Enabled Credit Package!");
        }
        return packageList;
    }

    @Override
    //for finance employee to refer to 
    public List<CreditPackage> retrieveAllCreditPackage() throws EmptyListException {
        Query query = em.createQuery("SELECT c FROM CreditPackage c");
        List<CreditPackage> packageList = query.getResultList();
        if (packageList.isEmpty()) {
            throw new EmptyListException("No Credit Package!");
        }

        return packageList;
    }

    @Override
    public void deleteCreditPackage(Long creditPackageId) throws CreditPackageCannotDeleteException {
        try {
            CreditPackage creditPackage = retrieveCreditPackageByCreditPackageId(creditPackageId, true);
            List<Customer> customers = creditPackage.getCustomers();
            if (customers.isEmpty()) {
                em.remove(creditPackage);
            } else {
                creditPackage.setIsEnabled(Boolean.FALSE);
                throw new CreditPackageCannotDeleteException("This Credit Package is being used at present, but is disabled now and cannot be used in the future!");
            }

        } catch (CreditPackageNotFoundException ex) {
        }
    }

    @Override
    public void purchaseCreditPackage(Long packageId, Long customerId, int units) throws GeneralException {
        try {
            CreditPackage creditPackage = retrieveCreditPackageByCreditPackageId(packageId, true);
            Customer customer = customerControllerLocal.retrieveCustomerByCustomerId(customerId, false, false, false, false, true);

            if (creditPackage.getCustomers().indexOf(customer) == -1) {
                creditPackage.getCustomers().add(customer);
                customer.getPackagePurchased().add(creditPackage);
            }

            Double total = creditPackage.getCreditAmount().doubleValue();
            total = total * units;
            BigDecimal totalBD = BigDecimal.valueOf(total);
            BigDecimal balanceNow = totalBD.add(customer.getCreditBalance());
            customer.setCreditBalance(balanceNow);//add to creditBalance;

            String description = "Purchased " + units + " '" + creditPackage.getName() + "', at " + creditPackage.getCreditAmount() + "/pack, Total: " + total + " credit(s).";
            CreditTransaction newTransaction = new CreditTransaction(totalBD, TransactionType.PURCHASE, new Date(), description);

            creditTransactionControllerLocal.createNewCreditTransaction(newTransaction, customerId);
        } catch (CreditPackageNotFoundException | CustomerNotFoundException | GeneralException ex) {
            throw new GeneralException("An unexpected error has occurred!" + ex.getMessage());
        }

    }

}
