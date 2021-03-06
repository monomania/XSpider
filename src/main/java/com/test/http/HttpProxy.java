package com.test.http;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpProxy extends ProxySelector {

  // 代理配置路径
  private static final String CONF_PROXY_PROPERTIES = "conf/proxy.properties";
  // 代理有效期
  private static final long PROXY_DURATION = 2 * 86400000L;
  // 获取48小时的proxy，1个
  private static final String REQUEST_48H =
      "http://webapi.http.zhimacangku.com/getip?num=1&type=3&pro=&city=0&yys=0&port=1&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";

  private long mExpireTimeMills;
  private long mLastRequestTimeMills;

  private boolean mAutoRequest;
  private final List<String> mProxySet = new ArrayList<>();
  private final Map<String, String> mUsedProxy = new HashMap<>();
  private final AtomicInteger mPointer = new AtomicInteger(0);

  public HttpProxy(boolean autoRequest) {
    mAutoRequest = autoRequest;
    loadProxy();
  }

  public static void main(String[] args) {
    HttpProxy httpProxy = new HttpProxy(true);
    httpProxy.autoUpdate();

    System.out.println(httpProxy.mProxySet);
    System.out.println(httpProxy.mExpireTimeMills);
    System.out.println(httpProxy.mLastRequestTimeMills);
  }

  private void autoUpdate() {
    if (!mAutoRequest) {
      return;
    }
    // 已过期, 更新
    if (System.currentTimeMillis() < mExpireTimeMills
        || System.currentTimeMillis() - mLastRequestTimeMills < PROXY_DURATION) {
      return;
    }

    OutputStream outputStream = null;
    try {
      // 重新请求
      Response response = new OkHttpClient.Builder().build()
          .newCall(new Request.Builder().url(REQUEST_48H).build()).execute();
      if (!response.isSuccessful() || response.body() == null) {
        return;
      }

      String list = response.body().string().replace("\r\n", " ");
      Properties properties = new Properties();
      properties.setProperty("proxy", list);
      properties.setProperty("expireTimeMills", (System.currentTimeMillis() + 2 * 864000_000) + "");
      properties.setProperty("requestTimeMills", System.currentTimeMillis() + "");
      outputStream = new FileOutputStream(CONF_PROXY_PROPERTIES);
      properties.store(outputStream, "");

      // 重新装载
      loadProxy();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(outputStream);
    }
  }

  private void loadProxy() {
    InputStream in = null;
    try {
      in = new FileInputStream(CONF_PROXY_PROPERTIES);
      Properties properties = new Properties();
      properties.load(in);
      String[] list = properties.getProperty("proxy").split("\\s");
      String expireString = properties.getProperty("expireTimeMills");
      mExpireTimeMills = Long.parseLong(expireString);
      String lastRequestTimeString = properties.getProperty("requestTimeMills");
      mLastRequestTimeMills = Long.parseLong(lastRequestTimeString);

      // 装载配置
      mProxySet.clear();
      mPointer.set(0);
      mProxySet.addAll(Arrays.asList(list));
      Collections.shuffle(mProxySet);
    } catch (Exception ignore) {
      // 不设置代理
      mAutoRequest = false;
    } finally {
      IOUtils.closeQuietly(in);
    }
  }

  @Override
  public synchronized List<Proxy> select(URI uri) {
    // 每次都检查
    autoUpdate();

    if (mProxySet.isEmpty()) {
      return null;
    }
    String proxyString = mProxySet.get(mPointer.getAndIncrement() % mProxySet.size());
    if (!proxyString.contains(":")) {
      return null;
    }
    String[] split = proxyString.split(":");
    Proxy nextProxy =
        new Proxy(Proxy.Type.HTTP, new InetSocketAddress(split[0], Integer.parseInt(split[1])));

    mUsedProxy.put(uri.toString(), proxyString);
    return Collections.singletonList(nextProxy);
  }

  @Override
  public synchronized void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
    String proxyString = mUsedProxy.remove(uri.toString());
    mProxySet.remove(proxyString);
  }
}
