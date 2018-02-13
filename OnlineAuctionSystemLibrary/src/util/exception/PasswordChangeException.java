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
public class PasswordChangeException extends Exception{
    public PasswordChangeException(){
        
    }
    
    public PasswordChangeException(String msg){
        super(msg);
    }
            
}
