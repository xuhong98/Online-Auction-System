package ejb.session.ws;

import ejb.session.stateless.AuctionListingControllerLocal;
import ejb.session.stateless.CustomerControllerLocal;
import entity.AuctionListing;
import entity.Customer;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AuctionListingStatus;
import util.exception.AuctionListingAlreadyClosedException;
import util.exception.AuctionListingNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.EmptyListException;

/**
 *
 * @author yingshi
 */
@WebService(serviceName = "AuctionListingWebservice")
@Stateless()
public class AuctionListingWebservice {

    @EJB
    private CustomerControllerLocal customerControllerLocal;

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @EJB
    private AuctionListingControllerLocal auctionListingControllerLocal;

    /**
     * Web service operation
     *
     * @param auctionId
     * @return
     * @throws util.exception.AuctionListingNotFoundException
     * @throws util.exception.AuctionListingAlreadyClosedException
     */
    @WebMethod(operationName = "remoteViewAuctionListingDetails")
    public AuctionListing remoteViewAuctionListingDetails(@WebParam(name = "auctionId") Long auctionId) throws AuctionListingNotFoundException, AuctionListingAlreadyClosedException {
        AuctionListing auctionListing = em.find(AuctionListing.class, auctionId);
        
        if (!auctionListing.getStatus().equals(AuctionListingStatus.ONGOING)) {//cannot show to customers
            throw new AuctionListingAlreadyClosedException("This auction listing is not available!");
        }
        
        em.detach(auctionListing);
        em.clear();

        auctionListing.setBiddings(null);
        
        return auctionListing;
    }

    /**
     * Web service operation
     *
     * @return
     * @throws util.exception.EmptyListException
     */
    @WebMethod(operationName = "remoteBrowseAllAuctionListings")
    public List<AuctionListing> remoteBrowseAllAuctionListings() throws EmptyListException {
        List<AuctionListing> list = auctionListingControllerLocal.retrieveOngoingAuction();
        for (AuctionListing each : list) {
            em.detach(each);
            each.setBiddings(null);
        }

        return list;
    }

    /**
     * Web service operation
     *
     * @param customerId
     * @return
     * @throws util.exception.CustomerNotFoundException
     */
    @WebMethod(operationName = "remoteBrowseWonAuctionListing")
    public List<AuctionListing> remoteBrowseWonAuctionListing(@WebParam(name = "customerId") Long customerId) throws CustomerNotFoundException {
        Customer customer = customerControllerLocal.retrieveCustomerByCustomerId(customerId, false, true, false, false, false);
        List<AuctionListing> list = customer.getWonAuctionListings();
        for (AuctionListing each : list) {
            em.detach(each);
            each.setWinner(null);
            each.setShippedAddress(null);
            each.setBiddings(null);
        }
        return list;
    }

}
