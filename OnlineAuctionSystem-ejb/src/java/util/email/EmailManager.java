package util.email;

import entity.AuctionListing;
import entity.Bid;
import entity.Customer;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import util.enumeration.BidType;

public class EmailManager {

    private final String emailServerName = "mailauth.comp.nus.edu.sg";
    private final String mailer = "JavaMailer";
    private String smtpAuthUser;
    private String smtpAuthPassword;

    public EmailManager() {
    }

    public EmailManager(String smtpAuthUser, String smtpAuthPassword) {
        this.smtpAuthUser = smtpAuthUser;
        this.smtpAuthPassword = smtpAuthPassword;
    }

    public Boolean emailWonBiddingNotification(AuctionListing auctionListing, String fromEmailAddress, String toEmailAddress) {
        String emailBody = "";
        emailBody += "Dear Customer " + auctionListing.getWinner().getUsername() + ",\n";
        emailBody += "  You have won a new Auction Listing!" + "\n\n";
        emailBody += "      Acution Listing ID: " + auctionListing.getAuctionListingId() + "\n";
        emailBody += "      Product Name: " + auctionListing.getProductName() + "\n";
        emailBody += "      Product Details: " + auctionListing.getProductDescription() + "\n";
        emailBody += "      Winning Price: " + auctionListing.getWinningprice() + "\n";
        emailBody += "      Date/Time: " + (new Date()) + "\n";
        emailBody += "  Please log in to CrazyBid.com to assign delivery address to ship the product!\n\n";
        emailBody += "Sincerely,\n";
        emailBody += "CrazyBid.com\n";

        try {
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailServerName);
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");
            javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            Message msg = new MimeMessage(session);

            if (msg != null) {
                msg.setFrom(InternetAddress.parse(fromEmailAddress, false)[0]);
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress, false));
                msg.setSubject("Won Auction Listing!!");
                msg.setText(emailBody);
                msg.setHeader("X-Mailer", mailer);

                Date timeStamp = new Date();
                msg.setSentDate(timeStamp);

                Transport.send(msg);
                System.out.println("**Email sent in Email Manager");

                return true;
            } else {
                return false;
            }
        } catch (MessagingException e) {
            e.printStackTrace();

            return false;
        }
    }

    //when premium customer's snipping/proxy is outbid
    public Boolean emailOutOfBidNotification(AuctionListing auctionListing, String fromEmailAddress, Customer customer, Bid myBid) {
        String emailBody = "";
        if (myBid.getBidtype().equals(BidType.NORMAL)) {
            emailBody += "Dear Customer " + customer.getUsername() + ",\n";
            emailBody += "  You are outbid for the auction listing!" + "\n\n";
            emailBody += "      Acution Listing ID: " + auctionListing.getAuctionListingId() + "\n";
            emailBody += "      Product Name: " + auctionListing.getProductName() + "\n";
            emailBody += "      Product Details: " + auctionListing.getProductDescription() + "\n";
            emailBody += "      Winning Bidding: " + auctionListing.getCurrentMaxBiddingPrice() + "\n";
            emailBody += "      Date/Time: " + (new Date()) + "\n\n";

            emailBody += "  Really want to win the bid? Try our Proxy-Bidding-cum-Sniping Agent powered by AI.SG. \n\n";

            emailBody += "Sincerely,\n";
            emailBody += "CrazyBid.com\n";
        } else {
            emailBody += "Dear Premium Customer " + customer.getUsername() + ",\n";
            emailBody += "  You are outbid for the auction listing!" + "\n\n";
            emailBody += "      Acution Listing ID: " + auctionListing.getAuctionListingId() + "\n";
            emailBody += "      Product Name: " + auctionListing.getProductName() + "\n";
            emailBody += "      Product Details: " + auctionListing.getProductDescription() + "\n";
            emailBody += "      Bidding Price: " + auctionListing.getCurrentMaxBiddingPrice() + "\n";
            emailBody += "      Date/Time: " + (new Date()) + "\n";

            emailBody += "   The maximum amount you specify for the Sniping bid is less than the current max bid. You can log in to CrazyBid :: Proxy cum Snipping Agent to place a higher bid!\n";
            emailBody += "\n";
            emailBody += "Sincerely,\n";
            emailBody += "CrazyBid.com\n";
        }

        try {

            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailServerName);
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");

            javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            Message msg = new MimeMessage(session);

            if (msg != null) {
                msg.setFrom(InternetAddress.parse(fromEmailAddress, false)[0]);
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customer.getEmail(), false));
                msg.setSubject("Bid failure.");
                msg.setText(emailBody);
                msg.setHeader("X-Mailer", mailer);
                Date timeStamp = new Date();
                msg.setSentDate(timeStamp);
                Transport.send(msg);
                System.out.println("**Email sent in Email Manager");
                return true;
            } else {
                return false;
            }

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
