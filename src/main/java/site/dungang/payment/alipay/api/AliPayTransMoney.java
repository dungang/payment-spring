package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.OpenAliPay;

/**
 * 单笔转账到支付宝账户接口是基于支付宝的资金处理能力，为了满足支付宝商家向其他支付宝账户转账的需求，
 * 针对有部分开发能力的商家，提供通过API接口完成支付宝账户间的转账的功能。 
 * 该接口适用行业较广，比如商家间的货款结算，商家给个人用户发放佣金等。
 * 
 * @author dungang
 * {@link https://docs.open.alipay.com/309}
 * 参数 ： payee_account(收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。)
 * out_biz_no(商户转账唯一订单号。发起转账来源方定义的转账单据ID，用于将转账回执通知给来源方。 )
 * 返回结果:order_id(支付宝转账单据号)
 */
public class AliPayTransMoney extends OpenAliPay {

	@Override
	public Map<String, String> buildParam() {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("payee_type", "ALIPAY_LOGONID");
		param.put("method", "alipay.fund.trans.toaccount.transfer");
		param.put("sign_type", "RSA2");
		return param;
	}
}
