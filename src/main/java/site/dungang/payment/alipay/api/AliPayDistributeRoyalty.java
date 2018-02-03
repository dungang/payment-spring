package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 分润格式支持以下两种（第一种平级分润格式默认卖家 seller_email 或卖家 UID 为分润付款方。用户 UID 是一串以字母 uid 开头，后面跟着以 2088 开头的 16 位数字的字符串）：
 * (1)	平级分润
 * z	收款方支付宝账号 1^金额 1^备注 1|收款方支付宝账号 2^金额 2^备注 2...|收
 * 款方支付宝账号 N^金额 N^备注 N
 * z	收款方 UID1^金额 1^备注 1|收款方 UID2^金额 2^备注 2...|收款方 UIDN^金额
 * N^备注 N
 * (2)	多级分润
 * z付款方支付宝账号^收款方支付宝账号 1^金额 1^备注 1|付款方支付宝账号 1^ 收款方支付宝账号 2^金额 2^备注 2...|付款方支付宝账号^收款方支付宝账号
 * N^金额 N^备注 N
 * 多级分润接口(志能配合即时到账接口的订单)
 * @author dungang
 *
 */
public class AliPayDistributeRoyalty extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "distribute_royalty");
		param.put("royalty_type", "10");//目前只支持10类型
		return param;
	}

}
