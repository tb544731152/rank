package com.imooc.miaosha.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils
{
	
  private  DefaultHttpClient httpClient;
  
  private static  DefaultHttpClient httpClientos;
  
  Logger logger = Logger.getLogger(getClass());

  static{
	  httpClientos = new DefaultHttpClient();  
  }
  
  public static HttpUtils getInstall()
  {
    return INSTALL_HANDLER.INSTALL;
  }
  
  
  private static class INSTALL_HANDLER
  {
    private static HttpUtils INSTALL = new HttpUtils();

    static {
    	INSTALL.httpClient = new DefaultHttpClient(); 
    }

  }

  public String sendJSON(final String url, final String jsonStr, boolean asyncFlag)
  {
    if (asyncFlag) {
      Executors.newSingleThreadExecutor().execute(new Runnable()
      {
        public void run() {
          if (HttpUtils.this.logger.isDebugEnabled()) {
            HttpUtils.this.logger.debug("sendJSON(url,jsonStr,asycFlag),url=" + url + ";jsonStr=" + jsonStr);
          }
          String result = HttpUtils.this.sendJSON(url, jsonStr);
          Logger logger = HttpUtils.this.logger;
          if (logger.isDebugEnabled())
            HttpUtils.this.logger.debug("sendJSON(url,jsonStr,asycFlag) result:" + result);
        }
      });
    }
    else {
      return sendJSON(url, jsonStr);
    }
    return "";
  }
  public String sendJSON(String url, String jsonStr) {
    String result = "";
    HttpClient client = getHttpClient();

    HttpPost post = new HttpPost(url);
    post.setHeader("Content-Type", "application/json");
    post.setEntity(new StringEntity(jsonStr, Charset.forName("utf-8")));
    try
    {
      HttpResponse response = client.execute(post);
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }
  public String sendJSON(String url, String jsonStr, Map<String, String> headerMap) {
    String result = "";
    HttpClient client = getHttpClient();

    HttpPost post = new HttpPost(url);
    post.setHeader("Content-Type", "application/x-www-form-urlencoded");

    for (String key : headerMap.keySet())
    {
      post.setHeader(key, (String)headerMap.get(key));
    }

    post.setEntity(new StringEntity(jsonStr, Charset.forName("utf-8")));
    try
    {
      HttpResponse response = client.execute(post);
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }

  public static String postJsonRequest(String urls, String param) {
		String result = "";
		URL url = null;
		OutputStream out = null;
		HttpURLConnection httpurl = null;
		try {
			url = new URL(urls);
			httpurl = (HttpURLConnection) url.openConnection();
			httpurl.setConnectTimeout(5 * 1000);
			httpurl.setReadTimeout(8 * 1000);
			httpurl.setRequestMethod("POST");
			httpurl.setDoOutput(true);
			out = httpurl.getOutputStream();
			out.write(param.getBytes("UTF-8"));
			out.flush();
		} catch (Exception ex) {
			new RuntimeException("ThreadPoolTask.post()与客户端通讯(读取数据)异常");
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedReader bufferreader = null;
		try {
			bufferreader = new BufferedReader(new InputStreamReader(httpurl
					.getInputStream(), "UTF-8"));
			StringBuffer stringbuffer = new StringBuffer();
			int ch;
			while ((ch = bufferreader.read()) > -1) {
				stringbuffer.append((char) ch);
			}
			result = stringbuffer.toString().trim();

			httpurl.disconnect();
		} catch (Exception e) {
			new RuntimeException("ThreadPoolTask.post()与客户端通讯(读取数据)异常");
		} finally {
			try {
				bufferreader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

  
  public static String postXmlRequest(String reqURL, String sendData) throws Exception
  {
    String responseContent = "";
    HttpClient httpClient = new DefaultHttpClient();
    httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY); 
    HttpGet httpPost = new HttpGet(reqURL);
    try {
      httpPost.setHeader("Content-Type", "application/text");
      StringEntity entity = new StringEntity(sendData, "UTF-8");
      //httpPost.setEntity(entity);
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity resEntity = response.getEntity();
      if (resEntity != null)
        responseContent = EntityUtils.toString(resEntity);
       
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return responseContent;
    
   // return  new String(responseContent.getBytes("iso8859-1"),"utf8");
  }
  
  
  public String getRequest(String reqURL, String decodeCharset)
  {
    String responseContent = null;
    HttpClient httpClient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(reqURL);
    try {
      HttpResponse response = httpClient.execute(httpGet);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
        EntityUtils.consume(entity);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return responseContent;
  }

  private HttpUriRequest getRequest(String url, Map<String, String> params, HttpMethod method)
  {
    if (method.equals(HttpMethod.POST)) {
      List listParams = new ArrayList();
      if (params != null) {
        for (String name : params.keySet())
          listParams.add(new BasicNameValuePair(name, (String)params.get(name)));
      }
      try
      {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(listParams);
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        request.setEntity(entity);
        return request;
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }
    if (url.indexOf("?") < 0) {
      url = url + "?";
    }
    if (params != null) {
      for (String name : params.keySet()) {
        url = url + "&" + name + "=" + URLEncoder.encode((String)params.get(name));
      }
    }
    HttpGet request = new HttpGet(url);
    return request;
  }

  public HttpClient getHttpClient()
  {
    HttpClient result = this.httpClient;
    return result;
  }

  public String sendGetRequest(String reqURL, String decodeCharset)
  {
    long responseLength = 0L;
    String responseContent = null;
    HttpClient httpClient = getHttpClient();
    HttpGet httpGet = new HttpGet(reqURL);
    try {
      HttpResponse response = httpClient.execute(httpGet);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        responseLength = entity.getContentLength();
        responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
        EntityUtils.consume(entity);
      }

      System.out.println("请求地址: " + httpGet.getURI());
      System.out.println("响应状态: " + response.getStatusLine());
      System.out.println("响应长度: " + responseLength);
      System.out.println("响应内容: " + responseContent);
    }
    catch (ClientProtocolException e) {
      log("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下", 
        e);
    } catch (ParseException e) {
      log(e.getMessage(), e);
    } catch (IOException e) {
      log("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
    }

    return responseContent;
  }

  public String sendPostRequest(String reqURL, String sendData, boolean isEncoder)
  {
    return sendPostRequest(reqURL, sendData, isEncoder, null, null);
  }

  public String sendPostRequest(String reqURL, String sendData)
  {
    Lock lock = new ReentrantLock();
    String responseContent = null;
    try {
      lock.lock();
      HttpClient httpClient = getHttpClient();
      HttpPost httpPost = new HttpPost(reqURL);
      httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
      StringEntity entity = new StringEntity(sendData, "UTF-8");
      httpPost.setEntity(entity);
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity resEntity = response.getEntity();
      if (resEntity != null) {
        responseContent = EntityUtils.toString(resEntity);
        EntityUtils.consume(resEntity);
      }
    } catch (Exception localException) {
    }
    finally {
      lock.unlock();
    }
    return responseContent;
  }

  public String sendPostRequest(String reqURL, String sendData, boolean isEncoder, String encodeCharset, String decodeCharset)
  {
    String responseContent = null;

    HttpClient httpClient = getHttpClient();

    HttpPost httpPost = new HttpPost(reqURL);

    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
    try
    {
      if (isEncoder)
      {
        List formParams = new ArrayList();

        for (String str : sendData.split("&"))
        {
          formParams.add(new BasicNameValuePair(str.substring(0, str.indexOf("=")), str.substring(str.indexOf("=") + 1)));
        }

        httpPost.setEntity(new StringEntity(URLEncodedUtils.format(formParams, encodeCharset == null ? "UTF-8" : encodeCharset)));
      }
      else {
        httpPost.setEntity(new StringEntity(sendData));
      }

      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();

      if (entity != null) {
        responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
        EntityUtils.consume(entity);
      }
    }
    catch (Exception e)
    {
      log("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
    }

    return responseContent;
  }

  public String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset)
  {
    String responseContent = null;

    HttpClient httpClient = getHttpClient();

    HttpPost httpPost = new HttpPost(reqURL);

    List formParams = new ArrayList();

    for (Map.Entry entry : params.entrySet())
    {
      formParams.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
    }

    try
    {
      httpPost.setEntity(new UrlEncodedFormEntity(formParams, 
        encodeCharset == null ? "UTF-8" : encodeCharset));

      HttpResponse response = httpClient.execute(httpPost);

      HttpEntity entity = response.getEntity();

      if (entity != null)
      {
        responseContent = EntityUtils.toString(entity, 
          decodeCharset == null ? "UTF-8" : decodeCharset);

        EntityUtils.consume(entity);
      }
    }
    catch (Exception e)
    {
      log("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
    }

    return responseContent;
  }

  public String sendPostSSLRequest(String reqURL, Map<String, String> params)
  {
    return sendPostSSLRequest(reqURL, params, null, null);
  }

  public String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset)
  {
    String responseContent = "";

    HttpClient httpClient = getHttpClient();

    X509TrustManager xtm = new X509TrustManager()
    {
      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
      {
      }

      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
      {
      }

      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

    };
    try
    {
      SSLContext ctx = SSLContext.getInstance("TLS");

      ctx.init(null, new TrustManager[] { xtm }, null);

      SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

      httpClient.getConnectionManager().getSchemeRegistry()
        .register(new Scheme("https", 443, socketFactory));

      HttpPost httpPost = new HttpPost(reqURL);

      List formParams = new ArrayList();

      for (Map.Entry entry : params.entrySet())
      {
        formParams.add(new BasicNameValuePair((String)entry.getKey(), 
          (String)entry
          .getValue()));
      }

      httpPost.setEntity(new UrlEncodedFormEntity(formParams, 
        encodeCharset == null ? "UTF-8" : encodeCharset));

      HttpResponse response = httpClient.execute(httpPost);

      HttpEntity entity = response.getEntity();

      if (entity != null)
      {
        responseContent = EntityUtils.toString(entity, 
          decodeCharset == null ? "UTF-8" : decodeCharset);

        EntityUtils.consume(entity);
      }

    }
    catch (Exception e)
    {
      log("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
    }

    return responseContent;
  }

  public static String sendPostRequestByJava(String reqURL, Map<String, String> params)
  {
    StringBuilder sendData = new StringBuilder();

    for (Map.Entry entry : params.entrySet())
    {
      sendData.append((String)entry.getKey()).append("=").append((String)entry.getValue()).append("&");
    }

    if (sendData.length() > 0) {
      sendData.setLength(sendData.length() - 1);
    }

    return sendPostRequestByJava(reqURL, sendData.toString());
  }

  public String sendPostRequestByMap(String reqURL, Map<String, String> params)
  {
    StringBuilder sendData = new StringBuilder();
    Lock lock = new ReentrantLock();
    try {
      lock.lock();

      for (Map.Entry entry : params.entrySet())
      {
        sendData.append((String)entry.getKey()).append("=").append((String)entry.getValue()).append("&");
      }

      if (sendData.length() > 0) {
        sendData.setLength(sendData.length() - 1);
      }

      return sendPostRequest(reqURL, sendData.toString());
    } catch (Exception localException) {
    } finally {
      lock.unlock();
    }
    return null;
  }

  public static String sendPostRequestByJava(String reqURL, String sendData)
  {
    HttpURLConnection httpURLConnection = null;
    OutputStream out = null;
    InputStream in = null;
    int httpStatusCode = 0;
    try {
      URL sendUrl = new URL(reqURL);
      httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
      httpURLConnection.setRequestMethod("POST");
      httpURLConnection.setDoOutput(true);
      httpURLConnection.setUseCaches(false);
      httpURLConnection.setConnectTimeout(30000);
      httpURLConnection.setReadTimeout(30000);
      out = httpURLConnection.getOutputStream();
      out.write(sendData.toString().getBytes());

      out.flush();

      httpStatusCode = httpURLConnection.getResponseCode();

      in = httpURLConnection.getInputStream();

      byte[] byteDatas = new byte[in.available()];

      in.read(byteDatas);

      return new String(byteDatas) + "`" + httpStatusCode;
    }
    catch (Exception e)
    {
      String str;
      log(e.getMessage(), e);

      return "Failed`" + httpStatusCode;
    }
    finally
    {
      if (out != null)
      {
        try
        {
          out.close();
        }
        catch (Exception e) {
          log("关闭输出流时发生异常,堆栈信息如下", e);
        }
      }
      if (in != null) {
        try {
          in.close();
        } catch (Exception e) {
          log("关闭输入流时发生异常,堆栈信息如下", e);
        }
      }
      if (httpURLConnection != null) {
        httpURLConnection.disconnect();
        httpURLConnection = null;
      }
    }
  }

  public static void log(String str, Exception e) {
    System.out.println(str);
  }

  public static enum HttpMethod
  {
    GET, POST;
  }

}