
package ws.auctionlistingWebService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.auctionlistingWebService package. 
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

    private final static QName _RemoteBrowseAllAuctionListings_QNAME = new QName("http://ws.session.ejb/", "remoteBrowseAllAuctionListings");
    private final static QName _RemoteViewAuctionListingDetailsResponse_QNAME = new QName("http://ws.session.ejb/", "remoteViewAuctionListingDetailsResponse");
    private final static QName _EmptyListException_QNAME = new QName("http://ws.session.ejb/", "EmptyListException");
    private final static QName _RemoteBrowseAllAuctionListingsResponse_QNAME = new QName("http://ws.session.ejb/", "remoteBrowseAllAuctionListingsResponse");
    private final static QName _RemoteBrowseWonAuctionListing_QNAME = new QName("http://ws.session.ejb/", "remoteBrowseWonAuctionListing");
    private final static QName _CustomerNotFoundException_QNAME = new QName("http://ws.session.ejb/", "CustomerNotFoundException");
    private final static QName _AuctionListingNotFoundException_QNAME = new QName("http://ws.session.ejb/", "AuctionListingNotFoundException");
    private final static QName _RemoteBrowseWonAuctionListingResponse_QNAME = new QName("http://ws.session.ejb/", "remoteBrowseWonAuctionListingResponse");
    private final static QName _RemoteViewAuctionListingDetails_QNAME = new QName("http://ws.session.ejb/", "remoteViewAuctionListingDetails");
    private final static QName _AuctionListingAlreadyClosedException_QNAME = new QName("http://ws.session.ejb/", "AuctionListingAlreadyClosedException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.auctionlistingWebService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RemoteBrowseWonAuctionListingResponse }
     * 
     */
    public RemoteBrowseWonAuctionListingResponse createRemoteBrowseWonAuctionListingResponse() {
        return new RemoteBrowseWonAuctionListingResponse();
    }

    /**
     * Create an instance of {@link RemoteViewAuctionListingDetails }
     * 
     */
    public RemoteViewAuctionListingDetails createRemoteViewAuctionListingDetails() {
        return new RemoteViewAuctionListingDetails();
    }

    /**
     * Create an instance of {@link AuctionListingAlreadyClosedException }
     * 
     */
    public AuctionListingAlreadyClosedException createAuctionListingAlreadyClosedException() {
        return new AuctionListingAlreadyClosedException();
    }

    /**
     * Create an instance of {@link RemoteBrowseAllAuctionListings }
     * 
     */
    public RemoteBrowseAllAuctionListings createRemoteBrowseAllAuctionListings() {
        return new RemoteBrowseAllAuctionListings();
    }

    /**
     * Create an instance of {@link RemoteViewAuctionListingDetailsResponse }
     * 
     */
    public RemoteViewAuctionListingDetailsResponse createRemoteViewAuctionListingDetailsResponse() {
        return new RemoteViewAuctionListingDetailsResponse();
    }

    /**
     * Create an instance of {@link EmptyListException }
     * 
     */
    public EmptyListException createEmptyListException() {
        return new EmptyListException();
    }

    /**
     * Create an instance of {@link RemoteBrowseAllAuctionListingsResponse }
     * 
     */
    public RemoteBrowseAllAuctionListingsResponse createRemoteBrowseAllAuctionListingsResponse() {
        return new RemoteBrowseAllAuctionListingsResponse();
    }

    /**
     * Create an instance of {@link RemoteBrowseWonAuctionListing }
     * 
     */
    public RemoteBrowseWonAuctionListing createRemoteBrowseWonAuctionListing() {
        return new RemoteBrowseWonAuctionListing();
    }

    /**
     * Create an instance of {@link CustomerNotFoundException }
     * 
     */
    public CustomerNotFoundException createCustomerNotFoundException() {
        return new CustomerNotFoundException();
    }

    /**
     * Create an instance of {@link AuctionListingNotFoundException }
     * 
     */
    public AuctionListingNotFoundException createAuctionListingNotFoundException() {
        return new AuctionListingNotFoundException();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteBrowseAllAuctionListings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "remoteBrowseAllAuctionListings")
    public JAXBElement<RemoteBrowseAllAuctionListings> createRemoteBrowseAllAuctionListings(RemoteBrowseAllAuctionListings value) {
        return new JAXBElement<RemoteBrowseAllAuctionListings>(_RemoteBrowseAllAuctionListings_QNAME, RemoteBrowseAllAuctionListings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteViewAuctionListingDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "remoteViewAuctionListingDetailsResponse")
    public JAXBElement<RemoteViewAuctionListingDetailsResponse> createRemoteViewAuctionListingDetailsResponse(RemoteViewAuctionListingDetailsResponse value) {
        return new JAXBElement<RemoteViewAuctionListingDetailsResponse>(_RemoteViewAuctionListingDetailsResponse_QNAME, RemoteViewAuctionListingDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmptyListException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "EmptyListException")
    public JAXBElement<EmptyListException> createEmptyListException(EmptyListException value) {
        return new JAXBElement<EmptyListException>(_EmptyListException_QNAME, EmptyListException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteBrowseAllAuctionListingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "remoteBrowseAllAuctionListingsResponse")
    public JAXBElement<RemoteBrowseAllAuctionListingsResponse> createRemoteBrowseAllAuctionListingsResponse(RemoteBrowseAllAuctionListingsResponse value) {
        return new JAXBElement<RemoteBrowseAllAuctionListingsResponse>(_RemoteBrowseAllAuctionListingsResponse_QNAME, RemoteBrowseAllAuctionListingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteBrowseWonAuctionListing }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "remoteBrowseWonAuctionListing")
    public JAXBElement<RemoteBrowseWonAuctionListing> createRemoteBrowseWonAuctionListing(RemoteBrowseWonAuctionListing value) {
        return new JAXBElement<RemoteBrowseWonAuctionListing>(_RemoteBrowseWonAuctionListing_QNAME, RemoteBrowseWonAuctionListing.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "CustomerNotFoundException")
    public JAXBElement<CustomerNotFoundException> createCustomerNotFoundException(CustomerNotFoundException value) {
        return new JAXBElement<CustomerNotFoundException>(_CustomerNotFoundException_QNAME, CustomerNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuctionListingNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "AuctionListingNotFoundException")
    public JAXBElement<AuctionListingNotFoundException> createAuctionListingNotFoundException(AuctionListingNotFoundException value) {
        return new JAXBElement<AuctionListingNotFoundException>(_AuctionListingNotFoundException_QNAME, AuctionListingNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteBrowseWonAuctionListingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "remoteBrowseWonAuctionListingResponse")
    public JAXBElement<RemoteBrowseWonAuctionListingResponse> createRemoteBrowseWonAuctionListingResponse(RemoteBrowseWonAuctionListingResponse value) {
        return new JAXBElement<RemoteBrowseWonAuctionListingResponse>(_RemoteBrowseWonAuctionListingResponse_QNAME, RemoteBrowseWonAuctionListingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteViewAuctionListingDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "remoteViewAuctionListingDetails")
    public JAXBElement<RemoteViewAuctionListingDetails> createRemoteViewAuctionListingDetails(RemoteViewAuctionListingDetails value) {
        return new JAXBElement<RemoteViewAuctionListingDetails>(_RemoteViewAuctionListingDetails_QNAME, RemoteViewAuctionListingDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuctionListingAlreadyClosedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "AuctionListingAlreadyClosedException")
    public JAXBElement<AuctionListingAlreadyClosedException> createAuctionListingAlreadyClosedException(AuctionListingAlreadyClosedException value) {
        return new JAXBElement<AuctionListingAlreadyClosedException>(_AuctionListingAlreadyClosedException_QNAME, AuctionListingAlreadyClosedException.class, null, value);
    }

}
