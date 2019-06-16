package com.argyranthemum.common.api.interceptor;

import com.argyranthemum.common.api.context.ParameterUtil;
import com.argyranthemum.common.core.auth.Auth;
import com.argyranthemum.common.core.auth.TargetContext;
import com.argyranthemum.common.core.exception.BaseException;
import com.argyranthemum.common.core.exception.DefaultError;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: Auth拦截器
 * <p>
 * 判断该数据是否属于该用户
 * @Author: estn.zuo
 * @CreateTime: 2016-12-15 16:44
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
            if (auth == null) {
                auth = ((HandlerMethod) handler).getBeanType().getAnnotation(Auth.class);
            }

            if (auth != null) {
                Long targetId = TargetContext.get();
                if (targetId == null) {
                    throw new BaseException(DefaultError.ACCESS_DENIED_ERROR);
                }

                String authParameter = auth.parameter();
                if (StringUtils.isNotBlank(authParameter)) {
                    String parameterValue = ParameterUtil.get(request, handler, authParameter);
                    if (!targetId.toString().equals(parameterValue)) {
                        throw new BaseException(DefaultError.TOKEN_IS_NOT_ALLOW);
                    }
                }

            }
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }

}