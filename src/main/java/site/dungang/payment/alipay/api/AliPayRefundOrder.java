package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;


import site.dungang.payment.alipay.AliPay;
import site.dungang.payment.utils.DateUtils;

/**
 * 支付宝无密退款接口
 * 
 * @author dungang
 *
 */
public class AliPayRefundOrder extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("service", "refund_fastpay_by_platform_nopwd");
		paramMap.put("refund_date", DateUtils.getCurrentYmdHMS());
		paramMap.put("batch_num", "1");
		return paramMap;
	}

}
