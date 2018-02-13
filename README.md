# Online-Auction-System
## Part 0: Overview
This is a project only focusing on backend development using Java EE. It uses Command Line Interface as UI.

The Online Auction System (“OAS”) operates a first-party online auction service. Employees are responsible for placing product items for sales through the creation of auction listings. Customers must register for an account and purchase credit packages. Customers should use these credita to place bid for product items.

There is also an automated proxy bidding cum sniping agent for premium customers, which can monitor their bids round-the-clock and constantly remind them whether they are out-bidded, in particular at the last minute. Customers whom use the software agent would need to pay an additional service fees. 

## Part 1: Architecture and Design
### 1.1. System Architecture
1 singleton session bean is used for data initialization. 8 stateless session beans implement different business methods. 2 remote client, OAS Admin Panel and OAS Auction Client are used by CrazyBid.com’s employees and customers respectively. Proxy Bidding cum Sniping Agent completes premium functions via SOAP Web Services.
<p>
  <img src="images/high-level architecture.png" width="1000"/>
</p>

### 1.2. UML Diagram of database
<p>
  <img src="images/UML Diagram of database.png" width="1000"/>
</p>

## Part 2: System Functions 
### 2.1. Programmatic timers
- Timers for auction listings (TimerConfig: Auction listing Id)
These timers control the starting and closing of auction listings. When the timer expires and the auction listing status is “Invisible” (i.e. start time is reached), a new timer will be created, with end time as expiration and meanwhile, change the status to “Ongoing”. When the timer expires and the auction listing status is “Ongoing” (i.e. end time is reached), “CloseAuctionListingAutomate” function will be executed.
- Timers for sniping bids (TimerConfig: SnipInfo class)
A helper class SnipInfo (implements Serializable) stores the information of a sniping bid, such as the time to place the bid and the max amount customers would like to place. The instances of SnipInfo are stored in timers and will be used to create sniping bids when timers expire.
### 2.2. Email notification
- Winning and losing notification
The winner and losers of each auction listing will receive an email notification about the outcome. Winners will also be reminded to assign shipping address.
- Sniping / proxy bids placing failed notification
When configuring the sniping/ proxy bid but the max amount specified by the customer is not enough to win the bid, an email notification will be sent as a reminder.
### 2.3. Bidding features
- View all my bids
This function is added for the convenience of customers to check all the bids they placed.
- Place new bids
Customer are only allowed to place a bid amount that is equal to or higher than the current max bid amount plus a default value. We will check if the custoemr had bidded for this auction before. If no, a new bid will be created. If yes, the old bid will be updated.
- Unique bid given a customer and an auction listing
In the system, we assure that given a certain customer and auction listing, there is a unique bid associated. That means when a customer increment his bid, or he becomes premium customer and place a proxy/sniping bid on the same auction listing, we will only update the old bid and won’t create new bid entity.
- Check before configure proxy/ sniping bidding
Before configuring proxy/ sniping bidding for customer, we check whether the highest bid was placed by the customer himself. If yes, we won’t update the bid.
## Part 3: User Interface
### 3.1. Color highlights
Different colors are used to highlight different messages. For example, red for warning message and blue for user-input and some important messages.
### 3.2. Table plotting
For all the “view details” method, we print out a table for clearer presentation of information:
### 3.3. Use Sequence number instead of an actual entity ID
For some methods, we print the sequence number of the list instead of the actual entity ID, thus preventing possible invalid input from users.
