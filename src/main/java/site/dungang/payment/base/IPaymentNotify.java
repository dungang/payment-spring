package site.dungang.payment.base;

import java.io.IOException;

/**
 * 处理通知业务
 * @author dungang
 *
 */
public interface IPaymentNotify {

	/**
	 * 执行处理通知
	 * @param notifyProcessor
	 * @return
	 * @throws IOException
	 */
	Object doNotify(INotifyProcessor notifyProcessor) throws IOException;
}
