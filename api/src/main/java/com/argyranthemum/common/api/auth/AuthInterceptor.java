package com.argyranthemum.common.api.auth;

import com.argyranthemum.common.core.auth.Auth;
import com.argyranthemum.common.core.auth.AuthService;
import com.argyranthemum.common.core.auth.AuthToken;
import com.argyranthemum.common.core.auth.TargetContext;
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
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
            if (auth != null) {

                //1.获取Token
                String tokenValue = this.doFetchTokenValue(request);
                if (tokenValue == null) {
                    throw new BaseException(DefaultError.TOKEN_NOT_FOUND);
                }

                //2.验证Token
                AuthToken authToken = authService.retrieveToken(tokenValue);

                if (authToken == null) {
                    throw new BaseException(DefaultError.TOKEN_ERROR);
                }

                if (authToken.expired()) {
                    throw new BaseException(DefaultError.TOKEN_EXPIRY);
                }

                String targetId = authToken.targetId();
                if (StringUtils.isBlank(targetId)) {
                    throw new BaseException(DefaultError.ACCESS_DENIED_ERROR);
                }

                logger.debug("targetId:" + targetId);

                //3.验证参数
                String authParameter = auth.parameter();
                if (StringUtils.isNotBlank(authParameter)) {
                    String parameterValue = ParameterUtil.get(request, handler, authParameter);
                    if (!targetId.equals(parameterValue)) {
                        throw new BaseException(DefaultError.TOKEN_IS_NOT_ALLOW);
                    }
                }

                //4. 设置TargetThreadLocal
                TargetContext.set(Long.parseLong(targetId));
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
                                @Nullable Exception ex) throws Exception {
        TargetContext.remove();
    }

}