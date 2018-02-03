package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 解冻接口
 * 解冻的话仅仅就是解冻,金额解冻完成，订单关闭
 * {@link https://doc.open.alipay.com/docs/doc.htm?articleId=106722&docType=1}
 * @author dungang
 * 参数：auth_no(支付宝授权订单号),out_request_no(商户请求流水号),amount,remark
 *
 */
public class AliPayFundAuthUnfreeze extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		
		//组装业务参数
		Map<String, String> param  = new HashMap<String, String>();
		param.put("service", "alipay.fund.auth.unfreeze");
		return param;
	}

}
