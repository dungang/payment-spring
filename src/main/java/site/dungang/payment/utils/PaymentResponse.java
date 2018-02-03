package site.dungang.payment.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;


public class PaymentResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String body;
	
	private Header[] headers;
	
	private int statusCode;
	
	private Map<String , String> headersMap = new HashMap<String , String>();

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
		for(Header header: headers) {
			headersMap.put(header.getName(), header.getValue());
		}
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public boolean is2xxSuccess() {
		if(this.statusCode <300 && this.statusCode >=200) {
			return true;
		}
		return false;
	}
	
	public String getContentType() {
		return this.headersMap.get("Content-Type");
	}
	
	public String getHeaderValue(String headerName) {
		return this.headersMap.get(headerName);
	}
}
