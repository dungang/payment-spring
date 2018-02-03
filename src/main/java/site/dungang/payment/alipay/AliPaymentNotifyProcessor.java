package site.dungang.payment.alipay;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.dungang.payment.base.INotifyProcessor;


/**
 * 支付宝通知处理抽象类 默认是输出字符串的结果：sucess:fail
 * 
 * @author dungang
 *
 */
public abstract class AliPaymentNotifyProcessor implements INotifyProcessor {

	@Override
	public Map<String, Object> preDealParams(Map<String, Object> paymentPostParam) {
		// Map<String, String> fields = new HashMap<String,String>();
		// fields.put("trade_no", paymentPostParam.get("trade_no"));
		// fields.put("out_trade_no", paymentPostParam.get("out_trade_no"));
		// return fields;
		return paymentPostParam;
	}

	@Override
	public String responseSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "success"; // 请不要修改或删除
	}

	@Override
	public String responseFail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "fail";
	}

	@Override
	public String paymentType() {
		return "AliPay";
	}

	@Override
	public String getOutTradeNo() {
		return "out_trade_no";
	}

	@Override
	public String getTradeNo() {
		return "trade_no";
	}

}
