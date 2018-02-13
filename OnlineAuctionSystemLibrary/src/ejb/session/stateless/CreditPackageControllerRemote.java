package ejb.session.stateless;

import entity.CreditPackage;
import java.util.List;
import util.exception.CreditPackageCannotDeleteException;
import util.exception.CreditPackageExistException;
import util.exception.CreditPackageNotFoundException;
import util.exception.EmptyListException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
public interface CreditPackageControllerRemote {

    public CreditPackage createNewCreditPackage(CreditPackage creditPackage) throws CreditPackageExistException, GeneralException;

    public void updateCreditPackage(CreditPackage creditPackage);

    public CreditPackage retrieveCreditPackageByCreditPackageId(Long creditPackageId, Boolean fetchPurchasedBy) throws CreditPackageNotFoundException;

    public void deleteCreditPackage(Long creditPackageId) throws CreditPackageCannotDeleteException;

    public void purchaseCreditPackage(Long packageId, Long customerId, int units) throws GeneralException;

    public List<CreditPackage> retrieveAllCreditPackage() throws EmptyListException;

    public List<CreditPackage> retrieveAllEnabledPackage() throws EmptyListException;

}
