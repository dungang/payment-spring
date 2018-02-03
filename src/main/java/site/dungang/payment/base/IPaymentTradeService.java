package site.dungang.payment.base;

import java.util.Map;


public interface IPaymentTradeService {

	public int save(PaymentTrade sysPaymentDealDetail);

	public PaymentTrade selectByPrimaryKey(String id);

	public PaymentTrade getPaymentDealDetailByOrderId(String orderId);
	
	public PaymentTrade getOnePaymentDealDetail(Map<String,String> map);
	
	public int update(PaymentTrade sysPaymentDealDetail);
}
