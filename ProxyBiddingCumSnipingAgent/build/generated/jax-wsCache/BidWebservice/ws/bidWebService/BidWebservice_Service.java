
package ws.bidWebService;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "BidWebservice", targetNamespace = "http://ws.session.ejb/", wsdlLocation = "http://localhost:8080/BidWebservice/BidWebservice?wsdl")
public class BidWebservice_Service
    extends Service
{

    private final static URL BIDWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException BIDWEBSERVICE_EXCEPTION;
    private final static QName BIDWEBSERVICE_QNAME = new QName("http://ws.session.ejb/", "BidWebservice");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/BidWebservice/BidWebservice?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BIDWEBSERVICE_WSDL_LOCATION = url;
        BIDWEBSERVICE_EXCEPTION = e;
    }

    public BidWebservice_Service() {
        super(__getWsdlLocation(), BIDWEBSERVICE_QNAME);
    }

    public BidWebservice_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), BIDWEBSERVICE_QNAME, features);
    }

    public BidWebservice_Service(URL wsdlLocation) {
        super(wsdlLocation, BIDWEBSERVICE_QNAME);
    }

    public BidWebservice_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BIDWEBSERVICE_QNAME, features);
    }

    public BidWebservice_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BidWebservice_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns BidWebservice
     */
    @WebEndpoint(name = "BidWebservicePort")
    public BidWebservice getBidWebservicePort() {
        return super.getPort(new QName("http://ws.session.ejb/", "BidWebservicePort"), BidWebservice.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BidWebservice
     */
    @WebEndpoint(name = "BidWebservicePort")
    public BidWebservice getBidWebservicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.session.ejb/", "BidWebservicePort"), BidWebservice.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BIDWEBSERVICE_EXCEPTION!= null) {
            throw BIDWEBSERVICE_EXCEPTION;
        }
        return BIDWEBSERVICE_WSDL_LOCATION;
    }

}
