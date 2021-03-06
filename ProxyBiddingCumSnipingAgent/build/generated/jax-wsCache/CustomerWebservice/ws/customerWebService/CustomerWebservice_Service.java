
package ws.customerWebService;

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
@WebServiceClient(name = "CustomerWebservice", targetNamespace = "http://ws.session.ejb/", wsdlLocation = "http://localhost:8080/CustomerWebservice/CustomerWebservice?wsdl")
public class CustomerWebservice_Service
    extends Service
{

    private final static URL CUSTOMERWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException CUSTOMERWEBSERVICE_EXCEPTION;
    private final static QName CUSTOMERWEBSERVICE_QNAME = new QName("http://ws.session.ejb/", "CustomerWebservice");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/CustomerWebservice/CustomerWebservice?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CUSTOMERWEBSERVICE_WSDL_LOCATION = url;
        CUSTOMERWEBSERVICE_EXCEPTION = e;
    }

    public CustomerWebservice_Service() {
        super(__getWsdlLocation(), CUSTOMERWEBSERVICE_QNAME);
    }

    public CustomerWebservice_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), CUSTOMERWEBSERVICE_QNAME, features);
    }

    public CustomerWebservice_Service(URL wsdlLocation) {
        super(wsdlLocation, CUSTOMERWEBSERVICE_QNAME);
    }

    public CustomerWebservice_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CUSTOMERWEBSERVICE_QNAME, features);
    }

    public CustomerWebservice_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CustomerWebservice_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CustomerWebservice
     */
    @WebEndpoint(name = "CustomerWebservicePort")
    public CustomerWebservice getCustomerWebservicePort() {
        return super.getPort(new QName("http://ws.session.ejb/", "CustomerWebservicePort"), CustomerWebservice.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CustomerWebservice
     */
    @WebEndpoint(name = "CustomerWebservicePort")
    public CustomerWebservice getCustomerWebservicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.session.ejb/", "CustomerWebservicePort"), CustomerWebservice.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CUSTOMERWEBSERVICE_EXCEPTION!= null) {
            throw CUSTOMERWEBSERVICE_EXCEPTION;
        }
        return CUSTOMERWEBSERVICE_WSDL_LOCATION;
    }

}
