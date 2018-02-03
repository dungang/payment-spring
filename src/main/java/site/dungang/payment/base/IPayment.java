package site.dungang.payment.base;

import java.util.Map;

public interface IPayment {
	/**
	 * 执行接口
	 * @param apiName
	 * @param param
	 * @return
	 */
	Map<String, Object> work(String apiName, Map<String, Object> param);
	
	/**
	 * 验签
	 * @param param
	 * @return
	 */
	boolean verifySign(Map<String,Object> param);

}
