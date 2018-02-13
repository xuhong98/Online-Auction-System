
package ws.customerWebService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.customerWebService package. 
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

    private final static QName _UpdateCustomerInfo_QNAME = new QName("http://ws.session.ejb/", "updateCustomerInfo");
    private final static QName _UpdateCustomerInfoResponse_QNAME = new QName("http://ws.session.ejb/", "updateCustomerInfoResponse");
    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://ws.session.ejb/", "InvalidLoginCredentialException");
    private final static QName _RemoteLoginResponse_QNAME = new QName("http://ws.session.ejb/", "remoteLoginResponse");
    private final static QName _RemoteLogin_QNAME = new QName("http://ws.session.ejb/", "remoteLogin");
    private final static QName _SetPremium_QNAME = new QName("http://ws.session.ejb/", "setPremium");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.customerWebService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link RemoteLoginResponse }
     * 
     */
    public RemoteLoginResponse createRemoteLoginResponse() {
        return new RemoteLoginResponse();
    }

    /**
     * Create an instance of {@link RemoteLogin }
     * 
     */
    public RemoteLogin createRemoteLogin() {
        return new RemoteLogin();
    }

    /**
     * Create an instance of {@link SetPremium }
     * 
     */
    public SetPremium createSetPremium() {
        return new SetPremium();
    }

    /**
     * Create an instance of {@link UpdateCustomerInfo }
     * 
     */
    public UpdateCustomerInfo createUpdateCustomerInfo() {
        return new UpdateCustomerInfo();
    }

    /**
     * Create an instance of {@link UpdateCustomerInfoResponse }
     * 
     */
    public UpdateCustomerInfoResponse createUpdateCustomerInfoResponse() {
        return new UpdateCustomerInfoResponse();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link CreditPackage }
     * 
     */
    public CreditPackage createCreditPackage() {
        return new CreditPackage();
    }

    /**
     * Create an instance of {@link CreditTransaction }
     * 
     */
    public CreditTransaction createCreditTransaction() {
        return new CreditTransaction();
    }

    /**
     * Create an instance of {@link AuctionListing }
     * 
     */
    public AuctionListing createAuctionListing() {
        return new AuctionListing();
    }

    /**
     * Create an instance of {@link Bid }
     * 
     */
    public Bid createBid() {
        return new Bid();
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomerInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "updateCustomerInfo")
    public JAXBElement<UpdateCustomerInfo> createUpdateCustomerInfo(UpdateCustomerInfo value) {
        return new JAXBElement<UpdateCustomerInfo>(_UpdateCustomerInfo_QNAME, UpdateCustomerInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomerInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "updateCustomerInfoResponse")
    public JAXBElement<UpdateCustomerInfoResponse> createUpdateCustomerInfoResponse(UpdateCustomerInfoResponse value) {
        return new JAXBElement<UpdateCustomerInfoResponse>(_UpdateCustomerInfoResponse_QNAME, UpdateCustomerInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InvalidLoginCredentialException")
    public JAXBElement<InvalidLoginCredentialException> createInvalidLoginCredentialException(InvalidLoginCredentialException value) {
        return new JAXBElement<InvalidLoginCredentialException>(_InvalidLoginCredentialException_QNAME, InvalidLoginCredentialException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "remoteLoginResponse")
    public JAXBElement<RemoteLoginResponse> createRemoteLoginResponse(RemoteLoginResponse value) {
        return new JAXBElement<RemoteLoginResponse>(_RemoteLoginResponse_QNAME, RemoteLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "remoteLogin")
    public JAXBElement<RemoteLogin> createRemoteLogin(RemoteLogin value) {
        return new JAXBElement<RemoteLogin>(_RemoteLogin_QNAME, RemoteLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPremium }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "setPremium")
    public JAXBElement<SetPremium> createSetPremium(SetPremium value) {
        return new JAXBElement<SetPremium>(_SetPremium_QNAME, SetPremium.class, null, value);
    }

}
