package site.dungang.payment.base;

import java.util.Map;

public interface Ipay {
	
	

	/**
	 * 对支付接口返回的结果做标准处理（格式化）
	 * @param resultMap
	 * @return
	 */
	Map<String, Object> process(Map<String, Object> resultMap);

	/**
	 * 构造接口的业务参数
	 * @return
	 */
	Map<String, String> buildParam();
	
	/**
	 * 提供对参数的特殊处理
	 * @param paramMap
	 */
	void beforeExecute(Map<String,Object> paramMap);

	/**
	 * 执行接口请求
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> execute(Map<String, Object> paramMap);
	
	/**
	 * 设置支付的参数properties
	 * @param properties
	 */
	void setPaymentProperties(IPaymentProperties properties);
	
	/**
	 * 获取支付的参数properties
	 * @return
	 */
	IPaymentProperties getPaymentProperties();

}
