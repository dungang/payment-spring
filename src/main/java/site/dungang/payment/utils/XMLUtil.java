package site.dungang.payment.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLUtil {
	
	private static Logger logger = LoggerFactory.getLogger(XMLUtil.class);

	/**
	 * 解析xml字符串
	 * @param strxml
	 * @return
	 */
	public static Map<String, Object> parseXml(String strxml) {
		InputStream in;
		try {
			in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
			return xmlParser(in);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 递归读取子元素
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> recursiveParser(Element root) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Element> elements = root.elements();
		for (Element e : elements) {
			if (e.elements().size() > 0) {
				map.put(e.getName(), recursiveParser(e));
			} else {
				map.put(e.getName(), e.getText());
			}
		}
		return map;
	}

	/**
	 * 从request中取得输入流
	 * 
	 * @param inputStream
	 * @return
	 */
	public static Map<String, Object> xmlParser(InputStream inputStream) {

		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			Map<String, Object> map = recursiveParser(root);
			inputStream.close();
			inputStream = null;
			return map;
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}
		return null;

	}
}
