package com.argyranthemum.common.api.interceptor;

import com.argyranthemum.common.core.auth.Auth;
import com.argyranthemum.common.core.auth.AuthToken;
import com.argyranthemum.common.core.auth.AuthTokenContext;
import com.argyranthemum.common.core.auth.AuthTokenService;
import com.argyranthemum.common.core.constant.SystemConst;
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
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    private AuthTokenService authTokenService;

    public TokenInterceptor(AuthTokenService authService) {
        this.authTokenService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

            Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
            if (auth == null) {
                auth = ((HandlerMethod) handler).getBeanType().getAnnotation(Auth.class);
            }

            if (auth != null) {
                String tokenValue = this.doFetchTokenValue(request);
                if (StringUtils.isBlank(tokenValue)) {
                    throw new BaseException(DefaultError.TOKEN_NOT_FOUND);
                }
                AuthToken authToken = authTokenService.retrieveToken(tokenValue);
                AuthTokenContext.set(authToken);
            }
        }

        return super.preHandle(request, response, handler);
    }

    private String doFetchTokenValue(HttpServletRequest request) {
        String tokenValue = request.getParameter(SystemConst.TOKEN_KEY);
        if (StringUtils.isBlank(tokenValue)) {
            tokenValue = request.getHeader(SystemConst.TOKEN_KEY);
        }
        return tokenValue;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {
        AuthTokenContext.remove();

    }

}