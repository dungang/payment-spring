package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 预授权流水查询
 * {@link https://doc.open.alipay.com/docs/doc.htm?articleId=106722&docType=1}
 * @author dungang
 *
 */
public class AliPayFundAuthOperationQuery extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		//组装业务参数
		Map<String, String> param  = new HashMap<String, String>();
		param.put("service", "alipay.fund.auth.operation.query");
		return param;
	}

}
