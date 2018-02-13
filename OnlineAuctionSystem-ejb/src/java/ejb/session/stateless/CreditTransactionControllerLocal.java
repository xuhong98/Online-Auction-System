
package ejb.session.stateless;

import entity.CreditTransaction;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
public interface CreditTransactionControllerLocal {

    CreditTransaction createNewCreditTransaction(CreditTransaction creditTransaction, Long customerId) throws GeneralException;
    
}
