package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 预授权转支付退款
 * 该接口用于将之前转交易支付出去的资金原路退回
 * @author dungang
 * 参数： out_trade_no（商户网站一定订单号）, trade_no(支付宝交易编号)
 * refund_amount(退款金额) out_request_no
 */
public class AliPayFundAuthTransPayRefund extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		//组装业务参数
		Map<String, String> param  = new HashMap<String, String>();
		param.put("service", "alipay.acquire.refund");
		param.put("operator_type", "1");
		return param;
	}

}
