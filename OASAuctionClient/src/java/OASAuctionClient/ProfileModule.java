package OASAuctionClient;

import ejb.session.stateless.CustomerControllerRemote;
import entity.Customer;
import java.util.InputMismatchException;
import java.util.Scanner;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;
import util.exception.PasswordChangeException;

/**
 *
 * @author yingshi
 */
public class ProfileModule {

    CustomerControllerRemote customerControllerRemote;
    Customer currentCustomer;

    public ProfileModule() {
    }

    public ProfileModule(CustomerControllerRemote customerControllerRemote) {
        this.customerControllerRemote = customerControllerRemote;
    }

    public void mainMenu(Customer customer) {
        currentCustomer = customer;
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS - Auction Client :: Profile Module ***\n");
            System.out.println("1: View Profile");
            System.out.println("2: Update Profile");
            System.out.println("3: Go Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 3) {
                try {
                    System.out.print("> ");

                    response = scanner.nextInt();

                    switch (response) {
                        case 1:
                            doViewProfile();
                            break;
                        case 2:
                            doUpdateProfile();
                            break;
                        case 3:
                            break OUTER;
                        default:
                            System.out.println("\033[31mInvalid Instruction!\033[34m");
                            break;
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
    }

    private void doViewProfile() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n*** OAS - Auction Client :: View Profile ***\n");

        String leftAlignFormat = "| %-20s | %-30s |%n";

        System.out.println("|------------ Customer Details  ------------------------|");

        System.out.format("+----------------------+--------------------------------+%n");
        System.out.format("| Column               |Profile                         |%n");
        System.out.format("+----------------------+--------------------------------+%n");

        System.out.printf(leftAlignFormat, "Customer Id ", currentCustomer.getCustomerId());
        System.out.printf(leftAlignFormat, "Username ", currentCustomer.getUsername());
        System.out.printf(leftAlignFormat, "First Name ", currentCustomer.getFirstName());
        System.out.printf(leftAlignFormat, "Last Name ", currentCustomer.getLastName());
        System.out.printf(leftAlignFormat, "Email ", currentCustomer.getEmail());
        System.out.printf(leftAlignFormat, "Phone Number ", currentCustomer.getPhoneNumber());
        System.out.printf(leftAlignFormat, "Credit Balance ", currentCustomer.getCreditBalance());
        System.out.printf(leftAlignFormat, "Account Type ", currentCustomer.getCustomerType());

        System.out.format("+----------------------+--------------------------------+%n\n");

        System.out.print("Press Enter to return...> ");
        scanner.nextLine().trim();
    }

    private void doUpdateProfile() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS - Auction Client :: Update Profile ***\n");
            System.out.println("1: Change Password");
            System.out.println("2: Change Other Information");
            System.out.println("3: Go Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 3) {
                System.out.print("> \033[34m");
                try {
                    response = scanner.nextInt();

                    switch (response) {
                        case 1:
                            doChangePassword();
                            break;
                        case 2:
                            doChangeOthers();
                            break;
                        case 3:
                            break OUTER;
                        default:
                            System.out.println("Invalid Instruction!");
                            break;
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
    }

    private void doChangeOthers() {

        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print("Enter First Name(Press Enter if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            currentCustomer.setFirstName(input);
        }

        System.out.print("Enter Last  Name(Press Enter if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            currentCustomer.setLastName(input);
        }

        System.out.print("Enter Phone Number(Press Enter if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            currentCustomer.setPhoneNumber(input);
        }

        System.out.print("Enter Email Address(Press Enter if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            //Ensure the email is in valid format
            if (input.contains("@")) {
                currentCustomer.setEmail(input);
            } else {
                System.out.println("\033[31mInvalid Email Address!\033[34m");
                System.out.print("Press Enter to return...> ");
                scanner.nextLine().trim();
                return;
            }
        }

        System.out.print("Enter Username(Press Enter if no change)> ");
        input = scanner.nextLine().trim();

        if (input.length() > 0) {
            input = input.toLowerCase();
            currentCustomer.setUsername(input);
        }

        System.out.print("\nEnter 'Y' to confirm update> ");
        input = scanner.nextLine().trim();
        if (input.equals("Y")) {
            try {
                customerControllerRemote.updateCustomer(currentCustomer);
                System.out.println("[System] Update Profile Succeed!");
            } catch (CustomerExistException | GeneralException ex) {
                System.out.println("\033[31m[Warning] Update Profile Failed! - " + ex.getMessage() + ".\033[34m");
                System.out.print("Press Enter to return...> ");
                scanner.nextLine().trim();
            }
        }
    }

    private void doChangePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input Old Password> ");
        String oldPass = scanner.nextLine().trim();
        System.out.print("Input New Password([6,16] characters)> ");
        String newPass1 = scanner.nextLine().trim();
        System.out.print("Confirm New Password> ");
        String newPass2 = scanner.nextLine().trim();

        if (!newPass1.equals(newPass2)) {
            System.out.println("\033[31m\n[Warning] Change Password Failed! - Two inputs are different!\033[34m");
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();
        } else {
            try {
                customerControllerRemote.changePassword(currentCustomer.getCustomerId(), oldPass, newPass2);
                System.out.println("\n[System] Changed Password Succeed!");
                System.out.print("\nPress Enter to return...> ");
                scanner.nextLine().trim();
            } catch (CustomerNotFoundException ex) {
                //won't happen, because customer must exist
            } catch (PasswordChangeException ex) {
                System.out.println("\033[31m\n[Warning] Change Password Failed!" + ex.getMessage() + "\033[34m");
                System.out.print("\nPress Enter to return...> ");
                scanner.nextLine().trim();
            }

        }
    }
}
