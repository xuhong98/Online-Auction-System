package OASAdminPanel;

import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.EmployeeControllerRemote;
import entity.Employee;
import java.util.InputMismatchException;
import java.util.Scanner;
import util.enumeration.EmployeeType;
import util.exception.EmployeeNotFoundException;
import util.exception.PasswordChangeException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author mango
 */
public class MainApp {

    private EmployeeControllerRemote employeeControllerRemote;
    private CreditPackageControllerRemote creditPackageControllerRemote;
    private AuctionListingControllerRemote auctionListingControllerRemote;

    private SystemAdminMoudle systemAdminMoudle;
    private FinanceMoudle financeMoudle;
    private SalesMoudle salesMoudle;

    private Employee employee;

    //constructor
    public MainApp() {

    }

    public MainApp(EmployeeControllerRemote employeeControllerRemote, CreditPackageControllerRemote creditPackageControllerRemote, AuctionListingControllerRemote auctionListingControllerRemote) {
        this.employeeControllerRemote = employeeControllerRemote;
        this.creditPackageControllerRemote = creditPackageControllerRemote;
        this.auctionListingControllerRemote = auctionListingControllerRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** Welcome to Online Auction System (OAS) :: Admin Panel ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;

            while (response < 1 || response > 2) {
                System.out.print("> ");

                try {
                    response = scanner.nextInt();

                    if (response == 1) {
                        doLogin();
                        systemAdminMoudle = new SystemAdminMoudle(employeeControllerRemote, employee.getEmployeeId());
                        financeMoudle = new FinanceMoudle(creditPackageControllerRemote);
                        salesMoudle = new SalesMoudle(auctionListingControllerRemote);
                        menuMain();
                    } else if (response == 2) {
                        break;
                    } else {
                        System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31m\nInput Error! Please enter a valid number!\033[34m");
                    scanner.nextLine();
                } catch (InvalidLoginCredentialException ex) {
                    System.out.println("\033[31m\n[Warning] " + ex.getMessage() + "\033[34m");
                    System.out.print("Press Enter to try again...> ");
                    scanner.nextLine();
                    scanner.nextLine().trim();
                }
            }

            if (response == 2) {
                break;
            }
        }
    }

    public void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n*** OAS Admin Panel :: Login ***\n");
        System.out.print("Enter username> ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        String password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            employee = employeeControllerRemote.employeeLogin(username, password);
            System.out.println("\n[System] Login sucessfully!");
        } else {
            throw new InvalidLoginCredentialException("Empty Input Field!");
        }

    }

    public void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS Admin Panel ***\n");
            System.out.println("\033[34mYou are logged in as [" + employee.getUsername() + "] as " + employee.getEmployeeType().toString() + " Staff.\n\033[0m");
            System.out.println("1: Change Password");
            System.out.println("2: Go to " + employee.getEmployeeType().toString() + " panel");
            System.out.println("3: Logout\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");
                try {
                    response = scanner.nextInt();

                    if (response == 1) {
                        changePassword();
                    } else if (response == 2) {
                        if (employee.getEmployeeType() == EmployeeType.SYSTEMADMIN) {
                            systemAdminMoudle.menuSystemAdmin();
                        } else if (employee.getEmployeeType() == EmployeeType.FINANCE) {
                            financeMoudle.menuFinance();
                        } else if (employee.getEmployeeType() == EmployeeType.SALES) {
                            salesMoudle.menuSales();
                        }
                    } else if (response == 3) {
                        break;
                    } else {
                        System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31m\nInput Error! Please enter a valid number!\033[34m");
                    scanner.nextLine();
                }
            }

            if (response == 3) {
                break;
            }
        }

    }

    public void changePassword() {
        System.out.println("\n*** OAS Admin Panel :: Change Password  ***\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter current password> ");
        String currentPassword = scanner.nextLine().trim();

        System.out.print("Please enter new password> ");
        String newPassword = scanner.nextLine().trim();
        System.out.print("Please confirm new password> ");
        String ReEnterNewPassword = scanner.nextLine().trim();

        if (newPassword.equals(ReEnterNewPassword)) {
            try {
                employeeControllerRemote.changePassword(employee.getEmployeeId(), currentPassword, newPassword);
                System.out.println("\n[System] Password has been successfully changed for employee: " + employee.getFirstName() + " " + employee.getLastName() + "!\n");
                System.out.print("Press Enter to continue...> ");
                scanner.nextLine();
            } catch (PasswordChangeException ex) {
                System.out.println("\033[31m\n[Warning] " + ex.getMessage() + "\033[34m");
                System.out.print("\nPress Enter to continue...> ");
                scanner.nextLine();
            } catch (EmployeeNotFoundException ex) {
                //won't happen, because this employee must exist
            }
        } else {
            System.out.println("\033[31m\n[Warning]Two input did not match! Please input again!\033[34m");
            System.out.print("\nPress Enter to continue...> ");
            scanner.nextLine();
        }
    }
}
