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
public class EmptyListException extends Exception {

    /**
     * Creates a new instance of <code>EmptyListException</code> without detail
     * message.
     */
    public EmptyListException() {
    }

    /**
     * Constructs an instance of <code>EmptyListException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmptyListException(String msg) {
        super(msg);
    }
}
