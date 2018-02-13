package ProxyBiddingCumSnipingAgent;

import java.util.InputMismatchException;
import java.util.Scanner;
import ws.customerWebService.Customer;
import ws.customerWebService.CustomerType;
import ws.customerWebService.InvalidLoginCredentialException_Exception;

/**
 *
 * @author mango
 */
public class MainApp {

    private PBCS proxyBiddingCumSnippingMenu;

    public MainApp() {
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n*** Welcome to CrazyBids.com - Proxy Bidding cum Sniping Agent ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            Integer response = 0;

            while (response < 1 || response > 2) {
                System.out.print("\033[0m> \033[34m");

                try {
                    response = scanner.nextInt();

                    if (response == 1) {
                        try {
                            doLogin();
                            break;
                        } catch (InvalidLoginCredentialException_Exception ex) {
                            System.out.println("\033[31m\n[Warning] " + ex.getMessage() + "\033[34m");
                        }
                    } else if (response == 2) {
                        break;
                    } else {
                        System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31m\nInput Error! Please enter a valid number!\033[34m");
                    scanner.nextLine();
                }
            }

            if (response == 2) {
                break;
            }

        }
        System.out.println("\033[34m\n--Thank you--");
        System.out.println("\033[34m--Powered by AI.SG--");
    }

    private void doLogin() throws InvalidLoginCredentialException_Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Proxy Bidding cum Snipping Agent :: Customer Login ***\n");
        System.out.print("Enter Username> ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password> ");
        String password = scanner.nextLine().trim();
        username = username.toLowerCase();

        proxyBiddingCumSnippingMenu = new PBCS();

        Customer customer = remoteLogin(username, password);
        System.out.println("\n[System] Login succesfully as [" + customer.getUsername() + "] !");

        //if not premium:
        if (customer.getCustomerType() == CustomerType.NORMAL) {
            System.out.println("\033[0m\n[System] You are not premium cutsomer, enter 'Y' to do Premium registration");
            System.out.print("> \033[34m");
            String answer = scanner.nextLine().trim();
            Boolean registerPremium = false;
            if (answer.equals("Y")) {
                registerPremium = doPremiumRegistration(customer.getCustomerId());
            }

            if (registerPremium) {
                System.out.println("\n[System] Successfully registerd as Premium Customer!");
                System.out.print("\nPress enter to continue...>");
                scanner.nextLine();
                proxyBiddingCumSnippingMenu = new PBCS(customer);
                proxyBiddingCumSnippingMenu.menuMain();
            } else {
                System.out.println("\033[31m\n[Warning] Premium registration failed!\033[34m");
                System.out.print("Press enter to continue...>");
                scanner.nextLine();
            }
        } //premium customer
        else {
            proxyBiddingCumSnippingMenu = new PBCS(customer);
            proxyBiddingCumSnippingMenu.menuMain();
        }
    }

    private Boolean doPremiumRegistration(Long customerId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Proxy Bidding cum Snipping Agent :: Premium Registration ***\n");
        System.out.println("By registering the premium type, you will pay additional \033[31m5%\033[30m of the winning bid price as service fee.");
        System.out.print("Enter 'Y' to confirm registration> ");
        String answer = scanner.nextLine().trim();
        if (answer.equals("Y")) {
            setPremium(customerId);
            return true;
        } else {
            return false;
        }
    }

    private static Customer remoteLogin(java.lang.String username, java.lang.String password) throws InvalidLoginCredentialException_Exception {
        ws.customerWebService.CustomerWebservice_Service service = new ws.customerWebService.CustomerWebservice_Service();
        ws.customerWebService.CustomerWebservice port = service.getCustomerWebservicePort();
        return port.remoteLogin(username, password);
    }

    private static void setPremium(java.lang.Long customerId) {
        ws.customerWebService.CustomerWebservice_Service service = new ws.customerWebService.CustomerWebservice_Service();
        ws.customerWebService.CustomerWebservice port = service.getCustomerWebservicePort();
        port.setPremium(customerId);
    }

}
