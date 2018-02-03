package site.dungang.payment.alipay;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import site.dungang.payment.utils.DateUtils;
import site.dungang.payment.utils.JsonUtils;
import site.dungang.payment.utils.PaymentHttpUtils;
import site.dungang.payment.utils.PaymentResponse;
import site.dungang.payment.utils.XMLUtil;



public abstract class OpenAliPay extends AliPay {

	private static Logger logger = LoggerFactory.getLogger(OpenAliPay.class);

	private static String[] protocalParamNames = new String[] {
			"app_id","method","format",
			"charset","sign_type","sign",
			"timestamp","version","notify_url",
			"biz_content","return_url"
			};
	@Override
	public Map<String,Object> execute(Map<String,Object> paramMap) {
		//执行之前对参数处理
		beforeExecute(paramMap);
		
		Map<String,Object> bizMap = new HashMap<String,Object>();
		
		//初始化协议参数
		bizMap.put("app_id", this.paymentProperties.getAppId());
		bizMap.put("charset", this.paymentProperties.getCharset());
		bizMap.put("sign_type", this.paymentProperties.getSignType());
		bizMap.put("timestamp", DateUtils.getCurrentYmdHMS());
		bizMap.put("format", "XML");
		bizMap.put("version", "1.0");
		//覆盖支付接口业务参数
		bizMap.putAll(this.buildParam());
		
		//覆盖系统业务参数
		bizMap.putAll(paramMap);
		
		prefixBaseUrlToNotifyAndReturnUrl(bizMap);
		
		//覆盖mapi习惯的传参 openapi 使用的是method
		if(bizMap.containsKey("service")) {
			bizMap.put("method", bizMap.get("service"));
			bizMap.remove("service");
		}
		
		
		Map<String,Object> protocolParam = this.getProtocolParamAndCleanProtocolParam(bizMap);
		
		String bizContent =  JsonUtils.toJson(bizMap);
		
		protocolParam.put("biz_content", bizContent);

		Map<String,Object> params = AlipayCore.generateWithSign(protocolParam,this.paymentProperties);
		
		String url = this.paymentProperties.getOpenApiGate() + "?" + AlipayCore.buidQuery(params,paymentProperties.getCharset());;

		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//如果是返回跳转的支付地址
		if(this.resultIsUrl) {
			logger.info("请求的地址：" + url);
			resultMap.put("url", url);
		} else {
			PaymentResponse rsp = PaymentHttpUtils.sendHttpGet(url);
			if(rsp.is2xxSuccess() && rsp.getContentType().indexOf("text/xml")>-1) {
				resultMap = XMLUtil.parseXml(rsp.getBody());
				logger.debug("xml2json: " + resultMap.toString());
			}
			logger.info("返回的结果：" + rsp.getBody());
		}
		return this.process(resultMap);
	}

	
	protected Map<String,Object> getProtocolParamAndCleanProtocolParam(Map<String,Object> sourceParam){
		Map<String,Object> commonParam = new HashMap<String,Object>();
		for(String name: protocalParamNames) {
			if(sourceParam.containsKey(name)) {
				commonParam.put(name, (String) sourceParam.get(name));
				sourceParam.remove(name);
			}
		}
		return commonParam;
	}
}
