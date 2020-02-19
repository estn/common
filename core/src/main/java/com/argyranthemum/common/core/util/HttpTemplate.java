package com.argyranthemum.common.core.util;

import com.argyranthemum.common.core.pojo.Result;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpTemplate {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static Logger statLogger = LoggerFactory.getLogger("http.stat");

    //默认字符集
    private static final String CHARSET = "UTF-8";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    //数据传输超时时间
    private static int DEFAULT_SOCKET_TIMEOUT = 30 * 1000;

    //连接超时时间
    private static int DEFAULT_CONNECTION_TIMEOUT = 30 * 1000;

    private static CloseableHttpClient client = null;

    static {
        try {
            /*-- Configuration SSL --*/
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();

            /*-- Init Pool Manager --*/
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(2000);
            cm.setDefaultMaxPerRoute(20);

            /*-- Build Client --*/
            client = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .setConnectionManager(cm)
                    .build();

        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    private boolean hasMethod = false;
    private boolean get = false;
    private boolean post = false;
    private boolean delete = false;
    private boolean put = false;

    private String url;
    private int timeout = DEFAULT_CONNECTION_TIMEOUT;
    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
    private String charset = CHARSET;
    private HttpHost proxy;
    private Map<String, Object> parameters = new HashMap<>();
    private Map<String, Object> headers = new HashMap<>();
    private String body;


    public HttpTemplate get(String url) {
        this.url = url;
        this.get = true;
        this.setHasMethod();
        return this;
    }

    public HttpTemplate post(String url) {
        this.url = url;
        this.post = true;
        this.setHasMethod();
        return this;
    }

    public HttpTemplate delete(String url) {
        this.url = url;
        this.delete = true;
        this.setHasMethod();
        return this;
    }

    public HttpTemplate put(String url) {
        this.url = url;
        this.put = true;
        this.setHasMethod();
        return this;
    }

    private void setHasMethod() {
        if (hasMethod) {
            throw new IllegalArgumentException("has setting method");
        }
        this.hasMethod = true;
    }

    public HttpTemplate timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public HttpTemplate socketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public HttpTemplate charset(String charset) {
        this.charset = charset;
        return this;
    }

    public HttpTemplate proxy(String host, int port) {
        this.proxy = new HttpHost(host, port);
        return this;
    }

    public HttpTemplate parameter(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public HttpTemplate parameters(Map<String, Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    public HttpTemplate header(String name, Object value) {
        this.headers.put(name, value);
        return this;
    }

    public HttpTemplate headers(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public HttpTemplate body(String body) {
        this.body = body;
        return this;
    }

    public Result execute() {
        this.valid();
        long start = System.currentTimeMillis();
        String startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());

        int statusCode = 0;
        HttpRequestBase request = null;
        CloseableHttpResponse response = null;
        Result result;
        try {
            request = this.getRequest();
            this.setRequestConfig(request, timeout, socketTimeout, proxy);
            this.setHeader(request, headers);

            response = client.execute(request);
            statusCode = response.getStatusLine().getStatusCode();
            result = new Result(statusCode, IOUtils.toString(response.getEntity().getContent(), charset));
        } catch (Exception e) {
            logger.error(e.toString(), e);
            result = new Result(500, e.getMessage());
        } finally {
            String template = "%s %s %s %s";
            statLogger.info(String.format(template, startTime, url, statusCode, System.currentTimeMillis() - start));
            this.release(request, response);
        }
        return result;
    }

    private void valid() {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url is empty");
        }

        if (!hasMethod) {
            throw new IllegalArgumentException("method is empty");
        }

    }


    private HttpRequestBase getRequest() {
        HttpRequestBase request = null;
        if (get) {
            request = new HttpGet(this.doGetURL(url, parameters));
        }

        if (post) {
            request = new HttpPost(url);
            ((HttpPost) request).setEntity(doGetEntity(parameters));
            if (body != null) {
                ((HttpPost) request).setEntity(new StringEntity(body, DEFAULT_CHARSET));
            }
        }

        if (put) {
            request = new HttpPut(url);
            ((HttpPut) request).setEntity(doGetEntity(parameters));
        }

        if (delete) {
            request = new HttpDelete(doGetURL(url, parameters));
        }

        if (request == null) {
            throw new IllegalArgumentException("method error");
        }

        return request;
    }


    private void setHeader(HttpRequestBase request, Map<String, Object> headers) {
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isBlank(name) || value == null || StringUtils.isBlank(value.toString())) {
                continue;
            }
            request.setHeader(name, value.toString());
        }
    }


    private void setRequestConfig(HttpRequestBase request, int timeout, int socketTimeout, HttpHost proxy) {
        RequestConfig.Builder configBuilder = RequestConfig
                .custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(timeout);
        if (proxy != null) {
            configBuilder.setProxy(proxy);
        }
        request.setConfig(configBuilder.build());
    }


    private String doGetURL(String url, Map<String, Object> param) {
        if (param == null || param.size() == 0) {
            return url;
        }

        if (url == null || StringUtils.isBlank(url)) {
            throw new RuntimeException("url is not allow null");
        }

        StringBuilder sb = new StringBuilder(10);
        sb.append(url);

        if (!url.endsWith("?")) {
            sb.append("?");
        }

        for (Map.Entry<String, Object> me : param.entrySet()) {
            if (me.getValue() != null) {
                Object value = me.getValue();
                try {
                    sb.append(me.getKey()).append("=").append(URLEncoder.encode(value.toString(), CHARSET)).append("&");
                } catch (UnsupportedEncodingException e) {
                    logger.debug(e.toString(), e);
                }
            }
        }

        String urlString = sb.toString();
        urlString = urlString.substring(0, urlString.length() - 1);

        return urlString;
    }


    private void release(HttpRequestBase base, CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            logger.error(e.toString(), e);
        }
        if (base != null) {
            base.releaseConnection();
        }
    }

    private HttpEntity doGetEntity(Map<String, Object> param) {
        if (param == null) {
            return null;
        }

        List<NameValuePair> formParams = new ArrayList<>();
        for (Map.Entry<String, Object> me : param.entrySet()) {
            Object value = me.getValue();
            if (value != null) {
                formParams.add(new BasicNameValuePair(me.getKey(), value.toString()));
            }
        }

        return new UrlEncodedFormEntity(formParams, DEFAULT_CHARSET);
    }
}