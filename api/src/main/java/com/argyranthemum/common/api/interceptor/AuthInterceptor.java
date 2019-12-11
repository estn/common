package com.argyranthemum.common.api.interceptor;

import com.argyranthemum.common.api.context.ParameterUtil;
import com.argyranthemum.common.core.auth.Auth;
import com.argyranthemum.common.core.auth.AuthToken;
import com.argyranthemum.common.core.auth.TokenContext;
import com.argyranthemum.common.core.constant.ConfigurationConst;
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
import java.util.List;

/**
 * @Description: Auth拦截器
 * <p>
 * 判断该数据是否属于该用户
 * @Author: estn.zuo
 * @CreateTime: 2016-12-15 16:44
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private String parameter;

    private String secret;

    public AuthInterceptor() {
    }

    public AuthInterceptor(String parameter) {
        this.parameter = parameter;
    }

    public AuthInterceptor(String parameter, String secret) {
        this.parameter = parameter;
        this.secret = secret;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
            if (auth == null) {
                auth = ((HandlerMethod) handler).getBeanType().getAnnotation(Auth.class);
            }

            if (StringUtils.isNotBlank(secret) && !ConfigurationConst.IS_RELEASE) {
                String secret = request.getParameter("secret");
                if (this.secret.equals(secret)) {
                    return super.preHandle(request, response, handler);
                }
            }

            if (auth != null) {
                AuthToken authToken = TokenContext.get();
                if (authToken == null) {
                    throw new BaseException(DefaultError.TOKEN_ERROR);
                }

                if (authToken.expired()) {
                    throw new BaseException(DefaultError.TOKEN_EXPIRY);
                }

//                this.checkRoles(auth, authToken);

                this.checkTargetId(request, handler, auth, authToken);
            }
        }

        return super.preHandle(request, response, handler);
    }

    private void checkTargetId(HttpServletRequest request, Object handler, Auth auth, AuthToken authToken) {
        Long targetId = authToken.targetId();
        if (targetId == null) {
            throw new BaseException(DefaultError.ACCESS_DENIED_ERROR);
        }

        //1.从parameter获取参数
        String authParameter = auth.parameter();

        //2.从value获取参数
        if (StringUtils.isBlank(authParameter)) {
            authParameter = auth.value();
        }

        //3.最后使用通用的参数
        if (StringUtils.isBlank(authParameter)) {
            authParameter = parameter;
        }

        if (StringUtils.isNotBlank(authParameter)) {
            String parameterValue = ParameterUtil.get(request, handler, authParameter);
            if (!targetId.toString().equals(parameterValue)) {
                throw new BaseException(DefaultError.TOKEN_IS_NOT_ALLOW);
            }
        }
    }

    private void checkRoles(Auth auth, AuthToken authToken) {

        if (auth.roles().length == 0) {
            return;
        }

        boolean flag = false;
        String[] authRoles = auth.roles();
        if (authRoles.length > 0) {
            List<String> roles = authToken.roles();
            if (roles != null && roles.size() > 0) {
                for (String authRole : authRoles) {
                    if (roles.contains(authRole)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        if (!flag) {
            throw new BaseException(DefaultError.ACCESS_DENIED_ERROR);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        TokenContext.remove();
    }

}