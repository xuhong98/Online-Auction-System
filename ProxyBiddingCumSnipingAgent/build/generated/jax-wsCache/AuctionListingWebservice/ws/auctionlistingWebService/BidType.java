
package ws.auctionlistingWebService;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bidType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="bidType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NORMAL"/>
 *     &lt;enumeration value="SNIPING"/>
 *     &lt;enumeration value="PROXY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "bidType")
@XmlEnum
public enum BidType {

    NORMAL,
    SNIPING,
    PROXY;

    public String value() {
        return name();
    }

    public static BidType fromValue(String v) {
        return valueOf(v);
    }

}
