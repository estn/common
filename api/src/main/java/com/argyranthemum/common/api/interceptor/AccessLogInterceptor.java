/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.api.interceptor;

import com.argyranthemum.common.api.context.RequestContext;
import com.argyranthemum.common.core.auth.AuthToken;
import com.argyranthemum.common.core.auth.TokenContext;
import com.argyranthemum.common.core.serializer.JacksonUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @Description: StaffRoleInterceptor
 * @CreateTime: 2019-06-02 16:03
 */
public class AccessLogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AccessLogInterceptor.class);

    private long size;

    private static final long DEFAULT_SIZE = 1024 * 100;

    public AccessLogInterceptor() {
        this.size = DEFAULT_SIZE;
    }

    public AccessLogInterceptor(long size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must gather than zero");
        }
        this.size = size;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            RequestContext.init(request, response);

            if (request.getContentLength() > size) {
                return true;
            }

            Map<String, String> parameterMap = convert(request.getParameterMap());
            AccessLog log = new AccessLog();
            AuthToken authToken = TokenContext.get();
            if (authToken != null) {
                Long id = authToken.targetId();
                if (id != null) {
                    log.setId(id.toString());
                }
            }

            log.setTime(new DateTime().toString());
            log.setIp(RequestContext.get().getIp());
            log.setMethod(request.getMethod());
            log.setUri(request.getRequestURI());
            log.setParameters(parameterMap);
            log.setHeaders(retrieveHeaders(request));
            log.setProject(retrieveProject(request.getRequestURI()));

            logger.info("{}", JacksonUtil.write(log));
        } catch (Exception e) {
            logger.error("record access log error.", e);
        }

        return super.preHandle(request, response, handler);
    }

    private String retrieveProject(String uri) {
        String[] strings = uri.split("/");
        if (strings.length > 2) {
            return strings[1];
        }
        return "";
    }

    private Map<String, String> retrieveHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = Maps.newHashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headerMap.put(headerName, headerValue);
        }
        return headerMap;
    }


    private Map<String, String> convert(Map<String, String[]> parameterMap) {
        Map<String, String> map = Maps.newHashMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            map.put(entry.getKey(), entry.getValue()[0]);
        }
        return map;
    }

    @Data
    @NoArgsConstructor
    private static class AccessLog {
        private String id;
        private String time;
        private String ip;
        private String method;
        private String uri;
        private String project;
        private Map<String, String> parameters;
        private Map<String, String> headers;
    }
}
