package com.argyranthemum.common.api.context;

import com.argyranthemum.common.core.pojo.Platform;
import com.argyranthemum.common.core.pojo.Version;
import com.argyranthemum.common.core.support.SignSupport;
import com.argyranthemum.common.core.util.encrypt.MD5Util;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


/**
 * @Description: 请求Context
 * @Author: liubo20
 * @CreateTime: 2018-11-01 10:35
 */
public class RequestContext {

    public static final String PLATFORM = "platform";
    public static final String VERSION = "version";
    public static final String DEVICE_ID = "deviceId";
    public static final String TOKEN = "token";
    public static final String TIMESTAMP = "timestamp";
    public static final String STAMP = "stamp";
    public static final String SIGN = "sign";
    public static final String DEBUG = "debug";
    public static final String SECRET = "secret";

    private static final ThreadLocal<RequestContextHolder> requestThreadLocal = new NamedThreadLocal<>("Request ThreadLocal");

    public static RequestContextHolder get() {
        return requestThreadLocal.get();
    }

    public static void remove() {
        requestThreadLocal.remove();
    }

    /**
     * 初始化并解析参数
     *
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {

        Platform platform = Platform.from(request.getParameter(PLATFORM));
        Version version = Version.from(request.getParameter(VERSION));
        String timestampString = request.getParameter(TIMESTAMP);
        Long timestamp = null;
        if (StringUtils.isNotBlank(timestampString)) {
            timestamp = Long.parseLong(timestampString);
        }

        String debugString = request.getParameter(DEBUG);
        Boolean debug = false;
        if (StringUtils.isNotBlank(debugString)) {
            debug = Boolean.parseBoolean(debugString);
        }
        RequestContextHolder holder = new RequestContextHolder.Builder()
                .setRequest(request)
                .setResponse(response)
                .setIp(getRealIp(request))
                .setPlatform(platform)
                .setVersion(version)
                .setDeviceId(request.getParameter(DEVICE_ID))
                .setToken(request.getParameter(TOKEN))
                .setTimestamp(timestamp)
                .setStamp(request.getParameter(STAMP))
                .setSign(request.getParameter(SIGN))
                .setDebug(debug)
                .setSecret(request.getParameter(SECRET))
                .build();

        requestThreadLocal.set(holder);
    }

    public static String getRealIp(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }

    public static class RequestContextHolder {
        private Platform platform;  //平台
        private Version version;    //版本
        private String deviceId;    //设备ID
        private String token;       //TOKEN值
        private Long timestamp;     //时间戳
        private String stamp;       //邮戳唯一值
        private String sign;        //签名值
        private Boolean debug;      //调试模式
        private String secret;
        private String ip;          //访问IP
        private HttpServletRequest request;
        private HttpServletResponse response;

        private RequestContextHolder() {
        }

        public Platform getPlatform() {
            return platform;
        }

        public Version getVersion() {
            return version;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public String getToken() {
            return token;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public String getStamp() {
            return stamp;
        }

        public String getSign() {
            return sign;
        }

        public Boolean getDebug() {
            return debug;
        }

        public String getSecret() {
            return secret;
        }

        public String getIp() {
            return ip;
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public HttpServletResponse getResponse() {
            return response;
        }

        static class Builder {
            private Platform platform;  //平台
            private Version version;    //版本
            private String deviceId;    //设备ID
            private String token;       //TOKEN值
            private Long timestamp;     //时间戳
            private String stamp;       //邮戳唯一值
            private String sign;        //签名值
            private Boolean debug;      //调试模式
            private String secret;
            private String ip;          //访问IP
            private HttpServletRequest request;
            private HttpServletResponse response;

            public Builder() {
            }

            public RequestContextHolder build() {
                RequestContextHolder holder = new RequestContextHolder();
                holder.platform = this.platform;
                holder.version = this.version;
                holder.deviceId = this.deviceId;
                holder.token = this.token;
                holder.timestamp = this.timestamp;
                holder.stamp = this.stamp;
                holder.sign = this.sign;
                holder.debug = this.debug;
                holder.secret = this.secret;
                holder.ip = this.ip;
                holder.request = this.request;
                holder.response = this.response;
                return holder;
            }


            public Builder setPlatform(Platform platform) {
                this.platform = platform;
                return this;
            }

            public Builder setVersion(Version version) {
                this.version = version;
                return this;
            }

            public Builder setDeviceId(String deviceId) {
                this.deviceId = deviceId;
                return this;
            }

            public Builder setToken(String token) {
                this.token = token;
                return this;
            }

            public Builder setTimestamp(Long timestamp) {
                this.timestamp = timestamp;
                return this;
            }

            public Builder setStamp(String stamp) {
                this.stamp = stamp;
                return this;
            }

            public Builder setSign(String sign) {
                this.sign = sign;
                return this;
            }

            public Builder setDebug(boolean debug) {
                this.debug = debug;
                return this;
            }

            public Builder setSecret(String secret) {
                this.secret = secret;
                return this;
            }

            public Builder setIp(String ip) {
                this.ip = ip;
                return this;
            }

            public Builder setRequest(HttpServletRequest request) {
                this.request = request;
                return this;
            }

            public Builder setResponse(HttpServletResponse response) {
                this.response = response;
                return this;
            }
        }


    }
}
