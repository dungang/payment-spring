package site.dungang.payment.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import site.dungang.payment.base.AbstractPayment;
import site.dungang.payment.base.Ipay;
import site.dungang.payment.base.PaymentFactory;
import site.dungang.payment.base.IPaymentProperties;


public class AliPayment extends AbstractPayment {

	private static Logger logger = LoggerFactory.getLogger(AliPayment.class);

	@Override
	public Ipay buildPay(String apiName) {
		try {
			// 反射获取支付接口
			String payClz = "site.dungang.payment.alipay.api.AliPay" + apiName;
			Ipay iPay = (Ipay) Class.forName(payClz).newInstance();

			logger.debug("加载的支付宝接口： " + payClz);

			// 设置接口的properties
			iPay.setPaymentProperties((IPaymentProperties) getPaymentProperties());
			return iPay;
		} catch (InstantiationException e) {
			e.printStackTrace();
			logger.error("加载支付宝接口失败： " + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error("加载支付宝接口失败： " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("加载支付宝接口失败： " + e.getMessage());
		}
		return null;
	}

	@Override
	public Map<String,Object> work(String apiName, Map<String, Object> param) {
		Ipay iPay = this.getPay(apiName);
		if (null != iPay) {
			return iPay.execute(param);
		}
		return null;
	}

	@Override
	public boolean verifySign(Map<String,Object> param) {
		
		AliPaymentProperties properties = this.getPaymentProperties();
		
		// 判断responsetTxt是否为true，isSign是否为true
		// responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		//openapi 增加了版本参数version
		String apiGate = param.containsKey("version") ? properties.getApiGate() : properties.getOpenApiGate();
		
		logger.debug("支付宝网关："+ apiGate);
		
		String responseTxt = "false";
		if (param.get("notify_id") != null) {
			String notify_id = (String) param.get("notify_id");
			responseTxt = verifyResponse(apiGate, properties.getPartnerId(), notify_id);
		}
		
		String sourceSign = "";
		if (param.get("sign") != null) {
			sourceSign = (String) param.get("sign");
		}

		// 写日志记录（若要调试，请取消下面两行注释）
		// String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign +
		// "\n 返回回来的参数：" + AlipayCore.createLinkString(param);
		// AlipayCore.logResult(sWord);

		if (AlipayCore.verifySign(param, sourceSign, properties) && responseTxt.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	private AliPaymentProperties getPaymentProperties() {
		return (AliPaymentProperties) PaymentFactory.getProperties("AliPay");
	}

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param apiGate
	 *            支付网关
	 * @param parnter
	 *            合作伙伴id
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private String verifyResponse(String apiGate, String partner, String notify_id) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		String veryfy_url = apiGate + "?service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;
		return checkUrl(veryfy_url);
	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private String checkUrl(String urlvalue) {
		String inputLine = "";

		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			e.printStackTrace();
			inputLine = "";
		}

		return inputLine;
	}

}
