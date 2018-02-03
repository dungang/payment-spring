package site.dungang.payment.base;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPayment implements IPayment  {
	
	public static final String MD5_SignType = "MD5";
	
	public static final String RSA_SignType = "RSA";
	
	public static final String RSA2_SignType = "RSA2";
	
	/**
	 * only for lock
	 */
	private static byte[] PAYS_LOCK = new byte[0];
	
	/**
	 * 支付渠道的交易流水行为
	 */
	private IPaymentTradePersistedService paymentTradeBehavior = null;
	
	private Map<String, Ipay> pays;

	public abstract Ipay buildPay(String apiName);

	protected Ipay getPay(String apiName) {

		this.insurancePays();

//		if (null != this.pays.get(apiName)) {
//			return this.pays.get(apiName);
//		} else {
//			Ipay iPay = buildPay(apiName);
//			this.pays.put(apiName, iPay);
//			return iPay;
//		}
		
		if( null == this.pays.get(apiName)) {
			//双重检查锁.
			//注意，前面反复提到“从语义上讲是没有问题的”，
			//但是很不幸，禁止指令重排优化这条语义直到jdk1.5以后才能正确工作
			synchronized (this) {
				if(null == this.pays.get(apiName)) {
					Ipay iPay = buildPay(apiName);
					this.pays.put(apiName, iPay);
				}
			}
		}
		return this.pays.get(apiName);
	}

	protected void insurancePays() {
		if (null == this.pays) {
			//双重检查锁.
			//注意，前面反复提到“从语义上讲是没有问题的”，
			//但是很不幸，禁止指令重排优化这条语义直到jdk1.5以后才能正确工作
			synchronized (PAYS_LOCK) {
				if(null == this.pays) {
					this.pays = new HashMap<String, Ipay>();
				}
			}
		}
	}

	public IPaymentTradePersistedService getPaymentTradeBehavior() {
		return paymentTradeBehavior;
	}

	public void setPaymentTradeBehavior(IPaymentTradePersistedService paymentTradeBehavior) {
		this.paymentTradeBehavior = paymentTradeBehavior;
	}

}
