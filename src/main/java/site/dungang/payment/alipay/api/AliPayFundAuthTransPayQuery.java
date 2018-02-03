package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 预授权转支付查询接口
 * 该接口用于查询转交易订单信息
 * @author dungang
 * 参数： out_trade_no（商户网站一定订单号）, trade_no(支付宝交易编号)
 */
public class AliPayFundAuthTransPayQuery extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		//组装业务参数
		Map<String, String> param  = new HashMap<String, String>();
		param.put("service", "alipay.acquire.query");
		return param;
	}

}
