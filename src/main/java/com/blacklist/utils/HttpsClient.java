package com.blacklist.utils;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

public class HttpsClient {
	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 10000;
	{
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		requestConfig = configBuilder.build();
	}

	public String doPost(String url, Map<String, String> param, String charset) {
		org.apache.http.client.HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = HttpClients.custom()
					.setSSLSocketFactory(factoryBuilder())
					.setConnectionManager(connMgr)
					.setDefaultRequestConfig(requestConfig).build();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> iterator = param.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator
						.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private static SSLConnectionSocketFactory factoryBuilder() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext,
					new HostnameVerifier() {
						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							return true;
						}
					});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}
}


//@SuppressWarnings("deprecation")
//class SSLClient1 extends DefaultHttpClient {
//	public SSLClient1() throws Exception {
//		super();
//		SSLContext ctx = SSLContext.getInstance("TLS");
//		X509TrustManager tm = new X509TrustManager() {
//
//			@Override
//			public void checkClientTrusted(X509Certificate[] arg0, String arg1)
//					throws CertificateException {
//			}
//
//			@Override
//			public void checkServerTrusted(X509Certificate[] arg0, String arg1)
//					throws CertificateException {
//			}
//
//			@Override
//			public X509Certificate[] getAcceptedIssuers() {
//				return null;
//			}
//		};
//		ctx.init(null, new TrustManager[] { tm }, null);
//		SSLSocketFactory ssf = new SSLSocketFactory(ctx,
//				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//		ClientConnectionManager ccm = this.getConnectionManager();
//		SchemeRegistry sr = ccm.getSchemeRegistry();
//		sr.register(new Scheme("https", 443, ssf));
//	}
//}