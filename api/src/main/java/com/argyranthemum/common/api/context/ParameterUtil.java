/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.api.context;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description: ParameterUtil
 * @CreateTime: 2019-05-20 22:41
 */
public class ParameterUtil {

    private static final Logger logger = LoggerFactory.getLogger(ParameterUtil.class);

    public static String get(HttpServletRequest request, Object handler, String parameterName) {

        //1.请求参数
        String parameterValue = request.getParameter(parameterName);

        //2.请求头
        if (StringUtils.isBlank(parameterValue)) {
            parameterValue = request.getHeader(parameterName);
        }

        //3.URI模板参数
        if (StringUtils.isBlank(parameterValue)) {
            if (handler instanceof HandlerMethod) {
                RequestMapping clazzRequestMapping = ((HandlerMethod) handler).getBeanType().getAnnotation(RequestMapping.class);
                if (clazzRequestMapping != null) {
                    String[] clazzRequestMappingValues = clazzRequestMapping.value();
                    if (clazzRequestMappingValues.length > 0) {
                        String clazzRequestMappingString = clazzRequestMappingValues[0];
                        RequestMapping methodRequestMapping = ((HandlerMethod) handler).getMethod().getAnnotation(RequestMapping.class);
                        if (methodRequestMapping != null) {
                            String[] methodRequestMappingValues = methodRequestMapping.value();
                            if (methodRequestMappingValues.length > 0) {
                                String methodRequestMappingString = methodRequestMappingValues[0];
                                UriTemplate template = new UriTemplate(clazzRequestMappingString + methodRequestMappingString);
                                Map<String, String> parameters = template.match(request.getRequestURI());
                                parameterValue = parameters.get(parameterName);
                            }
                        }//  end of methodRequestMapping != null
                    }
                } // end of clazzRequestMapping != null
            }
        }
        logger.debug("ParameterUtil,{} = {}", parameterName, parameterValue);
        return parameterValue;
    }
}
