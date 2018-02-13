
package ws.customerWebService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for auctionListingStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="auctionListingStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVISIBLE"/>
 *     &lt;enumeration value="ONGOING"/>
 *     &lt;enumeration value="NEEDINTERVENED"/>
 *     &lt;enumeration value="CLOSED"/>
 *     &lt;enumeration value="CANCELLED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "auctionListingStatus")
@XmlEnum
public enum AuctionListingStatus {

    INVISIBLE,
    ONGOING,
    NEEDINTERVENED,
    CLOSED,
    CANCELLED;

    public String value() {
        return name();
    }

    public static AuctionListingStatus fromValue(String v) {
        return valueOf(v);
    }

}
