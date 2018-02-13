package ejb.session.ws;

import ejb.session.stateless.CustomerControllerLocal;
import entity.Customer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author yingshi
 */
@WebService(serviceName = "CustomerWebservice")
@Stateless()
public class CustomerWebservice {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @EJB
    private CustomerControllerLocal customerControllerLocal;

    /**
     * Web service operation
     *
     * @param username
     * @param password
     * @return
     * @throws util.exception.InvalidLoginCredentialException
     */
    @WebMethod(operationName = "remoteLogin")
    public Customer remoteLogin(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws InvalidLoginCredentialException {
        Customer customer = customerControllerLocal.remoteLogin(username, password);
        
        em.detach(customer);
        em.clear();
        
        customer.setAddresses(null);
        customer.setBiddings(null);
        customer.setCreditTransactionHistory(null);
        customer.setPackagePurchased(null);
        customer.setWonAuctionListings(null);
        
        return customer;
    }

    /**
     * Web service operation
     *
     * @param customerId
     */
    @WebMethod(operationName = "setPremium")
    @Oneway
    public void setPremium(@WebParam(name = "customerId") Long customerId) {
        customerControllerLocal.setPremium(customerId);
    }


    /**
     * Web service operation
     * @param customerId
     * @return 
     * This method is for the use of update customer infomation,
     */
    @WebMethod(operationName = "updateCustomerInfo")
    public Customer updateCustomerInfo(@WebParam(name = "customerId") Long customerId) {
        try {
            Customer customer = customerControllerLocal.retrieveCustomerByCustomerId(customerId, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
            em.detach(customer);
            em.clear();

            customer.setAddresses(null);
            customer.setBiddings(null);
            customer.setCreditTransactionHistory(null);
            customer.setPackagePurchased(null);
            customer.setWonAuctionListings(null);

            return customer;
        } catch (CustomerNotFoundException ex) {
            return null;
        }
    }

    
    
}
