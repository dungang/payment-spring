package site.dungang.payment.utils;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("paymentCoreProperties")
@ConfigurationProperties(prefix = "payment.core")
public class PaymentCoreProperties {

	/**
	 * 支付渠道的class Map
	 */
	private Map<String, String> paymentClassMap;

	public Map<String, String> getPaymentClassMap() {
		return paymentClassMap;
	}

	public void setPaymentClassMap(Map<String, String> paymentClassMap) {
		this.paymentClassMap = paymentClassMap;
	}
	
}
