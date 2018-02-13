
package ejb.session.stateless;

import entity.Address;
import util.exception.AddressNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */

public interface AddressControllerRemote {
    
    public Address createNewAddress(Address address, Long customerId) throws GeneralException;

    public Address retrieveAddressById(Long id) throws AddressNotFoundException;

    public void updateAddress(Address address);

    public void deleteAddress(Long addressId) throws GeneralException;

    public void assignAddress(Long addressId, Long auctionId);

}
