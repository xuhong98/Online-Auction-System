
package util.exception;

/**
 *
 * @author yingshi
 */
public class InsufficientCreditBalanceException extends Exception {

    /**
     * Creates a new instance of <code>InsufficientCreditBalanceException</code>
     * without detail message.
     */
    public InsufficientCreditBalanceException() {
    }

    /**
     * Constructs an instance of <code>InsufficientCreditBalanceException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InsufficientCreditBalanceException(String msg) {
        super(msg);
    }
}
