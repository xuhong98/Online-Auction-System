package OASAdminPanel;

import ejb.session.stateless.CreditPackageControllerRemote;
import entity.CreditPackage;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.exception.CreditPackageCannotDeleteException;
import util.exception.CreditPackageExistException;
import util.exception.CreditPackageNotFoundException;
import util.exception.EmptyListException;
import util.exception.GeneralException;

/**
 *
 * @author mango
 */
public class FinanceMoudle {

    private CreditPackageControllerRemote creditPackageControllerRemote;

    //constructor
    public FinanceMoudle() {

    }

    public FinanceMoudle(CreditPackageControllerRemote creditPackageControllerRemote) {
        this();
        this.creditPackageControllerRemote = creditPackageControllerRemote;
    }

    public void menuFinance() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS Admin Panel :: Finance Staff ***\n");
            System.out.println("1: Create Credit Package");
            System.out.println("2: View Credit Package Details");
            System.out.println("3: View All Credit Packages");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");
                try {
                    response = scanner.nextInt();

                    if (response == 1) {
                        doCreateCreditPackage();
                    } else if (response == 2) {
                        doViewCreditPackageDetails();
                    } else if (response == 3) {
                        doViewAllCreditPackages();
                    } else if (response == 4) {
                        break;
                    } else {
                        System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31mInput Error! Please enter a valid number! Try again.\033[34m");
                    scanner.nextLine();
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    public void doCreateCreditPackage() {
        Scanner scanner = new Scanner(System.in);
        CreditPackage newCreditPackage = new CreditPackage();

        System.out.println("\n*** OAS Admin Panel :: Finance Staff :: Create New Credit Package  ***\n");
        System.out.print("Enter name> ");
        newCreditPackage.setName(scanner.nextLine().trim());
        while (true) {
            try {
                System.out.print("Enter Credit Amount> ");
                newCreditPackage.setCreditAmount(scanner.nextBigDecimal());
                break;
            } catch (InputMismatchException ex) {
                System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter Real Money> ");
                newCreditPackage.setRealMoney(scanner.nextBigDecimal());
                break;
            } catch (InputMismatchException ex) {
                System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                scanner.nextLine();
            }
        }
        newCreditPackage.setIsEnabled(Boolean.TRUE);

        scanner.nextLine();
        try {
            newCreditPackage = creditPackageControllerRemote.createNewCreditPackage(newCreditPackage);
            System.out.println("\n[System] Credit Package [" + newCreditPackage.getName() + "] has been successfully created!\n");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine();
        } catch (CreditPackageExistException ex) {
            System.out.println("\033[31m\n[Warning] Credit package with same name already exist \n\033[34m");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine();
        } catch (GeneralException ex) {
            System.out.println("\033[31m\n[Warning] An unexpected error has occurred while creating the new Credit Package: " + ex.getMessage() + "\n\033[34m");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine();
        }
    }

    public void doViewCreditPackageDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("\n*** OAS Admin Panel :: Finance Staff :: View Credit Package Details ***");

        while (true) {
            try {
                System.out.print("\nEnter Credit Package ID(0 to quit)> ");
                Long creditPackageId = scanner.nextLong();
                if (creditPackageId.equals(0l)) {
                    break;
                }
                try {
                    CreditPackage creditPackage = creditPackageControllerRemote.retrieveCreditPackageByCreditPackageId(creditPackageId, Boolean.TRUE);

                    String leftAlignFormat = "| %-20s | %-20s |%n";

                    System.out.format("\n|--------- Credit Package Details  -----------|%n");
                    System.out.format("+----------------------+----------------------+%n");
                    System.out.format("| Column               |Details               |%n");
                    System.out.format("+----------------------+----------------------+%n");

                    System.out.printf(leftAlignFormat, "Package ID", creditPackage.getCreditPackageId());
                    System.out.printf(leftAlignFormat, "Package Name", creditPackage.getName());
                    System.out.printf(leftAlignFormat, "Credit Amount", creditPackage.getCreditAmount());
                    System.out.printf(leftAlignFormat, "Real Money", creditPackage.getRealMoney());
                    String enabled = null;
                    if (creditPackage.getIsEnabled()) {
                        enabled = "Enabled";
                    } else {
                        enabled = "Disabled";
                    }

                    System.out.printf(leftAlignFormat, "Status", enabled);
                    System.out.format("+----------------------+----------------------+%n\n");

                    while (true) {
                        try {
                            System.out.println("1: Update Credit Package");
                            System.out.println("2: Delete Credit Package");
                            System.out.println("3: Back\n");
                            System.out.print("\033[0m> \033[34m");
                            response = scanner.nextInt();

                            if (response == 1) {
                                doUpdatecreditPackage(creditPackage);
                            } else if (response == 2) {
                                doDeletecreditPackage(creditPackage);
                            }
                            break;
                        } catch (InputMismatchException ex) {
                            System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                            scanner.nextLine();
                        }
                    }
                    break;
                } catch (CreditPackageNotFoundException ex) {
                    System.out.println("\033[31m\n[System] Credit Package ID " + creditPackageId + " does not exist.\n\033[34m");
                    System.out.print("Press Enter to try again...> ");
                    scanner.nextLine();
                    scanner.nextLine();
                }
            } catch (InputMismatchException ex) {
                System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                scanner.nextLine();
            }
        }
    }

    public void doViewAllCreditPackages() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n*** OAS Admin Panel :: Finance Staff :: View All creditPackages ***\n");
        try {

            List<CreditPackage> creditPackages = creditPackageControllerRemote.retrieveAllCreditPackage();
            System.out.printf("%10s%20s%15s%20s%20s\n", "ID", "Name", "Status", "Credit Amount", "Real Money");
            String enabled = null;
            for (CreditPackage creditPackage : creditPackages) {
                if (creditPackage.getIsEnabled()) {
                    enabled = "Enabled";
                } else {
                    enabled = "Disabled";
                }
                System.out.printf("%10s%20s%15s%20s%20s\n", creditPackage.getCreditPackageId(), creditPackage.getName(), enabled, creditPackage.getCreditAmount(), creditPackage.getRealMoney());
            }

            System.out.print("\nPress Enter to continue...> ");
            scanner.nextLine();
        } catch (EmptyListException ex) {
            System.out.println("[Warning] " + ex.getMessage());
            System.out.print("\nPress Enter to return> ");
            scanner.nextLine();
        }
    }

    public void doUpdatecreditPackage(CreditPackage creditPackage) {
        Scanner scanner = new Scanner(System.in);
        if (!creditPackage.getIsEnabled()) {
            System.out.println("\nYou cannot update disabled creditPackage.");
            System.out.print("\nPress Enter to continue...>");
            scanner.nextLine();
        } else {
            System.out.println("\n*** OAS Admin Panel :: Finance Staff :: View credit Package Details :: Update credit Package ***\n");

            System.out.print("Enter Name(blank if no change)> ");
            String inputName = scanner.nextLine().trim();
            if (inputName.length() > 0) {
                creditPackage.setName(inputName);
            }

            BigDecimal input;
            while (true) {
                System.out.print("Enter Credit Amount(blank if no change)> ");
                String nextLine = scanner.nextLine().trim();
                if (nextLine.length() > 0) {
                    Scanner lineScanner = new Scanner(nextLine);
                    try {
                        input = lineScanner.nextBigDecimal();
                        creditPackage.setCreditAmount(input);
                        break;
                    } catch (InputMismatchException ex) {
                        System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                        lineScanner.nextLine();
                    }
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Enter Real Money(blank if no change)> ");
                String nextLine = scanner.nextLine();
                if (nextLine.length() > 0) {
                    Scanner lineScanner = new Scanner(nextLine);
                    try {
                        input = lineScanner.nextBigDecimal();
                        creditPackage.setRealMoney(input);
                        break;
                    } catch (InputMismatchException ex) {
                        System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                        lineScanner.nextLine();
                    }
                } else {
                    break;
                }
            }

            creditPackageControllerRemote.updateCreditPackage(creditPackage);
            System.out.println("\n[System] Credit Package updated successfully!");
            System.out.print("\nPress Enter to continue...> ");
            scanner.nextLine();
        }
    }

    public void doDeletecreditPackage(CreditPackage creditPackage) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n*** OAS Admin Panel :: Finance Staff :: View Credit Package Details :: Delete Credit Package ***\n");
        System.out.printf("\nConfirm Delete Credit Package %s (Credit Package ID: %d) \n (Enter 'Y' to Delete, otherwise enter others)> ", creditPackage.getName(), creditPackage.getCreditPackageId());
        String input = scanner.nextLine().trim();

        if (input.equals("Y")) {
            try {
                creditPackageControllerRemote.deleteCreditPackage(creditPackage.getCreditPackageId());
                System.out.println("\n[System] Credit Package Successfully Deleted!");
                System.out.print("\nPress Enter to continue...> ");
                scanner.nextLine();
            } catch (CreditPackageCannotDeleteException ex) {
                System.out.println("\033[31m[Warning] " + ex.getMessage() + "\033[34m");
                System.out.print("\nPress Enter to continue...> ");
                scanner.nextLine();
            }
        } else {
            System.out.println("\033[31m\n[Warning] Credit Pakcage NOT deleted!\033[34m");
            System.out.print("\nPress Enter to continue...> ");
            scanner.nextLine();
        }

    }

}
