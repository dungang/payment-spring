package site.dungang.payment.alipay;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import site.dungang.payment.base.AbstractPay;
import site.dungang.payment.base.IPaymentProperties;
import site.dungang.payment.utils.PaymentHttpUtils;
import site.dungang.payment.utils.PaymentResponse;
import site.dungang.payment.utils.XMLUtil;


public abstract class AliPay extends AbstractPay {

	private static Logger logger = LoggerFactory.getLogger(AliPay.class);
	
	/**
	 * 支付宝的配置对象
	 */
	protected AliPaymentProperties paymentProperties;
	
	@Override
	public Map<String, Object> process(Map<String, Object> resultMap) {
		return resultMap;
	}

	@Override
	public Map<String, Object> execute(Map<String, Object> paramMap) {
		
		//执行之前对参数处理
		beforeExecute(paramMap);
		
		Map<String,Object> bizMap = new HashMap<String,Object>();
		
		//初始化协议参数
		bizMap.put("partner", this.paymentProperties.getPartnerId());
		bizMap.put("_input_charset", this.paymentProperties.getCharset());
		bizMap.put("sign_type", this.paymentProperties.getSignType());
		
		//覆盖支付接口业务参数
		bizMap.putAll(this.buildParam());
		
		//覆盖系统业务参数
		bizMap.putAll(paramMap);
		
		prefixBaseUrlToNotifyAndReturnUrl(bizMap);
		
		Map<String,Object> params = AlipayCore.generateWithSign(bizMap,this.paymentProperties);
		String strUrl = this.paymentProperties.getApiGate() + "?" + AlipayCore.buidQuery(params,paymentProperties.getCharset());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//如果是返回跳转的支付地址
		if(this.resultIsUrl) {
			resultMap.put("url", strUrl);
			logger.info("返回的结果：" + strUrl);
		} else {
			//执行接口调用
			logger.debug("支付接口请求的地址："+strUrl);	
			PaymentResponse rsp = PaymentHttpUtils.sendHttpGet(strUrl);
			logger.debug("支付宝返回的文档类型："+rsp.getContentType());
			if(rsp.is2xxSuccess() && rsp.getContentType().indexOf("text/xml")>-1) {
				resultMap = XMLUtil.parseXml(rsp.getBody());
				logger.debug("xml2json: " + resultMap.toString());
			}
			logger.info("返回的结果：" + rsp.getBody());
		}
		return this.process(resultMap);
	}

	@Override
	public void setPaymentProperties(IPaymentProperties properties) {
		this.paymentProperties = (AliPaymentProperties) properties;
		
	}

	@Override
	public IPaymentProperties getPaymentProperties() {
		return this.paymentProperties;
	}

	/**
	 * 给通知和跳转地址添加对应的前缀
	 * @param params
	 * @return
	 */
	protected Map<String, Object> prefixBaseUrlToNotifyAndReturnUrl(Map<String,Object> params){
		if(params.containsKey("return_url")) {
			params.put("return_url",  this.paymentProperties.getReturnBaseUrl() + params.get("return_url"));
		}
		if(params.containsKey("notify_url")) {
			params.put("notify_url",  this.paymentProperties.getNotifyBaseUrl() + params.get("notify_url"));
		}
		return params;
	}
}
