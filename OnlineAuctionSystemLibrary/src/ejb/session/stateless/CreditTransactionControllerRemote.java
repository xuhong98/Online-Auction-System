
package ejb.session.stateless;

import entity.CreditTransaction;
import util.exception.GeneralException;


/**
 *
 * @author yingshi
 */
public interface CreditTransactionControllerRemote {

    CreditTransaction createNewCreditTransaction(CreditTransaction creditTransaction, Long customerId) throws GeneralException;
    
}
