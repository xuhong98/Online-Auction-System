package ejb.session.stateless;

import entity.Address;
import entity.AuctionListing;
import entity.Customer;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.AddressNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
@Stateless
@Local(AddressControllerLocal.class)
@Remote(AddressControllerRemote.class)
public class AddressController implements AddressControllerRemote, AddressControllerLocal {

    @EJB
    private CustomerControllerLocal customerControllerLocal;

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @Override
    public Address createNewAddress(Address address, Long customerId) throws GeneralException {
        try {
            Customer customer = customerControllerLocal.retrieveCustomerByCustomerId(customerId, false, false, false, true, false);
            em.persist(address);

            customer.getAddresses().add(address);
            address.setCustomer(customer);

            em.flush();
            em.refresh(address);

            return address;
        } catch (PersistenceException | CustomerNotFoundException ex) {
            throw new GeneralException("An unexpected Error has occurred when creating new address");
        }
    }

    @Override
    public Address retrieveAddressById(Long id) throws AddressNotFoundException {

        Address address = em.find(Address.class, id);
        if (address != null) {
            address.getCustomer();
            address.getShippedAuctions().size();
            return address;
        } else {
            throw new AddressNotFoundException("Error: Address Not Found! Please check your input ID!");
        }
    }

    @Override
    public void updateAddress(Address address) {
        em.merge(address);
    }

    @Override
    public void deleteAddress(Long addressId) throws GeneralException {
        try {
            Address address = retrieveAddressById(addressId);
            if (address.getShippedAuctions().isEmpty()) {
                address.getCustomer().getAddresses().remove(address);
                address.setCustomer(null);
                em.remove(address);
            } else {
                address.getCustomer().getAddresses().remove(address);
                address.setIsEnabled(Boolean.FALSE);
            }
        } catch (AddressNotFoundException | PersistenceException ex) {
            throw new GeneralException("An unexpected Error has occurred when deleting new address!");
        }

    }

    @Override
    //Assign address to won auction listing
    public void assignAddress(Long addressId, Long auctionId) {
        try {
            Address address = retrieveAddressById(addressId);
            AuctionListing ac = em.find(AuctionListing.class, auctionId);

            address.getShippedAuctions().add(ac);
            ac.setShippedAddress(address);
            ac.setIsShipped(Boolean.TRUE);
        } catch (AddressNotFoundException ex) {
        }

    }

}
