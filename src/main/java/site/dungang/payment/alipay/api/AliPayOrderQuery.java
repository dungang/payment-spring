package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 单笔交易订单查询
 * @author dungang
 * 传递的参数：trade_no,out_order_no
 */
public class AliPayOrderQuery extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "single_trade_query");
		return param;
	}
}
