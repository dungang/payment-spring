package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.OpenAliPay;

/**
 * 查询转账订单接口
 * 商户可通过该接口查询转账订单的状态、支付时间等相关信息，主要应用于B2C转账订单查询的场景
 * @author dungang
 * 
 * {@link https://docs.open.alipay.com/309}
 * 参数：out_biz_no(商户转账唯一订单号：发起转账来源方定义的转账单据ID,和支付宝转账单据号不能同时为空。当和支付宝转账单据号同时提供时，将用支付宝转账单据号进行查询，忽略本参数)
 * ,order_id(支付宝转账单据号：和商户转账唯一订单号不能同时为空)
 *
 */
public class AliPayQueryTransMoney extends OpenAliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("method", "alipay.fund.trans.order.query");
		param.put("sign_type", "RSA2");
		return param;
	}

}
