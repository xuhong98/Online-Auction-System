package ejb.session.stateless;

import entity.Customer;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PasswordChangeException;

/**
 *
 * @author mango
 */
public interface CustomerControllerLocal {

    public Customer createNewCustomer(Customer customer) throws CustomerExistException, GeneralException;

    public Customer updateCustomer(Customer customer) throws CustomerExistException, GeneralException;

    public void changePassword(Long customerId, String currentPassword, String newPassword) throws CustomerNotFoundException, PasswordChangeException;

    public Customer customerLogIn(String userName, String password) throws InvalidLoginCredentialException, CustomerNotFoundException;

    public Customer retrieveCustomerByCustomerId(Long customerId, Boolean fetchBiddedAuctionListing, Boolean fetchWonAuctionListing, Boolean fetchCreditTransactionHistory, Boolean fetchAddresses, Boolean fetchPackages) throws CustomerNotFoundException;

    public Customer retrieveCustomerByUsername(String userNamesting) throws CustomerNotFoundException;

    public void setPremium(Long customerId);

    public Customer remoteLogin(String username, String password) throws InvalidLoginCredentialException;

}
