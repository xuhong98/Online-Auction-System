package ProxyBiddingCumSnipingAgent;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.auctionlistingWebService.AuctionListing;
import ws.auctionlistingWebService.AuctionListingAlreadyClosedException_Exception;
import ws.auctionlistingWebService.AuctionListingNotFoundException_Exception;
import ws.auctionlistingWebService.CustomerNotFoundException_Exception;
import ws.auctionlistingWebService.EmptyListException_Exception;
import ws.bidWebService.GeneralException_Exception;
import ws.customerWebService.Customer;

/**
 *
 * @author yingshi
 */
public class PBCS {

    private Customer currentCustomer;

    public PBCS() {

    }

    public PBCS(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public void menuMain() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n*** Welcome to CrazyBids.com - Proxy Bidding cum Sniping Agent ***\n");
            System.out.println("1: View Credit Balance");
            System.out.println("2: View Auction Listing Details");
            System.out.println("3: Browse All Auction Listings");
            System.out.println("4: View Won Auction Listings");
            System.out.println("5: Logout\n\033[34m");
            Integer response = 0;

            OUTER:
            while (response < 1 || response > 5) {
                System.out.print("> ");
                try {
                    response = scanner.nextInt();
                    switch (response) {
                        case 1:
                            doViewCreditBalance();
                            break;
                        case 2:
                            doViewAuctionListingDetails();
                            currentCustomer = updateCustomerInfo(currentCustomer.getCustomerId());
                            break;
                        case 3:
                            doBrowseAllAuctionListings();
                            break;
                        case 4:
                            doViewAllWonAuctionListings();
                            break;
                        case 5:
                            break OUTER;
                        default:
                            System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31m\nInput Error! Please enter a valid number!\033[34m");
                    scanner.nextLine();
                }
            }

            if (response == 5) {
                break;
            }
        }
    }

    private void doViewCreditBalance() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Proxy Bidding cum Snipping Agent :: View Credit Balance ***\n");
        System.out.println("\033[34mYou are logged in! Welcome [" + currentCustomer.getUsername() + "] !\n\033[0m");

        System.out.println("Customer ID: " + currentCustomer.getCustomerId());
        System.out.println("Credit balance: " + currentCustomer.getCreditBalance());
        System.out.print("\nPress enter to continue...> ");
        scanner.nextLine();
    }

    private void doViewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Proxy Bidding cum Snipping Agent :: View Auction Listing Details ***\n");
        Integer response = 0;

        while (true) {
            try {
                System.out.print("Enter Auction Listing ID> ");
                Long auctionListingId = scanner.nextLong();

                try {
                    while (true) {
                        AuctionListing auctionListing = remoteViewAuctionListingDetails(auctionListingId);

                        String leftAlignFormat = "| %-20s | %-40s |%n";

                        System.out.format("\033[34m\n|------------------ Auction Listing Details  ---------------------|%n\033[0m");
                        System.out.format("+----------------------+------------------------------------------+%n");
                        System.out.format("| Column               |Details                                   |%n");
                        System.out.format("+----------------------+------------------------------------------+%n");
                        System.out.printf(leftAlignFormat, "Auction Listing ID", auctionListing.getAuctionListingId());
                        System.out.printf(leftAlignFormat, "Product Name", auctionListing.getProductName());
                        System.out.printf(leftAlignFormat, "Product Description", auctionListing.getProductDescription());
                        System.out.printf(leftAlignFormat, "Start Time", auctionListing.getStartTime());
                        System.out.printf(leftAlignFormat, "End Time", auctionListing.getEndTime());
                        System.out.printf(leftAlignFormat, "Starting Price", auctionListing.getStartingPrice());
                        System.out.printf(leftAlignFormat, "Current Max Bid", auctionListing.getCurrentMaxBiddingPrice());
                        System.out.format("+----------------------+------------------------------------------+%n\n");

                        try {
                            System.out.println("1: Configure Proxy Bidding for Acution Listing");
                            System.out.println("2: Configure Sniping for Auction Listing");
                            System.out.println("3: Back\n\033[34m");
                            System.out.print("> ");
                            response = scanner.nextInt();

                            if (response == 1) {
                                doConfigureProxyBidding(auctionListing);
                            } else if (response == 2) {
                                doConfigureSnip(auctionListing);
                            } else if (response == 3) {
                                break;
                            }
                        } catch (InputMismatchException ex) {
                            System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                            scanner.nextLine();
                        }

                    }
                    if (response == 3) {
                        break;
                    }
                } catch (AuctionListingNotFoundException_Exception ex) {
                    System.out.println("\033[31m\n[Warning] Auction Listing ID " + auctionListingId + " does not exist.\n\033[34m");
                    System.out.print("Press Enter to try again...>");
                    scanner.nextLine();
                    scanner.nextLine();
                } catch (AuctionListingAlreadyClosedException_Exception ex) {
                    System.out.println("\033[31m\n[Warning] Auction Listing ID: " + auctionListingId + " is not available now.\033[34m");

                }
            } catch (InputMismatchException ex) {
                System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                scanner.nextLine();
            }
        }

    }

    private void doConfigureProxyBidding(AuctionListing auctionListing) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Proxy Bidding cum Snipping Agent :: Configure Proxy Bidding for Acution Listing ***\n");
        System.out.println("Enter the maximum amount you are willing to pay for this auction listing");

        BigDecimal currentMax = auctionListing.getCurrentMaxBiddingPrice();
        BigDecimal minBidAmount = getMinBid(currentMax);

        System.out.println("Minimal amount required: \033[31m" + minBidAmount + "\033[0m (0 to quit)");

        System.out.print("> ");
        BigDecimal maxBid = scanner.nextBigDecimal();

        while (true) {
            if (maxBid.equals(BigDecimal.ZERO)) {
                System.out.println("Placing sniping bid failed!");
                return;
            }
            if (maxBid.compareTo(minBidAmount) < 0) {
                System.out.println("\033[31mInput amount is less than the minimal amount required! Try again!\033[34m");
                System.out.print("> ");
                maxBid = scanner.nextBigDecimal();
            } else {
                break;
            }
        }
        scanner.nextLine();
        System.out.print("Enter 'Y' to confirm: ");
        String answer = scanner.nextLine().trim();
        if (answer.equals("Y")) {
            try {
                configureProxyBidding(auctionListing.getAuctionListingId(), currentCustomer.getCustomerId(), maxBid);
                System.out.println("\nYou have successfully configured proxy bidding for auction listing!");
            } catch (GeneralException_Exception ex) {
                System.out.println("\033[31m" + ex.getMessage() + "\033[34m");
            }
            System.out.print("\nPress enter to continue...>");
            scanner.nextLine();
        } else {
            System.out.println("Placing sniping bid failed!");
        }
    }

    private void doConfigureSnip(AuctionListing auctionListing) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Proxy Bidding cum Snipping Agent :: Configure Sniping for Auction Listing***\n");

        BigDecimal currentMax = auctionListing.getCurrentMaxBiddingPrice();
        BigDecimal minBidAmount = getMinBid(currentMax);

        System.out.println("Enter the maximum amount you are willing to pay: \033[0m");
        System.out.println("Minimal amount required: \033[31m" + minBidAmount + "\033[0m (0 to quit)");
        BigDecimal maxBid = null;
        while (true) {
            System.out.print("> \033[34m");
            maxBid = scanner.nextBigDecimal();
            if (maxBid.equals(BigDecimal.ZERO)) {
                System.out.println("Placing sniping bid failed!");
                return;
            }
            if (maxBid.compareTo(minBidAmount) < 0) {
                System.out.println("\033[31mInput amount is less than the minimal amount required! Try again!\033[34m");
            } else {
                break;
            }
        }

        //get time duration and convert to expired time
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(auctionListing.getEndTime().toGregorianCalendar().getTime()); // sets endTime to cal

        System.out.println("Enter the TIME DURATION before the listing expired that you want to place the bid:");
        System.out.println("A One-Time bid will be placed then at the highest winning price ( <= Max Bid " + maxBid + ")");

        Integer duration = null;
        XMLGregorianCalendar expireDate = null;

        while (true) {
            System.out.print("> ");
            duration = scanner.nextInt();
            cal.add(Calendar.MINUTE, duration * -1);

            try {
                expireDate = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) (cal));
                break;
            } catch (DatatypeConfigurationException ex) {
                System.out.println("\033[31m\nInvalid input. Try again!\033[34m");
            }
        }

        System.out.println("Confirm placing sniping bid at maximum amount: " + maxBid + ", " + duration + " minutes before auction listing expired?");
        System.out.print("Enter 'Y' to confirm> ");
        scanner.nextLine();
        String answer = scanner.nextLine().trim();

        if (!answer.equals("Y")) {
            System.out.println("Placing sniping bid failed!");
            return;
        }
        configureSniping(auctionListing.getAuctionListingId(), currentCustomer.getCustomerId(), maxBid, expireDate);
        System.out.println("\nYou have successfully configured sniping for auction listing!");

        System.out.print("\nPress enter to continue...>");
        scanner.nextLine().trim();

    }

    private void doBrowseAllAuctionListings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Proxy Bidding cum Snipping Agent :: Browse All Auction Listings ***\n");
        try {
            List<AuctionListing> auctionListings = remoteBrowseAllAuctionListings();
            System.out.printf("%8s%20s%20s%20s%40s\n", "ID", "Product Name", "Starting Price", "Current Max Bid", "End Time");
            for (AuctionListing each : auctionListings) {
                System.out.printf("%8s%20s%20s%20s%40s\n", each.getAuctionListingId(), each.getProductName(), each.getStartingPrice(), each.getCurrentMaxBiddingPrice(), each.getEndTime());
            }
        } catch (EmptyListException_Exception ex) {
            System.out.println("\033[31mThere is no available auction listing now.\033[34m");
        }
        System.out.print("\nPress enter to continue...>");
        scanner.nextLine();
    }

    private void doViewAllWonAuctionListings() {
        System.out.println("\n*** OAS - Proxy Bidding cum Snipping Agent :: View Won Auction Listings ***\n");
        Scanner scanner = new Scanner(System.in);
        List<AuctionListing> wonAuctions = null;

        try {
            wonAuctions = remoteBrowseWonAuctionListing(currentCustomer.getCustomerId());
        } catch (CustomerNotFoundException_Exception ex) {
            //won't happen
        }

        if (wonAuctions.isEmpty()) {
            System.out.println("\033[31mNo Won Auction Listings!\033[34m");
            System.out.print("Press Enter to return> ");
            scanner.nextLine().trim();
            return;
        }

        Integer sn = 0;
        System.out.printf("%3s%10s%20s%20s%30s%15s\n", "No.", "Id", "Product Name", "Winning Price", "End Time", "Status");
        String status = "";
        for (AuctionListing each : wonAuctions) {
            sn++;
            if (each.isIsShipped()) {
                status = "Assigned Address";
            } else {
                status = "To Assign Address";
            }
            System.out.printf("%3s%10s%20s%20s%30s%15s\n", sn, each.getAuctionListingId(), each.getProductName(), each.getWinningprice(), each.getEndTime(), status);

        }
    }

    private static void configureProxyBidding(java.lang.Long auctionId, java.lang.Long customerId, java.math.BigDecimal maxAmount) throws GeneralException_Exception {
        ws.bidWebService.BidWebservice_Service service = new ws.bidWebService.BidWebservice_Service();
        ws.bidWebService.BidWebservice port = service.getBidWebservicePort();
        port.configureProxyBidding(auctionId, customerId, maxAmount);
    }

    private static void configureSniping(java.lang.Long auctionId, java.lang.Long customerId, java.math.BigDecimal maxAmount, javax.xml.datatype.XMLGregorianCalendar date) {
        ws.bidWebService.BidWebservice_Service service = new ws.bidWebService.BidWebservice_Service();
        ws.bidWebService.BidWebservice port = service.getBidWebservicePort();
        port.configureSniping(auctionId, customerId, maxAmount, date);
    }

    private static java.util.List<ws.auctionlistingWebService.AuctionListing> remoteBrowseWonAuctionListing(java.lang.Long customerId) throws CustomerNotFoundException_Exception {
        ws.auctionlistingWebService.AuctionListingWebservice_Service service = new ws.auctionlistingWebService.AuctionListingWebservice_Service();
        ws.auctionlistingWebService.AuctionListingWebservice port = service.getAuctionListingWebservicePort();
        return port.remoteBrowseWonAuctionListing(customerId);
    }

    private static java.util.List<ws.auctionlistingWebService.AuctionListing> remoteBrowseAllAuctionListings() throws EmptyListException_Exception {
        ws.auctionlistingWebService.AuctionListingWebservice_Service service = new ws.auctionlistingWebService.AuctionListingWebservice_Service();
        ws.auctionlistingWebService.AuctionListingWebservice port = service.getAuctionListingWebservicePort();
        return port.remoteBrowseAllAuctionListings();
    }

    private static AuctionListing remoteViewAuctionListingDetails(java.lang.Long auctionId) throws AuctionListingAlreadyClosedException_Exception, AuctionListingNotFoundException_Exception {
        ws.auctionlistingWebService.AuctionListingWebservice_Service service = new ws.auctionlistingWebService.AuctionListingWebservice_Service();
        ws.auctionlistingWebService.AuctionListingWebservice port = service.getAuctionListingWebservicePort();
        return port.remoteViewAuctionListingDetails(auctionId);
    }

    private static Customer updateCustomerInfo(java.lang.Long customerId) {
        ws.customerWebService.CustomerWebservice_Service service = new ws.customerWebService.CustomerWebservice_Service();
        ws.customerWebService.CustomerWebservice port = service.getCustomerWebservicePort();
        return port.updateCustomerInfo(customerId);
    }

    private BigDecimal getMinBid(BigDecimal currentPrice) {
        double priceDouble = currentPrice.doubleValue();

        if (priceDouble < 1) {
            return BigDecimal.valueOf(priceDouble + 0.05);
        }

        if (priceDouble < 5) {
            return BigDecimal.valueOf(priceDouble + 0.25);
        }

        if (priceDouble < 25) {
            return BigDecimal.valueOf(priceDouble + 0.5);
        }

        if (priceDouble < 100) {
            return BigDecimal.valueOf(priceDouble + 1);
        }

        if (priceDouble < 250) {
            return BigDecimal.valueOf(priceDouble + 2.5);
        }

        if (priceDouble < 500) {
            return BigDecimal.valueOf(priceDouble + 5);
        }

        if (priceDouble < 1000) {
            return BigDecimal.valueOf(priceDouble + 10);
        }

        if (priceDouble < 2500) {
            return BigDecimal.valueOf(priceDouble + 25);
        }

        if (priceDouble < 5000) {
            return BigDecimal.valueOf(priceDouble + 50);
        }

        return BigDecimal.valueOf(priceDouble + 100);
    }

}
