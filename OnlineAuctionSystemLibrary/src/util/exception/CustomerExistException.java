/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author mango
 */
public class CustomerExistException extends Exception
{
    public CustomerExistException()
    {
    }
    
    
    
    public CustomerExistException(String msg)
    {
        super(msg);
    }
}