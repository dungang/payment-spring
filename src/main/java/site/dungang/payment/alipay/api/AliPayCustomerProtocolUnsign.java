package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 商户系统调用该接口实现对某一个签约客户的解约操作。譬如：解除批量退款服务。
 * @author dungang
 * 参数： trans_account, user_email
 */
public class AliPayCustomerProtocolUnsign extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "customer_unsign");
		param.put("biz_type", "10004");
		return param;
	}

}
