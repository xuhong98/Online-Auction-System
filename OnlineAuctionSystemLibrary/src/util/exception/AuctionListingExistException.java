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
public class AuctionListingExistException extends Exception {

    /**
     * Creates a new instance of <code>AuctionListingExistException</code>
     * without detail message.
     */
    public AuctionListingExistException() {
    }

    /**
     * Constructs an instance of <code>AuctionListingExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AuctionListingExistException(String msg) {
        super(msg);
    }
}
