package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 即时到账接口
 * 跳转接口：需要在浏览器中重定向
 * @author dungang
 * {@link https://docs.open.alipay.com/62/104743}
 * <code>
 * Map<String, String> param = new HashMap<String, String>();
 * param.put("out_trade_no", "orderId");
 * param.put("subject", "test123");
 * param.put("body", "即时到账测试");
 * param.put("total_fee", "0.01");
 * param.put("quantity", "1");
 * param.put("notify_url", "/pay/notify4Pay");
 * param.put("return_url", "/order/detail?id="+orderId);
 * paymentFactory.setPaymentType("AliPay");
 * Map<String, String> resultMap  = paymentFactory.work("CreateDirectPayByUser", param);
 * if(resultMap.containsKey("url")) {
 *  //跳转地址：url
 * }
 * </code>
 */
public class AliPayCreateDirectPayByUser extends AliPay {
	
	@Override
	public Map<String, String> buildParam() {
		//返回结果是url
		this.resultIsUrl = true;
		
		//组装业务参数
		Map<String, String> param  = new HashMap<String, String>();
		param.put("service", "create_direct_pay_by_user");
		param.put("seller_id",this.paymentProperties.getPartnerId());
		param.put("payment_type", "1");
		return param;
	}
}
