package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 交易关闭接口
 * @author dungang
 * 传递的参数：trade_no,out_order_no
 */
public class AliPayCloseOrder extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "close_trade");
		param.put("trade_role", "S");
		return param;
	}

}
