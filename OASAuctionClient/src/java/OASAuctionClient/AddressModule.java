package OASAuctionClient;

import ejb.session.stateless.AddressControllerRemote;
import ejb.session.stateless.CustomerControllerRemote;
import entity.Address;
import entity.Customer;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.exception.AddressNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
public class AddressModule {

    AddressControllerRemote addressControllerRemote;
    CustomerControllerRemote customerControllerRemote;

    Customer currentCustomer;

    public AddressModule(AddressControllerRemote addressControllerRemote, CustomerControllerRemote customerControllerRemote) {
        this.addressControllerRemote = addressControllerRemote;
        this.customerControllerRemote = customerControllerRemote;
    }

    public void mainMenu(Customer customer) {
        currentCustomer = customer;
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS - Auction Client :: Address Module ***\n");
            System.out.println("1: Create Address");
            System.out.println("2: View Address Details");
            System.out.println("3: View All Addresses");
            System.out.println("4: Go Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 4) {
                try {
                    System.out.print("> ");
                    response = scanner.nextInt();
                    switch (response) {
                        case 1:
                            doCreateAddress();
                            currentCustomer = customerControllerRemote.retrieveCustomerByCustomerId(currentCustomer.getCustomerId(), true, true, true, true, true);
                            break;
                        case 2:
                            doViewAddressDetails();
                            currentCustomer = customerControllerRemote.retrieveCustomerByCustomerId(currentCustomer.getCustomerId(), true, true, true, true, true);
                            break;
                        case 3:
                            doViewAllAddresses();
                            break;
                        case 4:
                            break OUTER;
                        default:
                            System.out.println("\033[31mInvalid Intruction Number!\033[34m");
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
                    scanner.next();
                } catch (CustomerNotFoundException ex) {

                }

            }
            if (response == 4) {
                break;
            }

        }
    }

    private void doCreateAddress() {

        Scanner scanner = new Scanner(System.in);
        Address address = new Address();

        System.out.println("\n*** OAS - Auction Client :: Create Address ***\n");

        System.out.print("Enter Country> ");
        address.setCountry(scanner.nextLine());

        System.out.print("Enter State> ");
        address.setState(scanner.nextLine());

        System.out.print("Enter City> ");
        address.setCity(scanner.nextLine());

        System.out.print("Enter Address Line 1> ");
        address.setAddressLine1(scanner.nextLine());

        System.out.print("Enter Address Line 2(blank if don't needed)> ");
        String inputLine2 = scanner.nextLine().trim();
        if (inputLine2.length() > 0) {
            address.setAddressLine2(inputLine2);
        }

        System.out.print("Enter Postcode> ");
        address.setPostcode(scanner.nextLine().trim());

        //initialize the address enabled
        address.setIsEnabled(true);

        try {
            address = addressControllerRemote.createNewAddress(address, currentCustomer.getCustomerId());
            System.out.println("\n[System] New Address Created Successfully!");
        } catch (GeneralException ex) {
            System.out.println("\033[31m[Warning] " + ex.getMessage() + "\033[34m");
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();
        }

    }

    private void doViewAddressDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: View Address Details ***\n");
        System.out.print("Enter Address ID> ");

        try {
            Long id = scanner.nextLong();
            while (true) {
                Address address = addressControllerRemote.retrieveAddressById(id);

                if (!address.getCustomer().equals(currentCustomer)) {
                    throw new AddressNotFoundException("You have no right to view this address!");
                }

                String leftAlignFormat = "| %-20s | %-40s |%n";

                System.out.println("\n|------------------ Address Details -----------------------------|");
                System.out.format("+----------------------+------------------------------------------+%n");
                System.out.format("| Column               |Details                                   |%n");
                System.out.format("+----------------------+------------------------------------------+%n");
                System.out.printf(leftAlignFormat, "Address ID ", address.getAddressId());
                System.out.printf(leftAlignFormat, "Country ", address.getCountry());
                System.out.printf(leftAlignFormat, "State ", address.getState());
                System.out.printf(leftAlignFormat, "City ", address.getCity());
                System.out.printf(leftAlignFormat, "Address Line 1 ", address.getAddressLine1());

                if (address.getAddressLine2() != null) {
                    System.out.printf(leftAlignFormat, "Address Line 2 ", address.getAddressLine2());
                }
                System.out.printf(leftAlignFormat, "Postcode ", address.getPostcode());

                System.out.format("+----------------------+------------------------------------------+%n\n");

                Integer response = 0;

                System.out.println("\nPlease select next operation: \n");
                System.out.println("1: Update Address");
                System.out.println("2: Delete Address");
                System.out.println("3: Go Back\n");
                response = 0;

                OUTER:
                while (response < 1 || response > 3) {
                    System.out.print("> \033[34m");
                    try {
                        response = scanner.nextInt();
                        switch (response) {
                            case 1:
                                doUpdateAddress(address);
                                break OUTER;
                            case 2:
                                doDeleteAddress(id);
                                break OUTER;
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
        } catch (AddressNotFoundException ex) {
            System.out.println("\033[31m[Warning] " + ex.getMessage() + "\033[34m");
            scanner.nextLine();
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();
        } catch (InputMismatchException ex) {
            System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
            scanner.next();
        }
    }

    private void doViewAllAddresses() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: View All Addresses ***\n");
        List<Address> addresses = currentCustomer.getAddresses();
        if (addresses.isEmpty()) {
            System.out.println("\033[31mYou haven't create any address!\033[34m");
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();
            return;
        }

        System.out.printf("%5s%13s%13s%13s%35s%30s%15s\n", "Id", "Country", "State", "City", "Address Line 1", "Address Line 2", "Postcode");
        for (Address each : addresses) {
            String line2 = "";
            if (each.getAddressLine2() == null) {
                line2 = "NIL";
            } else {
                line2 = each.getAddressLine2();
            }
            System.out.printf("%5s%13s%13s%13s%35s%30s%15s\n", each.getAddressId(), each.getCountry(), each.getState(), each.getCity(), each.getAddressLine1(), line2, each.getPostcode());

        }
        System.out.print("\nPress Enter to return> ");
        scanner.nextLine().trim();
    }

    private void doUpdateAddress(Address address) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.println("\n*** OAS - Auction Client :: Update Address ***\n");

        System.out.print("Enter Country(Press Enter if no change)> ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            address.setCountry(input);
        }

        System.out.print("Enter State(Press Enter if no change)> ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            address.setState(input);
        }

        System.out.print("Enter City(Press Enter if no change)> ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            address.setCity(input);
        }

        System.out.print("Enter Address Line 1(Press Enter if no change)> ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            address.setAddressLine1(input);
        }

        System.out.print("Enter Address Line 2(Press Enter if no change)> ");
        input = scanner.nextLine();
        if (input.length() > 0) {
            address.setAddressLine2(input);
        }

        System.out.print("Enter Postcode(Press Enter if no change)> ");
        input = scanner.nextLine().trim();
        if (input.length() > 0) {
            address.setPostcode(input);
        }

        System.out.print("Enter 'Y' to confirm Update> ");
        input = scanner.nextLine().trim();
        if (input.equals("Y")) {
            addressControllerRemote.updateAddress(address);

            System.out.println("\n[System] Address ID: " + address.getAddressId() + " Successfully Updated!");

        } else {
            System.out.println("\033[31m\n[Warning] Update Failed!\033[34m");
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();
        }

    }

    private void doDeleteAddress(Long id) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: Delete Address ***\n");
        System.out.println("Address Id: " + id);
        System.out.print("Enter 'Y' to confirm Delete> \033[34m");
        String input = scanner.nextLine().trim();
        if (input.equals("Y")) {
            try {
                addressControllerRemote.deleteAddress(id);
                System.out.println("\n[System] Address ID: " + id + " Successfully Deleted!");
                System.out.print("\nPress Enter to return...> ");
                scanner.nextLine().trim();
            } catch (GeneralException ex) {
                System.out.println("\033[31m\n[Warning] " + ex.getMessage() + ".\033[34m");
                System.out.print("\nPress Enter to return...> ");
                scanner.nextLine().trim();
            }

        } else {
            System.out.println("\033[31m\n[Warning] Deleted Failed! \033[34m");
            System.out.print("Press Enter to return...> ");
            scanner.nextLine().trim();
        }
    }
}
