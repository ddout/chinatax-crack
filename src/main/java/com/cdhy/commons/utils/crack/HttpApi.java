package com.cdhy.commons.utils.crack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.io.Resources;

@SuppressWarnings("deprecation")
public class HttpApi {

    private static DefaultHttpClient client = null;

    static {
	try {
	    File keystore = Resources.getResourceAsFile(HttpApi.class.getClassLoader(), "conf/cer/steven2.keystore");
	    // 获得密匙库
	    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	    FileInputStream instream = new FileInputStream(keystore);
	    // 密匙库的密码
	    trustStore.load(instream, "123456".toCharArray());
	    client = new DefaultHttpClient();
	    client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
	    client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
		    "    Mozilla/5.0 (Windows NT 6.2; rv:18.0) Gecko/20100101 Firefox/18.0");
	    // 注册密匙库
	    SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
	    // 不校验域名
	    socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	    Scheme sch = new Scheme("https", 443, socketFactory);
	    client.getConnectionManager().getSchemeRegistry().register(sch);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static Map<String, Object> getUrl(String url, String param) {
	return getUrl(url, param, null);
    }

    public static void main(String[] args) {
	System.out.println(getUrlText("https://inv-veri.chinatax.gov.cn/js/dc1de.js"));
    }

    public static String getUrlText(String url) {
	try {
	    HttpGet get = new HttpGet(url);
	    HttpResponse response1 = client.execute(get);
	    HttpEntity resEntity1 = response1.getEntity();
	    String result = EntityUtils.toString(resEntity1, "UTF-8");
	    return result;
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    public static Map<String, Object> getUrl(String url, String param, Header[] h) {
	Map<String, Object> resultMap = new HashMap<String, Object>();
	try {
	    HttpPost httppost1 = new HttpPost(url);
	    if (null != param && !"".equals(param)) {
		StringEntity stringEntity = new StringEntity(param, "GBK");// 解决中文乱码问题
		stringEntity.setContentEncoding("GBK");
		httppost1.setEntity(stringEntity);
	    }
	    if (null != h) {
		httppost1.setHeaders(h);
	    }
	    HttpResponse response1 = client.execute(httppost1);
	    HttpEntity resEntity1 = response1.getEntity();
	    String result = EntityUtils.toString(resEntity1, "gbk");
	    Header[] headers = response1.getHeaders("Set-Cookie");
	    resultMap.put("result", result);
	    resultMap.put("headers", headers);
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
	return resultMap;
    }
}
