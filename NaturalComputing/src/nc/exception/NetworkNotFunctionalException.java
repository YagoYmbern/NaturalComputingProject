/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.exception;

/**
 *
 * @author Oscar Lao
 */
public class NetworkNotFunctionalException extends Exception{
    
    /**
     * Generate an activation exception of the free neural network
     * @param message 
     */
    public NetworkNotFunctionalException(String message)
    {
        super(message);
    }    
}

