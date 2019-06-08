package com.argyranthemum.common.api.exception;

import com.argyranthemum.common.api.base.Response;
import com.argyranthemum.common.core.constant.ConfigurationConst;
import com.argyranthemum.common.core.exception.BaseException;
import com.argyranthemum.common.core.exception.DefaultError;
import com.argyranthemum.common.core.serializer.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;
import java.util.Map;

public class ServerExceptionResolver extends AbstractHandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String contentType;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        this.writeLog(request, ex);

        PrintWriter pw = null;
        try {
            response.setContentType(contentType);
            //HTTP状态码始终返回200
            response.setStatus(HttpServletResponse.SC_OK);
            pw = response.getWriter();
            pw.write(JacksonUtil.getInstance().writeValueAsString(handleException(ex)));
        } catch (IOException e) {
            logger.error(e.toString(), e);
        } finally {
            if (pw != null)
                pw.close();
        }

        return new ModelAndView();
    }

    private void writeLog(HttpServletRequest request, Exception ex) {
        logger.error(request.getRequestURI() + " | " + JacksonUtil.write(request.getParameterMap()) + " | " + ex.toString(), ex);

        Map<String, String[]> parameterMap = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            logger.error("key:{}. value:{}", entry.getKey(), entry.getValue());
        }

        BufferedReader br;
        try {
            br = request.getReader();
            String str, wholeStr = "";
            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            if (StringUtils.isNotBlank(wholeStr)) {
                logger.error("requet body:{}", wholeStr);
            }
        } catch (IOException e) {
        }

    }

    private Response handleException(Exception ex) {
        Response response = new Response();

        // 系统自定义异常
        if (ex instanceof BaseException) {
            BaseException bbe = (BaseException) ex;
            response.setCode(bbe.getError().getErrorCode());
            response.setData(bbe.getMessage());
        }
        // 拒绝访问异常，当权限不足时发生
        else if (ex instanceof AccessDeniedException) {
            response.setCode(DefaultError.ACCESS_DENIED_ERROR.getErrorCode());
            response.setData(DefaultError.ACCESS_DENIED_ERROR.getErrorMessage());
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            response.setCode(DefaultError.METHOD_NOT_SUPPORTED_ERROR.getErrorCode());
            response.setData(DefaultError.METHOD_NOT_SUPPORTED_ERROR.getErrorMessage());
        }
        // 系统自带异常，主要指JVM抛出的异常
        else {
            response.setCode(DefaultError.SYSTEM_INTERNAL_ERROR.getErrorCode());
            response.setData(DefaultError.SYSTEM_INTERNAL_ERROR.getErrorMessage());
            if (!ConfigurationConst.IS_RELEASE) {
                String message = ex.getMessage();
                message = message == null ? "NullPointException" : message;
                response.setData(message);
            }
        }

        return response;
    }


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
