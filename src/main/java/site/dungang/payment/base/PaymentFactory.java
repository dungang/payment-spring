package site.dungang.payment.base;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class PaymentFactory implements BeanFactoryAware {

	private static Logger logger = LoggerFactory.getLogger(PaymentFactory.class);

	private Map<String, IPayment> payments;

	private static BeanFactory beanFactory;

	/**
	 * only for lock
	 */
	private static byte[] PAYMENTS_LOCK = new byte[0];

	protected IPayment getPayment(String paymentType) {

		this.insurancePayments();

		if (null == payments.get(paymentType)) {
			// 双重检查锁.
			// 注意，前面反复提到“从语义上讲是没有问题的”，
			// 但是很不幸，禁止指令重排优化这条语义直到jdk1.5以后才能正确工作
			synchronized (this) {
				if (null == payments.get(paymentType)) {
					try {
						String paymentClz = "com.nda.common.payment.service." + paymentType.toLowerCase() + "." + paymentType + "ment";
						IPayment iPayment = (IPayment) Class.forName(paymentClz).newInstance();

						this.payments.put(paymentType, iPayment);

						logger.debug("加载支付渠道：" + paymentClz);
						return iPayment;
					} catch (InstantiationException e) {
						e.printStackTrace();
						logger.error("加载支付渠道失败： " + e.getMessage());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						logger.error("加载支付渠道失败： " + e.getMessage());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						logger.error("加载支付渠道失败： " + e.getMessage());
					}
				}
			}
		}
		return payments.get(paymentType);
	}

	protected void insurancePayments() {
		if (null == this.payments) {
			// 双重检查锁.
			// 注意，前面反复提到“从语义上讲是没有问题的”，
			// 但是很不幸，禁止指令重排优化这条语义直到jdk1.5以后才能正确工作
			synchronized (PAYMENTS_LOCK) {
				if (null == this.payments) {
					this.payments = new HashMap<String, IPayment>();
				}
			}

		}
	}

	/**
	 * 执行api调用
	 * 
	 * @param className
	 * @param param
	 * @return
	 */
	public Map<String, Object> work(String paymentType, String apiName, Map<String, Object> param) {
		IPayment iPayment = this.getPayment(paymentType);
		if (iPayment != null) {
			return iPayment.work(apiName, param);
		}
		return null;
	}

	/**
	 * 验签
	 */
	public boolean verifySign(String paymentType, Map<String, Object> param) {
		IPayment iPayment = this.getPayment(paymentType);
		if (iPayment != null) {
			return iPayment.verifySign(param);
		}
		return false;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		PaymentFactory.beanFactory = beanFactory;
	}

	/**
	 * 获取支付渠道的配置properties 渠道名称 alipay+ mentProperties， 在注解Servcie中的name必须按照这个规则
	 * 
	 * @param prefixPropertiesName
	 * @return
	 */
	public static IPaymentProperties getProperties(String prefixPropertiesName) {
		if (PaymentFactory.beanFactory == null) {
			throw new NullPointerException("BeanFactory is null!");
		}
		return (IPaymentProperties) PaymentFactory.beanFactory.getBean(prefixPropertiesName.toLowerCase() + "mentProperties");
	}

}
