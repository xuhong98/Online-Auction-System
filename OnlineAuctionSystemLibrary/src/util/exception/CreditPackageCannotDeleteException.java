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
public class CreditPackageCannotDeleteException extends Exception {

    /**
     * Creates a new instance of <code>CreditPackageCannotDeleteException</code>
     * without detail message.
     */
    public CreditPackageCannotDeleteException() {
    }

    /**
     * Constructs an instance of <code>CreditPackageCannotDeleteException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreditPackageCannotDeleteException(String msg) {
        super(msg);
    }
}
