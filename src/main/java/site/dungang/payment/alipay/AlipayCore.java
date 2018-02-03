package site.dungang.payment.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import site.dungang.payment.utils.MD5;
import site.dungang.payment.utils.RSA;

//import com.alipay.api.AlipayApiException;
//import com.alipay.api.internal.util.AlipaySignature;


/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：3.3
 *日期：2012-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayCore {

	private static Logger logger = LoggerFactory.getLogger(AlipayCore.class);

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param param
	 *            签名参数组
	 */
	public static void cleanSignAndSignType(Map<String, Object> param) {

		if (param.containsKey("sign")) {
			param.remove("sign");
		}

		if (param.containsKey("sign_type")) {
			param.remove("sign_type");
		}
	}

	public static void cleanSign(Map<String, Object> param) {

		if (param.containsKey("sign")) {
			param.remove("sign");
		}
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, Object> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = (String) params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	public static String buidQuery(Map<String, Object> params, String enc) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = (String) params.get(key);
			if(null == value) {
				value="";
			}
			try {
				if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符

					prestr = prestr + key + "=" + URLEncoder.encode(value, enc);

				} else {
					prestr = prestr + key + "=" + URLEncoder.encode(value, enc) + "&";
				}
			} catch (UnsupportedEncodingException e) {
				logger.debug("URL Encoder异常：" + e.getMessage());
			}
		}

		return prestr;
	}

	/**
	 * 参数签名
	 * 
	 * @param paramMap
	 * @param properties
	 * @return
	 * @throws AlipayApiException
	 */
	public static Map<String, Object> generateWithSign(Map<String, Object> paramMap, AliPaymentProperties properties) {
		String signType = (String) paramMap.get("sign_type");
		String charset = "UTF-8";

		if (paramMap.containsKey("_input_charset")) {
			charset = (String) paramMap.get("_input_charset");
			AlipayCore.cleanSignAndSignType(paramMap);
		} else if (paramMap.containsKey("charset")) {
			charset = (String) paramMap.get("charset");
			AlipayCore.cleanSign(paramMap);
		}

		String prestr = AlipayCore.createLinkString(paramMap);
		String mysign = "MD5";
		try {
			if (signType.equals("RSA")) {
				mysign = RSA.sign(prestr, properties.getPartnerPrivateKey(), charset);
			} else if (signType.equals("RSA2")) {
				mysign = RSA.sign256(prestr, properties.getPartnerPrivateKey(), charset);
			} else {
				mysign = MD5.sign(prestr, properties.getMd5key(), charset);
			}
		} catch (/* AlipayApiException */ Exception e) {
			e.printStackTrace();
			logger.debug("支付宝签名错误： " + e.getMessage());
		}
		paramMap.put("sign", mysign);
		paramMap.put("sign_type", signType);
		return paramMap;
	}

	public static boolean verifySign(Map<String, Object> param, String sourceSign, AliPaymentProperties properties) {
		String sign_type = "MD5";
		if (param.containsKey("sign_type")) {
			sign_type = (String) param.get("sign_type");
		}
		// 过滤空值、sign与sign_type参数
		AlipayCore.cleanSignAndSignType(param);
		// 获取待签名字符串
		String preSignStr = AlipayCore.createLinkString(param);

		if (sign_type.equals("RSA")) {
			return RSA.verify(preSignStr, sourceSign, properties.getAlipayPublicKey(), properties.getCharset());
		} else if (sign_type.equals("RSA2")) {
			return RSA.verify256(preSignStr, sourceSign, properties.getAlipayPublicKey(), properties.getCharset());
		} else {
			// 获得签名验证结果
			return MD5.verify(preSignStr, sourceSign, properties.getMd5key(), properties.getCharset());
		}

	}
}
