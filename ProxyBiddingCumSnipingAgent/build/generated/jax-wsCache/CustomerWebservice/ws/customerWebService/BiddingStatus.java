
package ws.customerWebService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for biddingStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="biddingStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PENDING"/>
 *     &lt;enumeration value="WON"/>
 *     &lt;enumeration value="LOST"/>
 *     &lt;enumeration value="CANCEL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "biddingStatus")
@XmlEnum
public enum BiddingStatus {

    PENDING,
    WON,
    LOST,
    CANCEL;

    public String value() {
        return name();
    }

    public static BiddingStatus fromValue(String v) {
        return valueOf(v);
    }

}
