
package OASAdminPanel;

import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.EmployeeControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author mango
 */
public class Main {

    @EJB
    private static EmployeeControllerRemote employeeControllerRemote;


    @EJB
    private static CreditPackageControllerRemote creditPackageControllerRemote;


    @EJB
    private static AuctionListingControllerRemote auctionListingControllerRemote;
    

    public static void main(String[] args) {
        MainApp mainApp = new MainApp(employeeControllerRemote, creditPackageControllerRemote, auctionListingControllerRemote);
        mainApp.runApp();
    }
    
}
