package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 支付宝三方协议查询接口
 * @author dungang
 * 参数:user_email(支付宝账户) 
 * 返回用户id：user_id 代理人支付宝唯一号 2088开通的16位正数
 * ps：请保存user_id方便转支付使用
 */
public class AliPayCustomerProtocolQuery extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "query_customer_protocol");
		param.put("biz_type", "10004"); //机票代扣业务
		return param;
	}

}
