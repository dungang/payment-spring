package site.dungang.payment.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.dungang.payment.base.INotifyProcessor;
import site.dungang.payment.base.IPaymentNotify;
import site.dungang.payment.base.PaymentFactory;
import site.dungang.payment.base.PaymentTrade;
import site.dungang.payment.base.IPaymentTradeService;
import site.dungang.payment.utils.JsonUtils;


/**
 * 支付渠道通知处理服务
 * 
 * @author dungang
 *
 */
@Service
@Transactional
public class PaymentNotifyService implements IPaymentNotify {

	private static Logger logger = LoggerFactory.getLogger(PaymentNotifyService.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private PaymentFactory paymentFactory;

	@Autowired
	private IPaymentTradeService dealService;

	@Override
	public Object doNotify(INotifyProcessor notifyProcessor) throws IOException {

		// 格式化post参数
		Map<String, Object> param = this.formatPostParam();
		logger.info(param.toString());
		// 验签
		if (paymentFactory.verifySign(notifyProcessor.paymentType(), param)) {
			PaymentTrade dealDetail = recordPostParam(param, 
					notifyProcessor.preDealParams(param), 
					notifyProcessor.paymentType(), 
					notifyProcessor.getOutTradeNo(),
					notifyProcessor.getTradeNo());
			notifyProcessor.process(param, dealDetail);
			return notifyProcessor.responseSuccess(request, response);
		} else {
			return notifyProcessor.responseFail(request, response);
		}
	}

	/**
	 * 格式化支付渠道的服务器推送的post的通知参数
	 * 
	 * @return
	 */
	private Map<String, Object> formatPostParam() {
		Map<String, String[]> requestParams = request.getParameterMap();
		// 格式化post参数
		Map<String, Object> param = new HashMap<String, Object>();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			param.put(name, valueStr);
		}
		return param;
	}

	/**
	 * 记录成功验签的通知
	 * @param param
	 * @param fields
	 * @param paymentType
	 * @param outTradeNo
	 * @param tradeNo
	 * @return
	 */
	private PaymentTrade recordPostParam(Map<String, Object> param, Map<String, Object> fields, 
			String paymentType, 
			String outTradeNo, 
			String tradeNo) {
		// 流水订单ID
		String id = (String) fields.get(outTradeNo);
		PaymentTrade dealDetail = dealService.selectByPrimaryKey(id);
		if(dealDetail == null){
			dealDetail = dealService.selectByPrimaryKey(id.substring(8));
		}
		if (null != dealDetail) {
			dealDetail.setTradeno((String) fields.get(tradeNo));// 支付渠道系统的交易号
			dealDetail.setNotifyInfo(JsonUtils.toJson(param));// 记录交易的返回的详细信息
			dealDetail.setStatus("finished");
			dealService.update(dealDetail);
		} else {
			logger.debug("支付处理流水编号不存在： " + id);
		}

		return dealDetail;
	}

	public PaymentFactory getPaymentFactory() {
		return paymentFactory;
	}

	public void setPaymentFactory(PaymentFactory paymentFactory) {
		this.paymentFactory = paymentFactory;
	}

	public IPaymentTradeService getdealService() {
		return dealService;
	}

	public void setdealService(IPaymentTradeService dealService) {
		this.dealService = dealService;
	}

}
