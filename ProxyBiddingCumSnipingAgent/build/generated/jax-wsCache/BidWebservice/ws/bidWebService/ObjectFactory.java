
package ws.bidWebService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.bidWebService package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConfigureProxyBiddingResponse_QNAME = new QName("http://ws.session.ejb/", "configureProxyBiddingResponse");
    private final static QName _ConfigureSniping_QNAME = new QName("http://ws.session.ejb/", "configureSniping");
    private final static QName _GeneralException_QNAME = new QName("http://ws.session.ejb/", "GeneralException");
    private final static QName _ConfigureProxyBidding_QNAME = new QName("http://ws.session.ejb/", "configureProxyBidding");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.bidWebService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GeneralException }
     * 
     */
    public GeneralException createGeneralException() {
        return new GeneralException();
    }

    /**
     * Create an instance of {@link ConfigureProxyBidding }
     * 
     */
    public ConfigureProxyBidding createConfigureProxyBidding() {
        return new ConfigureProxyBidding();
    }

    /**
     * Create an instance of {@link ConfigureProxyBiddingResponse }
     * 
     */
    public ConfigureProxyBiddingResponse createConfigureProxyBiddingResponse() {
        return new ConfigureProxyBiddingResponse();
    }

    /**
     * Create an instance of {@link ConfigureSniping }
     * 
     */
    public ConfigureSniping createConfigureSniping() {
        return new ConfigureSniping();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigureProxyBiddingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "configureProxyBiddingResponse")
    public JAXBElement<ConfigureProxyBiddingResponse> createConfigureProxyBiddingResponse(ConfigureProxyBiddingResponse value) {
        return new JAXBElement<ConfigureProxyBiddingResponse>(_ConfigureProxyBiddingResponse_QNAME, ConfigureProxyBiddingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigureSniping }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "configureSniping")
    public JAXBElement<ConfigureSniping> createConfigureSniping(ConfigureSniping value) {
        return new JAXBElement<ConfigureSniping>(_ConfigureSniping_QNAME, ConfigureSniping.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneralException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "GeneralException")
    public JAXBElement<GeneralException> createGeneralException(GeneralException value) {
        return new JAXBElement<GeneralException>(_GeneralException_QNAME, GeneralException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigureProxyBidding }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "configureProxyBidding")
    public JAXBElement<ConfigureProxyBidding> createConfigureProxyBidding(ConfigureProxyBidding value) {
        return new JAXBElement<ConfigureProxyBidding>(_ConfigureProxyBidding_QNAME, ConfigureProxyBidding.class, null, value);
    }

}
