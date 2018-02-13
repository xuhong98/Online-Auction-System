
package util.exception;

/**
 *
 * @author yingshi
 */
public class AuctionListingAlreadyClosedException extends Exception {

    /**
     * Creates a new instance of
     * <code>AuctionListingAlreadyClosedException</code> without detail message.
     */
    public AuctionListingAlreadyClosedException() {
    }

    /**
     * Constructs an instance of
     * <code>AuctionListingAlreadyClosedException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public AuctionListingAlreadyClosedException(String msg) {
        super(msg);
    }
}
