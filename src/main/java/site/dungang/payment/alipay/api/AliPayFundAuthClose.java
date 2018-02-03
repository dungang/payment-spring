package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 预授权订单关闭接口
 * {@link https://doc.open.alipay.com/docs/doc.htm?articleId=106722&docType=1}
 * @author dungang
 *
 */
public class AliPayFundAuthClose extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "alipay.fund.auth.close");
		return param;
	}

}
