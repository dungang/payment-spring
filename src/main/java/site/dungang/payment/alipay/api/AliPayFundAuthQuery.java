package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 预授权查询接口
 * @author dungang
 * 参数：auth_no, out_order_no
 *
 */
public class AliPayFundAuthQuery extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "alipay.fund.auth.query");
		return param;
	}

}
