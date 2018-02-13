
package ws.auctionlistingWebService;

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
@WebServiceClient(name = "AuctionListingWebservice", targetNamespace = "http://ws.session.ejb/", wsdlLocation = "http://localhost:8080/AuctionListingWebservice/AuctionListingWebservice?wsdl")
public class AuctionListingWebservice_Service
    extends Service
{

    private final static URL AUCTIONLISTINGWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException AUCTIONLISTINGWEBSERVICE_EXCEPTION;
    private final static QName AUCTIONLISTINGWEBSERVICE_QNAME = new QName("http://ws.session.ejb/", "AuctionListingWebservice");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/AuctionListingWebservice/AuctionListingWebservice?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AUCTIONLISTINGWEBSERVICE_WSDL_LOCATION = url;
        AUCTIONLISTINGWEBSERVICE_EXCEPTION = e;
    }

    public AuctionListingWebservice_Service() {
        super(__getWsdlLocation(), AUCTIONLISTINGWEBSERVICE_QNAME);
    }

    public AuctionListingWebservice_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUCTIONLISTINGWEBSERVICE_QNAME, features);
    }

    public AuctionListingWebservice_Service(URL wsdlLocation) {
        super(wsdlLocation, AUCTIONLISTINGWEBSERVICE_QNAME);
    }

    public AuctionListingWebservice_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUCTIONLISTINGWEBSERVICE_QNAME, features);
    }

    public AuctionListingWebservice_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuctionListingWebservice_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AuctionListingWebservice
     */
    @WebEndpoint(name = "AuctionListingWebservicePort")
    public AuctionListingWebservice getAuctionListingWebservicePort() {
        return super.getPort(new QName("http://ws.session.ejb/", "AuctionListingWebservicePort"), AuctionListingWebservice.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuctionListingWebservice
     */
    @WebEndpoint(name = "AuctionListingWebservicePort")
    public AuctionListingWebservice getAuctionListingWebservicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.session.ejb/", "AuctionListingWebservicePort"), AuctionListingWebservice.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUCTIONLISTINGWEBSERVICE_EXCEPTION!= null) {
            throw AUCTIONLISTINGWEBSERVICE_EXCEPTION;
        }
        return AUCTIONLISTINGWEBSERVICE_WSDL_LOCATION;
    }

}