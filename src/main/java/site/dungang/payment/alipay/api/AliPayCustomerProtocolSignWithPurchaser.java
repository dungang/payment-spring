package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 支付宝合作伙伴签约接口(采购商版)
 * 跳转接口：需要在浏览器中重定向
 * 完成签约之后，需要调用签约查询接口[AliPayCustomerProtocalQuery]查询签约状态
 * @author dungang
 * 参数：email
 */
public class AliPayCustomerProtocolSignWithPurchaser extends AliPay {

	@Override
	public Map<String, String> buildParam() {

		//返回结果是url
		this.resultIsUrl = true;
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "sign_protocol_with_partner");
		return param;
	}

}
