package OASAuctionClient;

import ejb.session.stateless.AddressControllerRemote;
import ejb.session.stateless.AuctionListingControllerRemote;
import ejb.session.stateless.BidControllerRemote;
import ejb.session.stateless.CreditTransactionControllerRemote;
import ejb.session.stateless.CustomerControllerRemote;
import entity.Address;
import entity.AuctionListing;
import entity.Bid;
import entity.Customer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.enumeration.AuctionListingStatus;
import util.enumeration.BidType;
import util.exception.AuctionListingAlreadyClosedException;
import util.exception.AuctionListingNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.EmptyListException;
import util.exception.GeneralException;
import util.exception.InsufficientCreditBalanceException;

/**
 *
 * @author yingshi
 */
public class AuctionListingModule {

    private CustomerControllerRemote customerControllerRemote;
    private AuctionListingControllerRemote auctionListingControllerRemote;
    private BidControllerRemote bidControllerRemote;
    private CreditTransactionControllerRemote creditTransactionControllerRemote;
    private AddressControllerRemote addressControllerRemote;

    private Customer currentCustomer;

    public AuctionListingModule(CustomerControllerRemote customerControllerRemote, AuctionListingControllerRemote auctionListingControllerRemote, BidControllerRemote bidControllerRemote, CreditTransactionControllerRemote creditTransactionControllerRemote, AddressControllerRemote addressControllerRemote) {
        this.customerControllerRemote = customerControllerRemote;
        this.auctionListingControllerRemote = auctionListingControllerRemote;
        this.bidControllerRemote = bidControllerRemote;
        this.creditTransactionControllerRemote = creditTransactionControllerRemote;
        this.addressControllerRemote = addressControllerRemote;
    }

    public void mainMenu(Customer customer) {
        currentCustomer = customer;
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS - Auction Client :: Auction Listing Module ***\n");
            System.out.println("1: Browse All Auction Listings");
            System.out.println("2: View Auction Listing Details");
            System.out.println("3: Browse Won Auction Listings");
            System.out.println("4: View All My Biddings");
            System.out.println("5: Go Back\n");
            response = 0;

            OUTER:
            while (response < 1 || response > 5) {
                try {
                    System.out.print("\033[0m> \033[34m");
                    response = scanner.nextInt();
                    switch (response) {
                        case 1:
                            doBrowseAllAuctionListings();
                            break;
                        case 2:
                            doViewAuctionListingDetails();
                            break;
                        case 3:
                            doBrowseWonAuctionListings();
                            break;
                        case 4:
                            doViewAllBiddings();
                        case 5:
                            break OUTER;
                        default:
                            System.out.println("\033[31mInvalid Intruction Number!\033[34m");
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
                    scanner.next();
                } catch (AuctionListingNotFoundException ex) {
                    System.out.println("\033[31m[Warning] Wrong Auction Listing ID! " + ex.getMessage() + "\033[34m");
                    System.out.println("\033[31mPress enter to continue...>\033[34m");
                    scanner.nextLine();
                }
            }
            if (response == 5) {
                break;
            }
        }
    }

    private void doBrowseAllAuctionListings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: Browse All Auction Listings ***\n");

        try {
            List<AuctionListing> allAuction = auctionListingControllerRemote.retrieveOngoingAuction();
            System.out.println("Below are ongoing auction listings: \n");
            //product Description shown in Details function
            System.out.printf("%5s%20s%15s%15s%35s%35s\n", "Id", "Product Name", "Max Bid", "Start Price", "Start Time", "End Time");

            for (AuctionListing each : allAuction) {
                System.out.printf("%5s%20s%15s%15s%35s%35s\n", each.getAuctionListingId(), each.getProductName(), each.getCurrentMaxBiddingPrice(), each.getStartingPrice(), each.getStartTime(), each.getEndTime());
            }
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();
        } catch (EmptyListException ex) {
            System.out.println("\033[31m Sorry, currently there's no auction listing available.\033[34m");
        }

    }

    private void doViewAuctionListingDetails() throws AuctionListingNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: View Auction Listing Details ***\n");
        System.out.print("Please Enter the ID of the Auction Listing> ");
        Long id = scanner.nextLong();
        AuctionListing auctionListing = auctionListingControllerRemote.retrieveAuctionListingById(id, true, false);

        try {
            while (true) {
                if (!auctionListing.getStatus().equals(AuctionListingStatus.ONGOING)) {
                    System.out.println("\n\033[31m[Warning] This auction listing is currently not available!\033[34m");
                    scanner.nextLine();
                    System.out.print("Press Enter to return...> ");
                    scanner.nextLine().trim();
                    return;
                }

                String leftAlignFormat = "| %-20s | %-40s |%n";

                System.out.println("\n|-------------- Auction Listing Details --------------------------|");
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

                System.out.println("1: Place New Bid");
                System.out.println("2: Refresh Auction Listing Bids");
                System.out.println("3: Go Back\n");
                Integer response = 0;
                OUTER:
                while (response < 1 || response > 3) {
                    System.out.print("\033[0m> \033[34m");
                    response = scanner.nextInt();
                    switch (response) {
                        case 1:
                            placeNewBid(auctionListing);
                            break;
                        case 2:
                            auctionListing = refreshBids(auctionListing);
                            break;
                        case 3:
                            break OUTER;
                        default:
                            System.out.println("\033[31mError: Please enter a valid instruction!\033[34m");
                            break;
                    }
                }
                if (response == 3) {
                    break;
                }
            }
        } catch (InsufficientCreditBalanceException ex) {
            System.out.println("\033[31m[Warning] Place New Bid Failed! Error: " + ex.getMessage() + "!\033[34m");
        } catch (InputMismatchException ex) {
            System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
            scanner.next();
        }
    }

    private void doBrowseWonAuctionListings() {
        System.out.println("\n*** OAS - Auction Client :: Browse Won Auction Listings ***\n");
        Scanner scanner = new Scanner(System.in);
        List<AuctionListing> wonAuctions = currentCustomer.getWonAuctionListings();
        if (wonAuctions.isEmpty()) {
            System.out.println("\033[31mNo Won Auction Listings\033[34m");
            System.out.print("Press Enter to return...> ");
            scanner.nextLine().trim();
            return;
        }

        Integer sn = 0;
        System.out.printf("%3s%20s%20s%35s%30s\n", "No.", "Product Name", "Winning Price", "End Time", "Status");
        String status = "";
        boolean hasToAssigned = false;
        for (AuctionListing each : wonAuctions) {
            sn++;
            if (each.getIsShipped()) {
                status = "Assigned [Address Id: " + each.getShippedAddress().getAddressId() + "]";
            } else {
                status = "To Assign Address";
                hasToAssigned = true;
            }
            System.out.printf("%3s%20s%20s%35s%30s\n", sn, each.getProductName(), each.getWinningprice(), each.getEndTime(), status);

        }

        //if no won auction listing needs to be assigned addresses;
        if (!hasToAssigned) {
            System.out.println("\nAll the won auction listings were assigned addresses!");
            System.out.print("\nPress Enter to return...> ");
            scanner.nextLine().trim();
            return;
        }
        
        //if some won auction listing needs to be assigned addresses
        System.out.println("\nSelect the No. of auction listing to assign Address(0 to return)");
        Integer response = -1;
        while (response < 0 || response > sn) {
            System.out.print("> \033[34m");
            try {
                response = scanner.nextInt();
                if (response == 0) {
                    break;
                }
                if (response > 0 && response <= sn) {

                    AuctionListing selectedAuction = wonAuctions.get(response - 1);

                    List<Address> addresses = currentCustomer.getAddresses();
                    if (addresses.isEmpty()) {
                        throw new EmptyListException("Empty Address List!");
                    }
                    
                    //show address list
                    System.out.println("\nYour Address List: \n");
                    printAddresses(addresses);
                    System.out.println("Select the Address you want to ship to: ");
                    System.out.print("\033[0m> \033[34m");
                    
                    //select the shipped addresss
                    Integer num = scanner.nextInt();
                    if (num > 0 && num <= addresses.size()) {
                        System.out.println("Ship [" + selectedAuction.getProductName() + "]");
                        System.out.print("To Address: ");
                        Address selectedAddress = addresses.get(num - 1);

                        String line2 = "";
                        if (selectedAddress.getAddressLine2() == null) {
                            line2 = "NIL";
                        } else {
                            line2 = selectedAddress.getAddressLine2();
                        }
                        String address = "[" + selectedAddress.getPostcode() + "] " + line2 + selectedAddress.getAddressLine1() + ", " + selectedAddress.getCity() + ", " + selectedAddress.getState() + ", " + selectedAddress.getCountry() + ".";
                        System.out.println(address);
                        scanner.nextLine();
                        
                        System.out.print("\nEnter 'Y' to confirm> ");
                        String answer = scanner.nextLine().trim();
                        if (answer.equals("Y")) {
                            addressControllerRemote.assignAddress(selectedAddress.getAddressId(), selectedAuction.getAuctionListingId());

                            System.out.println("\n[System] Assigned Delivery Address Successesful!");
                            System.out.print("\nPress Enter to return...> ");
                            scanner.nextLine().trim();
                        } else {
                            System.out.println("\033[31m[Warning] Assigned Delivery Address Failed!\033[34m");
                        }
                    } else {
                        System.out.println("\033[31mInvalid Number!\033[34m");
                        System.out.print("Press Enter to return...> ");
                        scanner.nextLine().trim();
                    }

                }
            } catch (InputMismatchException ex) {
                System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
                scanner.next();
            } catch (EmptyListException ex) {
                System.out.println("\033[31m[Warning] You didn't create any address. Please create address first!\033[34m");
            }
        }

    }

    private void doViewAllBiddings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: View All My Biddings ***\n");

        List<Bid> biddings = currentCustomer.getBiddings();
        if (biddings.isEmpty()) {
            System.out.println("\nNo Biddings.");
        } else {
            System.out.printf("%8s%23s%18s%15s%33s%13s%13s%18s%20s\n", "Bid Id", "Auction Listing ID", "Product Name", "Bid Amount", "Placed Time", "Bid Type", "Status", "Is Current Max?","VS.Reserve Price");
            for (Bid each : biddings) {
                String isCurrentMax = "No";
                if (each.getAuctionListing().getCurrentMaxBiddingPrice().equals(each.getBiddingAmount())) {
                    isCurrentMax = "Yes";
                }
                String compareWithReserve = "";
                if (each.getAuctionListing().getReservePrice().compareTo(each.getBiddingAmount())<0){
                    compareWithReserve = "Above";
                }else{
                    compareWithReserve = "Below or same";
                }
                System.out.printf("%8s%23s%18s%15s%33s%13s%13s%18s%20s\n", each.getBidId(), each.getAuctionListing().getAuctionListingId(), each.getAuctionListing().getProductName(), each.getBiddingAmount(), each.getPlacedTime(), each.getBidtype(), each.getStatus(), isCurrentMax,compareWithReserve);
            }
        }
        System.out.print("Press Enter to continue...>");
        scanner.nextLine();
    }

    private void placeNewBid(AuctionListing auctionListing) throws InsufficientCreditBalanceException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS - Auction Client :: Place New Bid ***\n");
        System.out.println("Place new bid for auction listing ID: " + auctionListing.getAuctionListingId());

        BigDecimal bidAmount;
        BigDecimal minBid;

        //this is the first bid, bid amount >= starting price is acceptable
        if (auctionListing.getBiddings().isEmpty()) {
            minBid = getMinBid(auctionListing.getStartingPrice());
        } else {
            //this auction listing already have biddings
            minBid = getMinBid(auctionListing.getCurrentMaxBiddingPrice());
        }

        //check whether this customer has bidded for this auction before;
        List<Bid> biddings = currentCustomer.getBiddings();

        Bid oldBid = null;
        Boolean biddedBefore = false;

        for (Bid each : biddings) {
            if (each.getAuctionListing().getAuctionListingId().equals(auctionListing.getAuctionListingId())) {
                oldBid = each;
                biddedBefore = true;
                System.out.println("\nYou HAVE bidded for this auction listing before.");

                //tell customer whether you are the current max current bid.
                if (each.getBidId().equals(auctionListing.getMaxBidId())) {
                    System.out.println("\033[34mYou are the max current bid for this auction listing!\n\033[0m");
                } else {
                    System.out.println("You are NOT the max current bid for this auction listing!\n");
                }
                break;
            }

        }

        System.out.println("Currently the Minimal amount you can place is: \033[34m" + minBid + "\033[0m Credit(s).\n");

        try {
            System.out.println("Your current credit balance: " + currentCustomer.getCreditBalance());
            System.out.print("Enter your bidding price(0 to quit)> ");
            bidAmount = scanner.nextBigDecimal();
            bidAmount.setScale(2, RoundingMode.HALF_UP);

            if (bidAmount.equals(BigDecimal.ZERO)) {
                return;
            }

            BigDecimal actualDeduct;

            if (biddedBefore) {
                actualDeduct = bidAmount.subtract(oldBid.getBiddingAmount());
            } else {
                actualDeduct = bidAmount;
            }

            if (bidAmount.compareTo(minBid) >= 0) {

                System.out.println("\n[\033[34m" + actualDeduct + "\033[0m] credit(s) will actually be deducted from your account.");
                System.out.print("\nEnter 'Y' to confirm placing new bid at: " + bidAmount + " credit(s)> ");

                scanner.nextLine();
                String answer = scanner.nextLine().trim();

                if (answer.equals("Y")) {

                    if (actualDeduct.compareTo(currentCustomer.getCreditBalance()) > 0) {
                        throw new InsufficientCreditBalanceException("Insufficient Credit Balance! Please top up first!");
                    }
                    //if bidded before, we will update the previous bid
                    if (biddedBefore) {
                        bidControllerRemote.incrementBid(oldBid.getBidId(), bidAmount, actualDeduct);
                    } else {
                        //if did not bid before, we will create a new bid record
                        Bid newBid = new Bid(new Date(), bidAmount, BidType.NORMAL);

                        newBid = bidControllerRemote.createNewBid(newBid, auctionListing.getAuctionListingId(), currentCustomer.getCustomerId());
                        bidControllerRemote.incrementBid(newBid.getBidId(), bidAmount, actualDeduct);
                    }
                    System.out.println("\n[System] Place New Bid successfully!");
                }

            } else {
                System.out.println("\033[31m[Warning] Bidding Amount is less than minimal required.\033[34m");
            }

        } catch (InputMismatchException ex) {
            System.out.println("\033[31mInput Error! Please enter a valid number!\033[34m");
            scanner.next();
        } catch (GeneralException | AuctionListingAlreadyClosedException ex) {
            System.out.println("\033[31m[Warning] " + ex.getMessage() + "!\033[34m");
        }
    }

    private AuctionListing refreshBids(AuctionListing auctionListing) {
        try {
            auctionListing = auctionListingControllerRemote.retrieveAuctionListingById(auctionListing.getAuctionListingId(), true, false);
            currentCustomer = customerControllerRemote.retrieveCustomerByCustomerId(currentCustomer.getCustomerId(), true, true, true, true, true);
            System.out.println("\n[System] Auction Listing Bids Refreshed!\n");
            return auctionListing;
        } catch (AuctionListingNotFoundException | CustomerNotFoundException ex) {
            return null;
        }
    }

    //Return the minimal amount that the next bid needs to be (ie. currentPrice = 25, return 26(25+1))
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

    private void printAddresses(List<Address> addresses) {
        System.out.printf("%3s%10s%10s%10s%20s%20s%10s\n", "No.", "Country", "State", "City", "Address Line 1", "Address Line 2", "Postcode");
        Integer sn = 0;
        for (Address each : addresses) {
            String line2 = "";
            if (each.getAddressLine2() == null) {
                line2 = "NIL";
            } else {
                line2 = each.getAddressLine2();
            }
            sn++;
            System.out.printf("%3s%10s%10s%10s%20s%20s%10s\n", sn, each.getCountry(), each.getState(), each.getCity(), each.getAddressLine1(), line2, each.getPostcode());
        }
    }
}
