package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 订创建并申请冻结接口
 * 跳转接口：需要在浏览器中重定向
 * 调用资金授权订单创建并申请冻结接口(alipay.fund.auth.create.freeze.apply)。
 * 请求参数中，业务产品码product_code设为FUND_PRE_AUTH。
 * 请求参数中，业务场景码scene_code根据下图需要传入。
 * 请求参数中，out_order_no（商户授权资金订单号）标识每笔不同授权订单，保证每笔唯一
 * 请求参数中，out_request_no（商户请求流水号）标识本次资金操作请求，保证每次请求唯一
 * 若签约花呗渠道，需要使用，则必须在请求参数中传入payee_logon_id（收款方支付宝账号）或者payee_user_id（收款方支付宝用户号），两者必传其一
 * 请求参数中若传入payee_logon_id（收款方支付宝账号）或者payee_user_id（收款方支付宝用户号），在转支付时会校验收款方是否与此一致
 * 同步返回is_success为T，仅表示请求接口调用成功，不代表冻结处理成功；业务处理以异步通知为准
 * 返回数据业务处理注意，记录
 * auth_no（支付宝授权订单号）、
 * out_order_no（商户授权资金订单号）、
 * operation_id（支付宝资金操作流水）、
 * out_request_no（商户请求流水号）以便后续查询、撤销、关闭使用
 * {@link https://doc.open.alipay.com/docs/doc.htm?articleId=106722&docType=1}
 * @author dungang
 * 参数：return_url,notify_url,outer_order_no,out_request_no,
 * order_title,amount
 * 申请成功后，需要后台保存 auth_no 支付宝授权订单号
 */
public class AliPayFundAuthCreateFreezeApply extends AliPay {


	@Override
	public Map<String, String> buildParam() {
		//返回结果是url
		this.resultIsUrl = true;
		
		//组装业务参数
		Map<String, String> param  = new HashMap<String, String>();
		param.put("service", "alipay.fund.auth.create.freeze.apply");
		param.put("product_code","FUND_PRE_AUTH");
		param.put("scene_code", "HOTEL");
		//收款方账户
		param.put("payee_logon_id", this.paymentProperties.getAccount());
		//收款方id
		param.put("payee_user_id", this.paymentProperties.getPartnerId());
		param.put("pay_mode", "PC");
		return param;
	}

}
