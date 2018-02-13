package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enumeration.CustomerType;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PasswordChangeException;
import util.helperClass.SecurityHelper;

/**
 *
 * @author mango
 */
@Stateless
@Local(CustomerControllerLocal.class)
@Remote(CustomerControllerRemote.class)
public class CustomerController implements CustomerControllerRemote, CustomerControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public Customer createNewCustomer(Customer customer) throws CustomerExistException, GeneralException {
        try {
            SecurityHelper securityHelper = new SecurityHelper();
            customer.setPassword(securityHelper.generatePassword(customer.getPassword()));

            em.persist(customer);
            em.flush();
            em.refresh(customer);

            return customer;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CustomerExistException("Customer with same username or email already exist");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) throws CustomerExistException, GeneralException {
        try {
            return em.merge(customer);
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CustomerExistException("Customer with same username/phone number/email already exist");
            } else {
                throw new GeneralException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
    }

    @Override
    public Customer retrieveCustomerByCustomerId(Long customerId, Boolean fetchBiddedAuctionListing, Boolean fetchWonAuctionListing, Boolean fetchCreditTransactionHistory, Boolean fetchAddresses, Boolean fetchPackages) throws CustomerNotFoundException {

        Customer customer = em.find(Customer.class, customerId);

        if (customer != null) {
            //lazy fetching
            if (fetchBiddedAuctionListing) {
                customer.getBiddings().size();
            }
            if (fetchWonAuctionListing) {
                customer.getWonAuctionListings().size();
            }
            if (fetchCreditTransactionHistory) {
                customer.getCreditTransactionHistory().size();
            }
            if (fetchAddresses) {
                customer.getAddresses().size();
            }
            if (fetchPackages) {
                customer.getPackagePurchased().size();
            }

            return customer;
        } else {
            throw new CustomerNotFoundException("Customer ID " + customerId + " does not exist");
        }
    }

    @Override
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username = :username");
        query.setParameter("username", username);

        try {
            Customer customer = (Customer) query.getSingleResult();
            customer.getCreditTransactionHistory().size();
            customer.getAddresses().size();
            customer.getBiddings().size();
            customer.getWonAuctionListings().size();

            return customer;
        } catch (NoResultException ex) {
            throw new CustomerNotFoundException("Customer Username " + username + " does not exist!");
        }
    }

    @Override
    public void changePassword(Long customerId, String currentPassword, String newPassword) throws CustomerNotFoundException, PasswordChangeException {

        if (currentPassword.length() > 16 || currentPassword.length() < 6) {
            throw new PasswordChangeException("Password length must be in range [6.16]!");
        }

        Customer customer = retrieveCustomerByCustomerId(customerId, false, false, false, false, false);
        SecurityHelper securityHelper = new SecurityHelper();

        if (securityHelper.verifyPassword(currentPassword, customer.getPassword())) {
            customer.setPassword(securityHelper.generatePassword(newPassword));
        } else {
            throw new PasswordChangeException("Password change Failed: Current password is wrong");
        }
    }

    @Override
    public Customer customerLogIn(String username, String password) throws InvalidLoginCredentialException {
        try {
            SecurityHelper securityHelper = new SecurityHelper();
            Customer customer = retrieveCustomerByUsername(username);

            if (securityHelper.verifyPassword(password, customer.getPassword())) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Wrong password!");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist!");
        }

    }

    @Override
    public void setPremium(Long customerId) {
        try {
            Customer customer = retrieveCustomerByCustomerId(customerId, false, false, false, false, false);
            customer.setCustomerType(CustomerType.PREMIUM);
        } catch (CustomerNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public Customer remoteLogin(String username, String password) throws InvalidLoginCredentialException {//only exposed to Local yet
        try {
            SecurityHelper securityHelper = new SecurityHelper();
            Customer customer = retrieveCustomerByUsername(username);//the returned only have wonAuctionListing

            if (securityHelper.verifyPassword(password, customer.getPassword())) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Wrong password!");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist!");
        }

    }

}
