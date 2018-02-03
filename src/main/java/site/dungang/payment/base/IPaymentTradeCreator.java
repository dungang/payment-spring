package site.dungang.payment.base;

import java.util.Map;

/**
 * 执行渠道交易创建者
 * 
 * @author dungang
 *
 */
public interface IPaymentTradeCreator {

	/**
	 * 这里只传递 payment  
	 * <dl>
	 * <dd>1.如果返回空，则不记录流水，既不查询也不创建</dd>
	 * <dd>2.如果返回有值，根据传递的参数先查询未处理的流水，如果没有 则创建一条</dd>
	 * <dl>
	 * 
	 * @return
	 */
	public PaymentTrade creatQueryPaymentDeal();

	/**
	 * 
	 * 构造支付的参数 
	 * <dl>
	 * <dd>1.服务根据数据库查询的结果SysPaymentDealDetail作为参数</dd>
	 * <dd>2.creatQueryPaymentDeal会影响这里的参数</dd>
	 * </dl>
	 * @param deal
	 * @param dependencydeal
	 * @return
	 */
	public Map<String, Object> createPaymentParam(PaymentTrade deal,PaymentTrade dependencydeal);
	
	
	/**
	 * 同步返回的支付宝交易单号名称
	 * 告诉支付渠道服务交易单号的名称
	 * 根据不同的接口，交易单号的名称会发生变化。
	 * 比如： trade_no,order_id,auth_no
	 * @return
	 */
	public String createTradeNoName();

}
