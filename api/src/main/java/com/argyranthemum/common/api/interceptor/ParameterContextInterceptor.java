/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.api.interceptor;

import com.argyranthemum.common.core.exception.BaseException;
import com.argyranthemum.common.core.support.SignSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description: StaffRoleInterceptor
 * @CreateTime: 2019-06-02 16:03
 */
public class ParameterContextInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ParameterContextInterceptor.class);

    private List<String> parameters;

    public ParameterContextInterceptor(List<String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = SignSupport.convert(request.getParameterMap());
        for (String parameter : parameters) {
            if (!map.containsKey(parameter)) {
                throw new BaseException(parameter + " is miss");
            }
        }
        return super.preHandle(request, response, handler);
    }


}
