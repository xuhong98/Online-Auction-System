package OASAuctionClient;

import ejb.session.stateless.AddressControllerRemote;
import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.BidControllerRemote;
import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.CreditTransactionControllerRemote;
import ejb.session.stateless.CustomerControllerRemote;
import entity.Customer;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import util.enumeration.CustomerType;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author yingshi
 */
public class MainApp {

    private AuctionListingControllerRemote auctionListingControllerRemote;
    private BidControllerRemote bidControllerRemote;
    private CreditPackageControllerRemote creditPackageControllerRemote;
    private CreditTransactionControllerRemote creditTransactionControllerRemote;
    private CustomerControllerRemote customerControllerRemote;
    private AddressControllerRemote addressControllerRemote;

    private ProfileModule profileModule;
    private AddressModule addressModule;
    private AuctionListingModule auctionListingModule;
    private CreditModule creditModule;

    private Customer currentCustomer;

    public MainApp() {
    }

    public MainApp(AuctionListingControllerRemote auctionListingControllerRemote, BidControllerRemote bidControllerRemote, CreditPackageControllerRemote creditPackageControllerRemote, CreditTransactionControllerRemote creditTransactionControllerRemote, CustomerControllerRemote customerControllerRemote, AddressControllerRemote addressControllerRemote) {
        this.auctionListingControllerRemote = auctionListingControllerRemote;
        this.bidControllerRemote = bidControllerRemote;
        this.creditPackageControllerRemote = creditPackageControllerRemote;
        this.creditTransactionControllerRemote = creditTransactionControllerRemote;
        this.customerControllerRemote = customerControllerRemote;
        this.addressControllerRemote = addressControllerRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n*** Welcome to CrazyBids.com - OAS Auction Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: New User Register");
            System.out.println("3: Exit\n");
            Integer response = 0;

            while (response < 1 || response > 3) {
                try {
                    response = 0;
                    System.out.print("\033[0m> \033[34m");
                    response = scanner.nextInt();

                    if (response == 1) {
                        try {
                            doLogIn();

                            profileModule = new ProfileModule(customerControllerRemote);
                            creditModule = new CreditModule(customerControllerRemote, creditPackageControllerRemote);
                            addressModule = new AddressModule(addressControllerRemote, customerControllerRemote);
                            auctionListingModule = new AuctionListingModule(customerControllerRemote, auctionListingControllerRemote, bidControllerRemote, creditTransactionControllerRemote, addressControllerRemote);

                            System.out.println("\n[System] Log In Successful! ");
                            menuMain();
                        } catch (InvalidLoginCredentialException ex) {
                            scanner.nextLine();
                            System.out.println("\033[31m\n[Warning] Login Failed! " + ex.getMessage() + "\033[34m");
                            System.out.print("\nPress Enter to return...> ");
                            scanner.nextLine().trim();

                        }
                    } else if (response == 2) {
                        doRegistration();
                    } else if (response == 3) {
                        break;
                    } else {
                        System.out.println("\033[31mInvalid option, please try again!\033[34m");
                    }

                } catch (InputMismatchException ex) {
                    System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
                    scanner.next();
                }
            }
            if (response == 3) {
                break;
            }
        }

        System.out.println("\033[34m\n-- Have a nice day :) --");
        System.out.println("\033[34m-- CrazyBid.com :: Online Auction System --");
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS - Auction Client ***\n");
            System.out.println("\033[34mYou are logged in! Welcome [" + currentCustomer.getUsername() + "] !\n\033[0m");

            System.out.println("1: Manage Profile");
            System.out.println("2: Manage Addresses");
            System.out.println("3: Manage Credits");
            System.out.println("4: Manage Auction Listings");
            System.out.println("5: Logout\n");

            response = 0;

            OUTER:
            while (response < 1 || response > 5) {
                try {
                    System.out.print("> ");

                    response = scanner.nextInt();

                    switch (response) {
                        case 1:
                            profileModule.mainMenu(currentCustomer);
                            break;
                        case 2:
                            addressModule.mainMenu(currentCustomer);
                            break;
                        case 3:
                            creditModule.mainMenu(currentCustomer);
                            break;
                        case 4:
                            auctionListingModule.mainMenu(currentCustomer);
                            break;
                        case 5:
                            break OUTER;
                        default:
                            System.out.println("\033[31m Invalid Instruction Number!\033[34m");
                            break;
                    }
                    currentCustomer = customerControllerRemote.retrieveCustomerByCustomerId(currentCustomer.getCustomerId(), true, true, true, true, true);
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31m Input Error! Please enter a valid number!\033[34m");
                    scanner.next();
                } catch (CustomerNotFoundException ex) {
                    //wont happed, because customer must exist
                }
            }

            if (response == 5) {
                break;
            }

        }

    }

    private void doLogIn() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("\n*** OAS - Auction Client :: Customer Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        username = username.toLowerCase();

        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentCustomer = customerControllerRemote.customerLogIn(username, password);
        } else {
            throw new InvalidLoginCredentialException("Empty username or password!");
        }
    }

    private void doRegistration() {
        Scanner scanner = new Scanner(System.in);

        try {
            Customer newCustomer = new Customer();
            System.out.println("\n*** OAS - Auction Client :: Create Customer ***\n");
            String input;

            while (true) {
                System.out.print("Enter First Name> ");
                input = scanner.nextLine().trim();
                if (input.length() > 0) {
                    newCustomer.setFirstName(input);
                    break;
                } else {
                    System.out.println("\033[31mEmpty Field!\033[34m");
                }
            }

            while (true) {
                System.out.print("Enter Last Name> ");
                input = scanner.nextLine().trim();
                if (input.length() > 0) {
                    newCustomer.setLastName(input);
                    break;
                } else {
                    System.out.println("\033[31mEmpty Field!\033[34m");
                }
            }

            while (true) {
                System.out.print("Enter Email> ");
                input = scanner.nextLine().trim();
                if (input.length() > 0) {
                    if (input.contains("@")) {
                        newCustomer.setEmail(input);
                        break;
                    } else {
                        System.out.println("Invalid Email Address!");
                    }

                } else {
                    System.out.println("\033[31mEmpty Field!\033[34m");
                }
            }

            while (true) {

                System.out.print("Enter Phone Number> ");
                input = scanner.nextLine().trim();
                if (input.length() > 0) {
                    newCustomer.setPhoneNumber(input);
                    break;
                } else {
                    System.out.println("\033[31mEmpty Field!\033[34m");
                }
            }

            while (true) {
                System.out.print("Enter Username> ");
                input = scanner.nextLine().trim();
                if (input.length() > 0) {
                    input = input.toLowerCase();
                    newCustomer.setUsername(input);
                    break;
                } else {
                    System.out.println("\033[31mEmpty Field!\033[34m");
                }
            }

            while (true) {
                System.out.print("Enter Password([6,16] characters)> ");
                String pass1 = scanner.nextLine().trim();

                System.out.print("Confirm Password> ");
                String pass2 = scanner.nextLine().trim();

                if (pass1.length() > 16 || pass1.length() < 6) {
                    System.out.println("\033[31m[Error] Password length should be in range [6,16]\033[34m");
                    continue;
                }
                if (pass1.equals(pass2)) {
                    newCustomer.setPassword(pass1);
                    break;
                } else {
                    System.out.println("\033[31m[Warning] Two input did not match! Please input again!\033[34m");
                }
            }

            //initialize credit balance and customer type
            newCustomer.setCreditBalance(BigDecimal.ZERO);
            newCustomer.setCustomerType(CustomerType.NORMAL);
            newCustomer = customerControllerRemote.createNewCustomer(newCustomer);
            System.out.println("\n[System] New customer created successfully! ID: " + newCustomer.getCustomerId() + ", username: " + newCustomer.getUsername());

        } catch (CustomerExistException | GeneralException ex) {
            System.out.println("\033[31m\n[Warning] An error has occurred while creating the new customer: " + ex.getMessage() + "!\033[34m");
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();
        }

    }

}
