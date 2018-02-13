package ejb.session.ws;

import ejb.session.stateless.BidControllerLocal;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import util.exception.GeneralException;

/**
 *
 * @author yingshi
 */
@WebService(serviceName = "BidWebservice")
@Stateless()
public class BidWebservice {

    @EJB
    private BidControllerLocal bidControllerLocal;

    /**
     * Web service operation
     *
     * @param auctionId
     * @param customerId
     * @param maxAmount
     * @throws util.exception.GeneralException
     */
    @WebMethod(operationName = "configureProxyBidding")
    public void configureProxyBidding(@WebParam(name = "auctionId") Long auctionId, @WebParam(name = "customerId") Long customerId, @WebParam(name = "maxAmount") BigDecimal maxAmount) throws GeneralException {
        bidControllerLocal.createProxyBid(maxAmount, customerId, auctionId);
    }

    /**
     * Web service operation
     *
     * @param auctionId
     * @param customerId
     * @param maxAmount
     * @param date
     */
    @WebMethod(operationName = "configureSniping")
    @Oneway
    public void configureSniping(@WebParam(name = "auctionId") Long auctionId, @WebParam(name = "customerId") Long customerId, @WebParam(name = "maxAmount") BigDecimal maxAmount, @WebParam(name = "date") Date date) {
        bidControllerLocal.createSnippingBid(customerId, auctionId, maxAmount, date);
    }

}
