package site.dungang.payment.alipay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import site.dungang.payment.base.IPaymentProperties;

@Component("alipaymentProperties")
@ConfigurationProperties(prefix = "alipay")
public class AliPaymentProperties implements IPaymentProperties {

	private static Logger logger = LoggerFactory.getLogger(AliPaymentProperties.class);

	/**
	 * Mapi网关
	 */
	private String apiGate = "https://mapi.alipay.com/gateway.do";

	/**
	 * openapi网关
	 */
	private String openApiGate = "https://openapi.alipay.com/gateway.do";

	/**
	 * 合作伙伴id
	 */
	private String partnerId = "";

	/**
	 * app id
	 */
	private String appId = "";

	/**
	 * 渠道支付交流流水行为（记录交易流水） 默认不开启，设置完成的类名
	 */
	private String tradeBehavior = null;

	/**
	 * md5签名密钥
	 * 
	 */
	private String md5key = "";

	/**
	 * 开发者私钥 是对请求参数的签名密钥
	 */
	private String partnerPrivateKey = "";

	/**
	 * ali支付公钥 是对阿里通知结果的签名验签公钥
	 */
	private String alipayPublicKey = "";

	/**
	 * 接受支付宝通知的baseurl
	 */
	private String notifyBaseUrl = "http://ngrok-004.ngrok.xiaomiqiu.cn";

	/**
	 * 跳转地址的baseurl
	 */
	private String returnBaseUrl = "http://localhost";

	/**
	 * 签名类型 md5，RSA，RSA2
	 */
	private String signType = "MD5";

	/**
	 * 编码
	 */
	private String charset = "UTF-8";

	/**
	 * 账户
	 */
	private String account = "";

	/**
	 * 账户名称
	 */
	private String accountName = "杭州赫码科技有限公司";

	public AliPaymentProperties() {
		super();
		logger.debug("实例化支付宝配置的properties");
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOpenApiGate() {
		return openApiGate;
	}

	public void setOpenApiGate(String openApiGate) {
		this.openApiGate = openApiGate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getApiGate() {
		return apiGate;
	}

	public void setApiGate(String apiGate) {
		this.apiGate = apiGate;
	}

	public String getPartnerPrivateKey() {
		return partnerPrivateKey;
	}

	public void setPartnerPrivateKey(String partnerPrivateKey) {
		this.partnerPrivateKey = partnerPrivateKey;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getNotifyBaseUrl() {
		return notifyBaseUrl;
	}

	public void setNotifyBaseUrl(String notifyBaseUrl) {
		this.notifyBaseUrl = notifyBaseUrl;
	}

	public String getReturnBaseUrl() {
		return returnBaseUrl;
	}

	public void setReturnBaseUrl(String returnBaseUrl) {
		this.returnBaseUrl = returnBaseUrl;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getMd5key() {
		return md5key;
	}

	public void setMd5key(String md5key) {
		this.md5key = md5key;
	}

	@Override
	public String getAccountId() {
		return this.partnerId;
	}

	@Override
	public String getTradeBehavior() {
		return this.tradeBehavior;
	}

	public void setTradeBehavior(String tradeBehavior) {
		this.tradeBehavior = tradeBehavior;
	}
}
