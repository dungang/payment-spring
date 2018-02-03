package site.dungang.payment.alipay.api;

import java.util.HashMap;
import java.util.Map;

import site.dungang.payment.alipay.AliPay;

/**
 * 预授权撤销接口
 * 比如说冻结100元，你冻结转支付50元，然后撤销订单，就会退款支付的50元，再解冻剩下的50元，订单状态变成关闭
 * 撤销会清算，把支付的、冻结的，全部退回给用户
 * {@link https://doc.open.alipay.com/docs/doc.htm?articleId=106722&docType=1}
 * @author dungang
 *
 */
public class AliPayFundAuthCancel extends AliPay {

	@Override
	public Map<String, String> buildParam() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("service", "alipay.fund.auth.cancel");
		return param;
	}

}
