package com.argyranthemum.common.api.exception;

import com.argyranthemum.common.api.base.Response;
import com.argyranthemum.common.core.exception.BaseException;
import com.argyranthemum.common.core.exception.DefaultError;
import com.argyranthemum.common.core.serializer.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;

public class ServerExceptionResolver extends AbstractHandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String contentType;

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error(ex.toString(), ex);
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