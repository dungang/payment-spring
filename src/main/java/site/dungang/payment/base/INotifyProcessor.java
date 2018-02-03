package site.dungang.payment.base;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 异步通知处理接口
 * 
 * @author dungang
 *
 */
public interface INotifyProcessor {

	/**
	 * 支付渠道通知的参数
	 * 
	 * @param paymentPostParam
	 *            支付网关post的参数，接口不同可能不同
	 * @param dealDetail
	 *            系统流水模型，根据本次通知查询得到，可能为空
	 */
	void process(Map<String, Object> paymentPostParam, PaymentTrade dealDetail);

	/**
	 * 成功相应支付网关
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	String responseSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 失败响应支付网关
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	String responseFail(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 对支付网关post的参数进行预处理
	 * 
	 * @param paymentPostParam
	 * @return
	 */
	Map<String, Object> preDealParams(Map<String, Object> paymentPostParam);

	/**
	 * 支付渠道的类型
	 * 
	 * @return
	 */
	String paymentType();

	/**
	 * 获取商户订单交易流水号
	 * 
	 * @return
	 */
	String getOutTradeNo();

	/**
	 * 获取支付渠道的业务订单号
	 * 
	 * @return
	 */
	String getTradeNo();
}
