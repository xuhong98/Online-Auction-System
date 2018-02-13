
package ws.auctionlistingWebService;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "EmptyListException", targetNamespace = "http://ws.session.ejb/")
public class EmptyListException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private EmptyListException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public EmptyListException_Exception(String message, EmptyListException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public EmptyListException_Exception(String message, EmptyListException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ws.auctionlistingWebService.EmptyListException
     */
    public EmptyListException getFaultInfo() {
        return faultInfo;
    }

}
