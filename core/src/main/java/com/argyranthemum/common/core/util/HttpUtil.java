package com.argyranthemum.common.core.util;

import com.argyranthemum.common.core.pojo.Result;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description: HTTP请求工具类，实现Http和Https两种协议
 * Author: estn.zuo
 * CreateTime: 2014-05-20 20:37
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

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


    private HttpUtil() {
    }

    public static Result get(String url) {
        return get(url, null, DEFAULT_CONNECTION_TIMEOUT, CHARSET);
    }

    public static Result get(String url, String charset) {
        return get(url, null, DEFAULT_CONNECTION_TIMEOUT, charset);
    }

    public static Result get(String url, Map<String, Object> param) {
        return get(url, param, DEFAULT_CONNECTION_TIMEOUT, CHARSET);
    }

    public static Result get(String url, Map<String, Object> param, String charset) {
        return get(url, param, DEFAULT_CONNECTION_TIMEOUT, charset);
    }

    /**
     * 发送GET请求
     *
     * @param url   请求URL地址
     * @param param 请求参数
     * @return 字符串结果
     */
    public static Result get(String url, Map<String, Object> param, int timeout, String charset) {
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        Result result;
        try {
            httpGet = new HttpGet(doGetURL(url, param));

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                    .setConnectTimeout(timeout).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);

            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            result = new Result(statusCode, IOUtils.toString(response.getEntity().getContent(), charset));
        } catch (Exception e) {
            result = new Result(500, e.getMessage());
            logger.error(e.toString(), e);
        } finally {
            release(httpGet, response);
        }
        return result;
    }

    /**
     * @param url
     * @param param 请求参数
     * @return
     */
    public static Result post(String url, Map<String, Object> param) {
        return post(url, param, null, null, DEFAULT_CONNECTION_TIMEOUT);
    }

    /**
     * @param url
     * @param param   请求参数
     * @param headers 请求头
     * @return
     */
    public static Result post(String url, Map<String, Object> param, Map<String, String> headers) {
        return post(url, param, null, headers, DEFAULT_CONNECTION_TIMEOUT);
    }

    /**
     * @param url
     * @return
     */
    public static Result post(String url) {
        return post(url, null, null, null, DEFAULT_CONNECTION_TIMEOUT);
    }


    /**
     * @param url
     * @param body 请求体
     * @return
     */
    public static Result post(String url, String body) {
        return post(url, null, body, null, DEFAULT_CONNECTION_TIMEOUT);
    }

    /**
     * @param url
     * @param body    请求体
     * @param headers 请求头
     * @return
     */
    public static Result post(String url, String body, Map<String, String> headers) {
        return post(url, null, body, headers, DEFAULT_CONNECTION_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url     请求URL地址
     * @param param   请求参数
     * @param body    请求体
     * @param headers 请求头
     * @param timeout 超时时间
     * @return
     */
    public static Result post(String url, Map<String, Object> param, String body, Map<String, String> headers, int timeout) {
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        Result result = null;
        try {
            httpPost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                    .setConnectTimeout(timeout).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);

            /** 1.设置请求参数 */
            if (param != null) {
                httpPost.setEntity(doGetEntity(param));
            }

            /** 2.设置请求头 */
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }

            /** 3.设置请求体 */
            if (body != null) {
                httpPost.setEntity(new StringEntity(body, DEFAULT_CHARSET));
            }

            response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            result = new Result(statusCode, IOUtils.toString(response.getEntity().getContent(), CHARSET));
        } catch (IOException e) {
            result = new Result(500, e.getMessage());
            logger.error(e.toString(), e);
        } finally {
            release(httpPost, response);
        }
        return result;
    }


    /**
     * 发送PUT请求
     *
     * @param url   请求URL地址
     * @param param 请求参数
     * @return
     */
    public static Result put(String url, Map<String, Object> param) {
        HttpPut httpPut = null;
        CloseableHttpResponse response = null;
        Result result = null;
        try {
            httpPut = new HttpPut(url);
            httpPut.setEntity(doGetEntity(param));

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                    .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT).build();//设置请求和传输超时时间
            httpPut.setConfig(requestConfig);

            response = client.execute(httpPut);
            int statusCode = response.getStatusLine().getStatusCode();
            result = new Result(statusCode, IOUtils.toString(response.getEntity().getContent(), CHARSET));
        } catch (Exception e) {
            result = new Result(500, e.getMessage());
            logger.error(e.toString(), e);
        } finally {
            release(httpPut, response);
        }
        return result;
    }

    /**
     * 发送DELETE请求
     *
     * @param url   请求URL地址
     * @param param 请求参数
     * @return
     */
    public static Result delete(String url, Map<String, Object> param) {
        HttpDelete httpDelete = null;
        CloseableHttpResponse response = null;
        Result result = null;
        try {
            httpDelete = new HttpDelete(doGetURL(url, param));
            response = client.execute(httpDelete);
            int statusCode = response.getStatusLine().getStatusCode();
            result = new Result(statusCode, IOUtils.toString(response.getEntity().getContent(), CHARSET));
        } catch (Exception e) {
            result = new Result(500, e.getMessage());
            logger.error(e.toString(), e);
        } finally {
            release(httpDelete, response);
        }
        return result;
    }

    /**
     * 释放资源
     *
     * @param base
     * @param response
     */
    private static void release(HttpRequestBase base, CloseableHttpResponse response) {
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

    /**
     * 将map集合封装成Entity对象
     *
     * @param param
     * @return
     */
    private static HttpEntity doGetEntity(Map<String, Object> param) {
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

    /**
     * 拼接请求字符串，将请求参数拼接到URL地址后面
     *
     * @param url   请求URL地址
     * @param param 参数
     * @return
     */
    private static String doGetURL(String url, Map<String, Object> param) {
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
                if (value == null) {
                    continue;
                }
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
}
