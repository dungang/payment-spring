package site.dungang.payment.alipay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AliPaymentProperties.class)
public class AliPaymentAutoConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(AliPaymentAutoConfiguration.class);

	public AliPaymentAutoConfiguration() {
		logger.debug("AliPaymentAutoConfiguration start");
	}
}
