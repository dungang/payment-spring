package site.dungang.payment.base;

import java.util.Map;

/**
 * 支付渠道服务
 * @author dungang
 *
 */
public interface IPaymentDealService {

	/**
	 * 执行流水服务 并返回结果
	 * @param creator
	 * @return
	 */
	Map<String, Object> execute(IPaymentDealCreator creator);
}
