package OASAdminPanel;

import ejb.session.stateless.AuctionListingControllerRemote;
import entity.AuctionListing;
import entity.Bid;
import entity.Customer;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.enumeration.AuctionListingStatus;
import util.exception.AuctionListingExistException;
import util.exception.AuctionListingNotFoundException;
import util.exception.EmptyListException;
import util.exception.GeneralException;

/**
 *
 * @author mango
 */
public class SalesMoudle {

    private AuctionListingControllerRemote auctionListingControllerRemote;

    //constructor
    public SalesMoudle() {

    }

    public SalesMoudle(AuctionListingControllerRemote auctionListingControllerRemote) {
        this.auctionListingControllerRemote = auctionListingControllerRemote;

    }

    public void menuSales() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("\n*** OAS Admin Panel :: Sales Staff ***\n");
            System.out.println("1: Create New Auction Listing");
            System.out.println("2: View Auction Listing Details");
            System.out.println("3: View All Auction Listing");
            System.out.println("4: View All Auction listings with Bids but Below Reserve Price");
            System.out.println("5: Back\n");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("\033[0m> \033[34m");
                try {
                    response = scanner.nextInt();

                    if (response == 1) {
                        doCreateNewAuctionListing();
                    } else if (response == 2) {
                        doViewAuctionListingDetails();
                    } else if (response == 3) {
                        doViewAllAuctionListings();
                    } else if (response == 4) {
                        doViewAllAuctionListingsWithBidBelowReservePrice();
                    } else if (response == 5) {
                        break;
                    } else {
                        System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                    scanner.nextLine();
                }
            }

            if (response == 5) {
                break;
            }
        }
    }

    public void doCreateNewAuctionListing() {
        Scanner scanner = new Scanner(System.in);
        AuctionListing newAuctionListing = new AuctionListing();

        System.out.println("\n*** OAS Admin Panel :: Sales Staff :: Create New Auction Listing  ***\n");

        System.out.print("Enter Product Name> ");
        newAuctionListing.setProductName(scanner.nextLine().trim());
        System.out.print("Enter product Description> ");
        newAuctionListing.setProductDescription(scanner.nextLine().trim());

        System.out.println("\nPlease follow date format: \033[34myyyy-MM-dd HH:mm:ss\033[0m");
        System.out.println("Do you want to start the new Auction Listing from NOW? (Enter 'Y' if you want. Enter others if you don't.)");
        System.out.print("> ");
        //get the current time
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //inithialize startTime to be currentTime
        Date startTime = new Date();

        if (scanner.nextLine().trim().equals("Y")) {
            newAuctionListing.setStartTime(currentTime);
            newAuctionListing.setStatus(AuctionListingStatus.ONGOING);
        } else {
            while (true) {
                System.out.print("Enter Start Time (should be After current time) > ");
                String dateInString = scanner.nextLine();

                try {
                    startTime = formatter.parse(dateInString);
                    if (startTime.after(currentTime)) {
                        newAuctionListing.setStartTime(startTime);
                        newAuctionListing.setStatus(AuctionListingStatus.INVISIBLE);
                        break;
                    } else {
                        System.out.println("\033[31m\n[Warning] Start Time should be after current time. Please try again\033[34m");
                    }
                } catch (ParseException ex) {
                    System.out.println("\033[31m\n[Warning] Invalid date format. Please try again.\033[34m");
                }
            }
        }

        Date endTime;
        while (true) {
            System.out.print("Enter End Time> ");
            String dateInString = scanner.nextLine();

            try {
                endTime = formatter.parse(dateInString);
                if (endTime.after(startTime)) {
                    newAuctionListing.setEndTime(endTime);
                    break;
                } else {
                    System.out.println("\033[31m\n[Warning] End Time should be after start time. Please try again\033[34m");
                }
            } catch (ParseException e) {
                System.out.println("\033[31m\n[Warning] Invalid date format. Please try again.\033[34m");
            }
        }

        while (true) {
            try {
                System.out.print("Enter Reserve Price> ");
                newAuctionListing.setReservePrice(scanner.nextBigDecimal());
                break;
            } catch (InputMismatchException ex) {
                System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter Starting Price> ");
                newAuctionListing.setStartingPrice(scanner.nextBigDecimal());
                break;
            } catch (InputMismatchException ex) {
                System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                scanner.nextLine();
            }
        }

        newAuctionListing.setCurrentMaxBiddingPrice(BigDecimal.ZERO);
        scanner.nextLine();
        try {
            newAuctionListing = auctionListingControllerRemote.createNewAuctionListing(newAuctionListing);
            System.out.println("\n[System] Auction Listing " + newAuctionListing.getProductName() + " Succuessfully created!\n");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine();
        } catch (AuctionListingExistException ex) {
            System.out.println("\033[31m\n[Warning] Failed! Auction Listing with same name already exist \n\033[34m");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine();
        } catch (GeneralException ex) {
            System.out.println("\033[31m\n[Warning] An unexpected error has occurred while creating the new Auction Listing: " + ex.getMessage() + "\n\033[34m");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine();
        }
    }

    public void doViewAuctionListingDetails() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        System.out.println("\n*** OAS Admin Panel :: Sales Staff :: View Auction Listing Details ***");

        try {
            System.out.print("\nEnter Auction Listing ID> ");
            Long auctionListingId = scanner.nextLong();

            try {
                while (true) {
                    AuctionListing auctionListing = auctionListingControllerRemote.retrieveAuctionListingById(auctionListingId, Boolean.FALSE, Boolean.FALSE);

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
                    System.out.printf(leftAlignFormat, "Status", auctionListing.getStatus());
                    System.out.printf(leftAlignFormat, "Current Max Bid", auctionListing.getCurrentMaxBiddingPrice());
                    System.out.printf(leftAlignFormat, "Starting Price", auctionListing.getStartingPrice());
                    System.out.printf(leftAlignFormat, "Reserve Price", auctionListing.getReservePrice());

                    System.out.format("+----------------------+------------------------------------------+%n\n");

                    try {
                        System.out.println("1: Update AuctionListing");
                        System.out.println("2: Delete AuctionListing");
                        System.out.println("3: Back\n");
                        response = 0;
                        while (response < 1 || response > 3) {
                            System.out.print("> ");
                            response = scanner.nextInt();

                            if (response == 1) {
                                doUpdateAuctionListing(auctionListing);
                                break;
                            } else if (response == 2) {
                                doDeleteAuctionListing(auctionListing);
                                response = 3;
                            } else if (response == 3) {
                                break;
                            } else {
                                System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                            }
                        }
                        if (response == 3) {
                            break;
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                        scanner.nextLine();
                    }
                }
            } catch (AuctionListingNotFoundException ex) {
                System.out.println("\033[31m\n[Warning] AuctionListing ID " + auctionListingId + " does not exist.\n\033[34m");
                scanner.nextLine();
                System.out.print("Press Enter to try again...>");
                scanner.nextLine();
            }
        } catch (InputMismatchException ex) {
            System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
            scanner.nextLine();
        }

    }

    public void doUpdateAuctionListing(AuctionListing auctionListing) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*** OAS Admin Panel :: Sales Staff :: View Auction Listing Details :: Update auctionListing***\n");

        if (!(auctionListing.getStatus().equals(AuctionListingStatus.ONGOING) || auctionListing.getStatus().equals(AuctionListingStatus.ONGOING))) {
            System.out.println("\033[31m[Warning] This auction listing is expired or cancelled and cannot be updated!\033[34m");
            System.out.print("\nPress Enter to tgo back...>");
            scanner.next().trim();
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        //changeStartTime are used for updating timer
        Boolean changeStartTime = false;

        System.out.print("Enter Product Name (blank if no change)> ");
        String productName = scanner.nextLine().trim();
        if (productName.length() > 0) {
            auctionListing.setProductName(productName);
        }

        System.out.print("Enter Product Description (blank if no change)> ");
        String descri = scanner.nextLine().trim();
        if (descri.length() > 0) {
            auctionListing.setProductDescription(descri);
        }

        System.out.println("Please follow date format: \033[34myyyy-MM-dd HH:mm:ss\033[0m");

        while (auctionListing.getStatus().equals(AuctionListingStatus.INVISIBLE)) {
            System.out.print("Enter Start Time (blank if no change)> ");
            String dateInString = scanner.nextLine();

            if (dateInString.length() == 0) {
                break;
            } else {
                try {
                    startTime = formatter.parse(dateInString);
                    auctionListing.setStartTime(startTime);
                    changeStartTime = true;
                    break;
                } catch (ParseException ex) {
                    System.out.println("\033[31m\n[Warning] Invalid Date Format! Please try again.\033[34m");
                }
            }
        }

        Date endTime = null;
        //changeEndTime are used for updating timer
        Boolean changeEndTime = false;
        while (true) {
            System.out.print("Enter End Time (blank if no change)> ");
            String dateInString = scanner.nextLine();

            if (dateInString.length() == 0) {
                break;
            } else {
                try {
                    endTime = formatter.parse(dateInString);
                    auctionListing.setEndTime(endTime);
                    changeEndTime = true;
                    break;
                } catch (ParseException ex) {
                    System.out.println("\033[31m\n[Warning] Invalid Date Format! Please try again.\033[34m");
                }
            }
        }

        BigDecimal input;
        while (true) {
            System.out.print("Enter Reserve Price (blank if no change)> ");
            String nextLine = scanner.nextLine();
            //check whether this input is blank
            if (nextLine.length() > 0) {
                Scanner lineScanner = new Scanner(nextLine);
                try {
                    //if this input is not blank, then convert it into bigDecial
                    input = lineScanner.nextBigDecimal();
                    auctionListing.setReservePrice(input);
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
            System.out.print("Enter Starting Price (blank if no change)> ");
            String nextLine = scanner.nextLine();
            if (nextLine.length() > 0) {
                Scanner lineScanner = new Scanner(nextLine);
                try {
                    input = lineScanner.nextBigDecimal();
                    auctionListing.setStartingPrice(input);
                    break;
                } catch (InputMismatchException ex) {
                    System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                    lineScanner.nextLine();
                }
            } else {
                break;
            }
        }

        auctionListingControllerRemote.updateAuctionListing(auctionListing, changeStartTime, changeEndTime);
        System.out.println("\033[31m\n[System] Auction Listing updated successfully!\n\033[34m");
        System.out.print("Press Enter to continue...>");
        scanner.nextLine();
    }

    public void doDeleteAuctionListing(AuctionListing auctionListing) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n*** OAS Admin Panel :: Sales Staff :: View Auction Listing Details :: Delete Auction Listing ***\n");
        System.out.printf("Confirm Delete Auction Listing %s (Auction Listing ID: %d) \n (Enter 'Y' to Delete, otherwise enter others.)", auctionListing.getProductName(), auctionListing.getAuctionListingId());
        System.out.print("> \033[34m");
        String input = scanner.nextLine().trim();

        if (input.equals("Y")) {
            try {
                //cannot delete auction listing which has been already cancelled
                if (auctionListing.getStatus().equals(AuctionListingStatus.CANCELLED)) {
                    System.out.println("\033[31m[Warning] This auction listing is already cancelled and cannot be deleted!\033[34m");
                    System.out.print("Press Enter to continue...>");
                    scanner.nextLine().trim();
                    return;
                }
                //cannot delete auction listing which has been already closed
                if (auctionListing.getStatus().equals(AuctionListingStatus.CLOSED)) {
                    System.out.println("\033[31m[Warning] This auction listing is already closed and cannot be deleted!\033[34m");
                    System.out.print("Press Enter to continue...>");
                    scanner.nextLine().trim();
                    return;
                }
                auctionListingControllerRemote.deleteAuctionListing(auctionListing.getAuctionListingId());
                System.out.println("\n[System] Auction listing [Id:" + auctionListing.getAuctionListingId() + "] isSuccessfully Deleted!\n");
                System.out.print("Press Enter to continue...>");
                scanner.nextLine().trim();

            } catch (AuctionListingNotFoundException ex) {
            }
        } else {
            System.out.println("\033[31m\n[Warning] Auction listing NOT deleted!\n\033[34m");
            System.out.print("Press Enter to continue...>");
            scanner.nextLine().trim();
        }
    }

    public void doViewAllAuctionListings() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("*** OAS Admin Panel :: Sales staff :: View All AuctionListings ***\n");

            List<AuctionListing> auctionListings = auctionListingControllerRemote.retrieveAllAuctionListings();
            System.out.printf("%10s%20s%20s%40s%20s\n", "ID", "Product Name", "Status", "End Time", "Current Max Bid");

            for (AuctionListing auctionListing : auctionListings) {
                System.out.printf("%10s%20s%20s%40s%20s\n", auctionListing.getAuctionListingId(), auctionListing.getProductName(), auctionListing.getStatus(), auctionListing.getEndTime(), auctionListing.getCurrentMaxBiddingPrice());
            }

            System.out.print("\nPress Enter key to continue...> ");
            scanner.nextLine();
        } catch (EmptyListException ex) {
            System.out.println("\033[31m[Warning] " + ex.getMessage() + "\033[34m");
            System.out.print("\nPress Enter to continue...>");
            scanner.nextLine();
        }
    }

    public void doViewAllAuctionListingsWithBidBelowReservePrice() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** OAS Admin Panel :: Sales Staff :: View All Auction Listings With Bid But Below Reserve Price ***\n");

        try {
            List<AuctionListing> auctionListings = auctionListingControllerRemote.retrieveNeedManualAuction();

            System.out.printf("%10s%20s%20s%20s\n", "ID", "Product Name", "Status", "Current Max Bid");

            for (AuctionListing auctionListing : auctionListings) {
                System.out.printf("%10s%20s%20s%20s\n", auctionListing.getAuctionListingId(), auctionListing.getProductName(), auctionListing.getStatus(), auctionListing.getCurrentMaxBiddingPrice());
            }

            while (true) {
                System.out.println("\n1: Assign Winning Bid for Listing with Bids but Below Reserve Price");
                System.out.println("2: Back\n");
                int response = 0;

                while (response < 1 || response > 2) {
                    try {
                        System.out.print("> ");
                        response = scanner.nextInt();

                        if (response == 1) {
                            assignWinningBidforListingwithBidsbutBelowReservePrice();
                        } else if (response == 2) {
                            break;
                        } else {
                            System.out.println("\033[31m\nInvalid option, please try again!\n\033[34m");
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("\033[31m\nInput Error! Please enter a valid number! Try again.\033[34m");
                        scanner.nextLine();
                    }
                }

                if (response == 2) {
                    break;
                }
            }
        } catch (EmptyListException ex) {
            System.out.println("\033[31m[Warning] " + ex.getMessage() + "\033[34m");
            System.out.print("\nPress Enter to continue...>");
            scanner.nextLine();
        }

    }

    public void assignWinningBidforListingwithBidsbutBelowReservePrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the AuctionListing Id that you want to assgin winning bid for:");
        try {
            System.out.print("> ");
            Long auctionListingId = scanner.nextLong();
            scanner.nextLine();

            try {
                AuctionListing auctionListing = auctionListingControllerRemote.retrieveAuctionListingById(auctionListingId, true, false);

                String leftAlignFormat = "| %-20s | %-40s |%n";

                System.out.format("\033[34m\n|------------------ Auction Listing Details  ---------------------|%n\033[0m");
                System.out.format("+----------------------+------------------------------------------+%n");
                System.out.format("| Column               |Details                                   |%n");
                System.out.format("+----------------------+------------------------------------------+%n");

                System.out.printf(leftAlignFormat, "Auction Listing ID", auctionListing.getAuctionListingId());
                System.out.printf(leftAlignFormat, "Product Name", auctionListing.getProductName());
                System.out.printf(leftAlignFormat, "Product Description", auctionListing.getProductDescription());
                System.out.printf(leftAlignFormat, "End Time", auctionListing.getEndTime());
                System.out.printf(leftAlignFormat, "Status", auctionListing.getStatus());
                System.out.printf(leftAlignFormat, "Max Bid", auctionListing.getCurrentMaxBiddingPrice());
                System.out.printf(leftAlignFormat, "Starting Price", auctionListing.getStartingPrice());
                System.out.printf(leftAlignFormat, "Reserve Price", auctionListing.getReservePrice());

                System.out.format("+----------------------+------------------------------------------+%n\n");

                Customer highestBidCustomer = null;
                //assign the highest bid
                List<Bid> bids = auctionListing.getBiddings();
                for (Bid each : bids) {
                    if (each.getBidId().equals(auctionListing.getMaxBidId())) {
                        highestBidCustomer = each.getCustomer();
                        break;
                    }
                }

                System.out.println("\nEnter 'Y' if you want to assign the HIGHEST bid: as the winning bid. \nEnter 'N' if you want to mark the listing as NO WINNING BID");
                System.out.print("\033[0m> \033[34m");
                String input = scanner.nextLine().trim();

                if (input.equals("Y")) {
                    auctionListingControllerRemote.closeAuctionListingManually(auctionListingId, highestBidCustomer.getCustomerId());
                    System.out.println("\n[System] The highest bid has been assigned as the winning bid!");
                    System.out.print("\nPress Enter to continue...>");
                    scanner.nextLine();
                } else if (input.equals("N")) {
                    auctionListingControllerRemote.closeAuctionListingManually(auctionListingId, 0l);
                    System.out.println("\n[System] The highest bid has NOT been assigned as the winning bid!");
                    System.out.print("\nPress Enter to continue...>");
                    scanner.nextLine();
                } else {
                    System.out.println("\033[31m[Warning] Invalid input!\033[34m");
                }
            } catch (AuctionListingNotFoundException ex) {
                System.out.println("\033[31m\n[Warning] Auction Listing ID " + auctionListingId + " does not exist!\033[34m");
                System.out.print("\nPress Enter to try again...>");
                scanner.nextLine();
            }
        } catch (InputMismatchException ex) {
            System.out.println("\033[31m\nInput Error! Please enter a valid number!\033[34m");
            scanner.nextLine();
        }

    }
}
