/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author yingshi
 */
public class AuctionListingNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>AuctionListingNotFoundException</code>
     * without detail message.
     */
    public AuctionListingNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AuctionListingNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AuctionListingNotFoundException(String msg) {
        super(msg);
    }
}
