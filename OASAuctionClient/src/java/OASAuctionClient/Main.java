package OASAuctionClient;

import ejb.session.stateless.AddressControllerRemote;
import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.BidControllerRemote;
import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.CreditTransactionControllerRemote;
import ejb.session.stateless.CustomerControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author yingshi
 */
public class Main {

    @EJB
    private static AddressControllerRemote addressControllerRemote;

    @EJB
    private static AuctionListingControllerRemote auctionListingControllerRemote;

    @EJB
    private static BidControllerRemote bidControllerRemote;

    @EJB
    private static CreditPackageControllerRemote creditPackageControllerRemote;

    @EJB
    private static CreditTransactionControllerRemote creditTransactionControllerRemote;

    @EJB
    private static CustomerControllerRemote customerControllerRemote;

    public static void main(String[] args) {
        MainApp mainApp = new MainApp(auctionListingControllerRemote, bidControllerRemote, creditPackageControllerRemote, creditTransactionControllerRemote, customerControllerRemote, addressControllerRemote);
        mainApp.runApp();
    }

}
