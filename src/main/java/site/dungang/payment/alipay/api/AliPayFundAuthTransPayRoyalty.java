package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 预授权冻结转支付分账接口
 * 比如冻结100 ，调用接口，支付50元， 剩下 50元
 * 
 * 调用统一下单并支付接口(alipay.acquire.createandpay)将预授权订单转支付
 * 请求参数中，订单业务类型（product_code）字段传FUND_TRADE_FAST_PAY
 * 可部分解冻转支付，累计转支付金额不得超过授权冻结总金额
 * 若预授权冻结时传入payee_logon_id（收款方支付宝账号）或者payee_user_id（收款方支付宝用户号），则转支付请求参数seller_id（卖家支付宝用户号）或seller_email（卖家支付宝账号）必须与冻结传入的一致
 * 请求参数buyer_id（买家支付宝用户号）不可为空，为授权用户的支付宝账号uid
 * 请求参数auth_no（授权号）传入预授权冻结返回的auth_no
 * 业务处理，记录out_trade_no（商户订单号）及trade_no（支付宝交易号），用于后续查单或者退款
 * {@link https://doc.open.alipay.com/docs/doc.htm?articleId=106722&docType=1}
 * @author dungang
 * 参数：必须回传 auth_no(预授权编号) out_trade_no(商户网站唯一订单号)
 * out_trade_no,subject,totel_fee
 * 分润
 * royalty_type(分账类型，默认是ROYALTY,不用传参，默认自动添加)
 * royalty_parameters(分账明细)
 * 
 * 分账明细格式：json
 * <code>
 * [
 * 	{
 * 		"serialNo":"分账序列-整型，执行的顺序",
 * 		"outRelationId": "分账关联号，可以位空则位商户订单号+序列号",
 * 		"transOutType":"转出账户类型：默认userId",
 * 		"transOut":"出账支付宝用户号",
 * 		"transInType": "转入账户类型：默认userId"
 * 		"transIn":"入账支付宝用户号",
 * 		"batchNo":"批次号 可空",
 * 		"amount": "分账金额",
 * 		"desc": "分账描述 可空"
 * 	}
 * ]
 * </code>
 * 
 */
public class AliPayFundAuthTransPayRoyalty extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		//组装业务参数
		Map<String, String> param  = new HashMap<String, String>();
		param.put("service", "alipay.acquire.createandpay");
		param.put("product_code","FUND_TRADE_FAST_PAY");
		param.put("seller_id",this.paymentProperties.getPartnerId());
		param.put("seller_email",this.paymentProperties.getAccount());
		param.put("operator_type","1"); //操作人的类型 支付宝|商家
		param.put("royalty_type", "ROYALTY");
		return param;
	}

}
