package site.dungang.payment.base;

import java.io.Serializable;
import java.util.Date;

public class PaymentTrade implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String paymentid;

	private String orderid;

	/**
	 * 依赖的API名称
	 */
	private String dependencyApiName;

	/**
	 * 业务类型
	 */
	private String businessType;

	private String tradeno;

	private String status;

	private String apiName;

	private String reqInfo;

	private String resInfo;

	private String notifyInfo;

	private Date createTime;

	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid == null ? null : orderid.trim();
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno == null ? null : tradeno.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getNotifyInfo() {
		return notifyInfo;
	}

	public void setNotifyInfo(String notifyInfo) {
		this.notifyInfo = notifyInfo;
	}

	public String getReqInfo() {
		return reqInfo;
	}

	public void setReqInfo(String reqInfo) {
		this.reqInfo = reqInfo;
	}

	public String getResInfo() {
		return resInfo;
	}

	public void setResInfo(String resInfo) {
		this.resInfo = resInfo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDependencyApiName() {
		return dependencyApiName;
	}

	public void setDependencyApiName(String dependencyApiName) {
		this.dependencyApiName = dependencyApiName;
	}

}