package site.dungang.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import site.dungang.payment.base.PaymentFactory;
import site.dungang.payment.utils.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PyamentTest {

	@Autowired
	private PaymentFactory factory;

	private final String orderId = "402465084582006784";

	private static Logger logger = LoggerFactory.getLogger(PyamentTest.class);

	@Test
	public void testPayment() {

		// 构建参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("out_trade_no", DateUtils.getYmdformat() + orderId);
		param.put("subject", "test123");
		param.put("body", "即时到账测试");
		param.put("total_fee", "0.01");
		param.put("quantity", "1");
		param.put("notify_url", "/pay/notify4Pay");
		param.put("return_url", "/order/detail?id=" + orderId);

		// 执行返回结果
		Map<String, Object> resultMap = factory.work("AliPay", "CreateDirectPayByUser", param);

		logger.debug("测试即时到账接口的url：" + resultMap.toString());
		assertThat(resultMap).isNotNull();
		assertThat(resultMap.get("url")).isNotNull();
	}
}
