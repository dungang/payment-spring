package site.dungang.payment.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentHttpUtils {

	private static Logger logger = LoggerFactory.getLogger(PaymentHttpUtils.class);

	// utf-8字符编码
	public static final String CHARSET_UTF_8 = "utf-8";

	// HTTP内容类型。
	public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

	// HTTP内容类型。相当于form表单的形式，提交数据
	public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

	// HTTP内容类型。相当于form表单的形式，提交数据
	public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";

	// 连接管理器
	private static PoolingHttpClientConnectionManager pool;

	// 请求配置
	private static RequestConfig requestConfig;

	static {

		try {
			logger.debug("初始化PaymentHttpUtils~~~开始");
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			// 配置同时支持 HTTP 和 HTPPS
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			// 初始化连接管理器
			pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
			pool.setMaxTotal(200);
			// 设置最大路由
			pool.setDefaultMaxPerRoute(2);
			// 根据默认超时限制初始化requestConfig
			int socketTimeout = 10000;
			int connectTimeout = 10000;
			int connectionRequestTimeout = 10000;
			requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
					.setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

			logger.debug("初始化PaymentHttpUtils~~~结束");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		// 设置请求超时时间
		requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
				.setConnectionRequestTimeout(50000).build();
	}

	public static CloseableHttpClient getHttpClient() {

		CloseableHttpClient httpClient = HttpClients.custom()
				// 设置连接池管理
				.setConnectionManager(pool)
				// 设置请求配置
				.setDefaultRequestConfig(requestConfig)
				// 设置重试次数
				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();

		return httpClient;
	}

	private static PaymentResponse sendHttpBase(HttpRequestBase requestBase) {

		CloseableHttpClient httpClient = null;
		
		CloseableHttpResponse response = null;

		PaymentResponse paymentResponse = new PaymentResponse();

		// 创建默认的httpClient实例.
		httpClient = getHttpClient();
		// 配置请求信息
		requestBase.setConfig(requestConfig);
		// 执行请求
		try {
			response = httpClient.execute(requestBase);

			paymentResponse.setStatusCode(response.getStatusLine().getStatusCode());
			// 可以获得响应头
			paymentResponse.setHeaders(response.getAllHeaders());

			// 得到响应实例
			HttpEntity entity = response.getEntity();
			paymentResponse.setBody(EntityUtils.toString(entity, CHARSET_UTF_8));
			EntityUtils.consume(entity);
		} catch (ParseException e) {
			logger.debug("api请求异常：" + e.getMessage());
		} catch (IOException e) {
			logger.debug("api请求异常：" + e.getMessage());
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.debug("api请求资源关闭异常：" + e.getMessage());
			}
		}
		return paymentResponse;
	}

	public static PaymentResponse sendHttpPost(String httpUrl) {
		// 创建httpPost
		HttpPost httpPost = new HttpPost(httpUrl);
		return sendHttpBase(httpPost);
	}

	public static PaymentResponse sendHttpGet(String httpUrl) {
		// 创建get请求
		HttpGet httpGet = new HttpGet(httpUrl);
		return sendHttpBase(httpGet);
	}

	public static PaymentResponse sendHttpPostForm(String httpUrl, Map<String, String> maps) {
		String parem = convertStringParamter(maps);
		return sendHttpPostBody(httpUrl, parem, CONTENT_TYPE_FORM_URL);
	}

	public static PaymentResponse sendHttpPostJson(String httpUrl, String jsonString) {
		return sendHttpPostBody(httpUrl, jsonString, CONTENT_TYPE_JSON_URL);
	}

	public static PaymentResponse sendHttpPostXml(String httpUrl, String xmlString) {
		return sendHttpPostBody(httpUrl, xmlString, CONTENT_TYPE_TEXT_HTML);
	}

	public static PaymentResponse sendHttpPostBody(String httpUrl, String body, String contentType) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		try {
			// 设置参数
			if (body != null && body.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(body, "UTF-8");
				stringEntity.setContentType(contentType);
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			logger.debug("api post 请求异常：" + e.getMessage());
		}
		return sendHttpBase(httpPost);
	}

	public static String convertStringParamter(Map<String, String> parameterMap) {
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator<String> iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}
				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		return parameterBuffer.toString();
	}
}
