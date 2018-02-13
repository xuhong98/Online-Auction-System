package OASAuctionClient;

import ejb.session.stateless.CreditPackageControllerRemote;
import ejb.session.stateless.CustomerControllerRemote;
import entity.CreditPackage;
import entity.CreditTransaction;
import entity.Customer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.exception.CustomerNotFoundException;
import util.exception.EmptyListException;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
public class CreditModule {

    private CustomerControllerRemote customerControllerRemote;
    private CreditPackageControllerRemote creditPackageControllerRemote;

    private Customer currentCustomer;

    public CreditModule() {
    }

    public CreditModule(CustomerControllerRemote customerControllerRemote, CreditPackageControllerRemote creditPackageControllerRemote) {
        this.customerControllerRemote = customerControllerRemote;
        this.creditPackageControllerRemote = creditPackageControllerRemote;
    }

    public void mainMenu(Customer customer) {
        currentCustomer = customer;
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS - Auction Client :: Credit Module ***\n");
            System.out.println("1: View Credit Balance");
            System.out.println("2: View Credit Transaction History");
            System.out.println("3: Purchase Credit Package");
            System.out.println("4: Go Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 4) {
                System.out.print("\033[0m> \033[34m");
                try {
                    response = scanner.nextInt();

                    switch (response) {
                        case 1:
                            doViewCreditBalance();
                            break;
                        case 2:
                            doViewCreditTransactionHistory();
                            break;
                        case 3:
                            doPurchaseCreditPackage();
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
                }

            }
            if (response == 4) {
                break;
            }
        }
    }

    private void doViewCreditBalance() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: View Credit Balance ***\n");
        System.out.println("Customer ID: " + currentCustomer.getCustomerId());
        System.out.println("Credit Balance: " + currentCustomer.getCreditBalance());
        System.out.print("\nPress Enter to return...> ");
        scanner.nextLine().trim();

    }

    private void doViewCreditTransactionHistory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: View Credit Transaction History ***\n");
        List<CreditTransaction> history = currentCustomer.getCreditTransactionHistory();

        //order credit tansaction according to credit transaction ID
        Collections.sort(history, (CreditTransaction c1, CreditTransaction c2) -> {
            if (c1.getCreditTransactionId() > c2.getCreditTransactionId()) {
                return 1;
            } else {
                return -1;
            }
        });

        if (history == null || history.isEmpty()) {
            System.out.println("\033[31mNo Credit Transaction History!\033[34m");
        } else {
            System.out.printf("%4s%12s%10s%32s%72s\n", "Id", "Type", "Amount", "Time", "Description");

            for (CreditTransaction each : history) {
                System.out.printf("%4s%12s%10s%32s%72s\n", each.getCreditTransactionId(), each.getTransactionType(), each.getAmount(), each.getTransactionTime(), each.getDescription());
            }
        }
        System.out.print("\nPress Enter to return...> ");
        scanner.nextLine().trim();

    }

    private void doPurchaseCreditPackage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: Purchase Credit Package ***\n");

        try {
            List<CreditPackage> allPackages = creditPackageControllerRemote.retrieveAllEnabledPackage();
            System.out.println("Currently Available Credit Packages: \n");
            System.out.printf("%3s%25s%15s%15s%20s\n", "No.", "Name", "Real Money", "Credit", "Exchange Ratio");

            Integer sn = 0;
            Double ratio;
            //select credit package via sequence number rather than credit packge ID
            for (CreditPackage each : allPackages) {
                sn++;
                ratio = each.getRealMoney().doubleValue() / each.getCreditAmount().doubleValue();
                DecimalFormat df = new DecimalFormat("0.00");
                System.out.printf("%3s%25s%15s%15s%20s\n", sn, each.getName(), each.getRealMoney(), each.getCreditAmount(), df.format(ratio));

            }

            System.out.println("\nSelect the package you want to purchase(0 to quit)");
            Integer packageNum = -1;

            while (packageNum < 0 || packageNum > sn) {
                System.out.print("\033[0m> \033[34m");
                try {
                    packageNum = scanner.nextInt();

                    if (packageNum >= 1 && packageNum <= sn) {
                        //use sequence number to get the credit package customer choose
                        CreditPackage selectedPackage = allPackages.get(packageNum - 1);

                        System.out.println("\nSelect the number of units you want to purchase(0 to quit)");
                        System.out.print("\033[0m> \033[34m");

                        Integer units = 0;
                        units = scanner.nextInt();

                        scanner.nextLine();
                        if (units != 0) {
                            System.out.println("\n\033[34m- Summary -\033[0m \n");
                            System.out.println("Credit Package: " + selectedPackage.getName());
                            System.out.println("Unit Prince: " + selectedPackage.getRealMoney());
                            System.out.println("Unit: " + units);
                            BigDecimal totalRealMoney = selectedPackage.getRealMoney().multiply(BigDecimal.valueOf(units.longValue()));
                            BigDecimal totalCredit = selectedPackage.getCreditAmount().multiply(BigDecimal.valueOf(units.longValue()));
                            System.out.println("Total Amount Paid: " + totalRealMoney);
                            System.out.println("Credit Purchased: " + totalCredit);
                            System.out.println("------------------------------\n");
                            System.out.print("Enter 'Y' to confirm purchase> ");
                            String answer = scanner.nextLine().trim();
                            if (answer.equals("Y")) {
                                try {
                                    creditPackageControllerRemote.purchaseCreditPackage(selectedPackage.getCreditPackageId(), currentCustomer.getCustomerId(), units);
                                    Long id = currentCustomer.getCustomerId();
                                    currentCustomer = null;
                                    currentCustomer = customerControllerRemote.retrieveCustomerByCustomerId(id, false, false, true, false, false);
                                    System.out.println("\n[System] New Credit Package Purchased!");
                                    System.out.println("Current Credit Balance: \033[34m" + currentCustomer.getCreditBalance() + "\033[0m");
                                } catch (GeneralException ex) {
                                    System.out.println("\033[31m[Warning] Purchased Failed!" + ex.getMessage() + ".\033[34m");
                                } catch (CustomerNotFoundException ex) {

                                }

                            } else {
                                System.out.println("\033[31m[Warning] Purchased Failed!\033[34m");
                            }
                        } else {
                            System.out.println("\033[31m[Warning] Purchased Failed!\033[34m");
                        }
                        System.out.print("\nPress Enter to return..> ");
                        scanner.nextLine().trim();
                    } else {
                        if (packageNum == 0) {
                            break;
                        }
                        System.out.println("\033[31m[Warning] Invalid package ID!\033[34m");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
                    scanner.next();
                }
            }
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();

        } catch (InputMismatchException ex) {
            System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
            scanner.next();
        }
    }
}
