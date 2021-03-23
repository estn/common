package com.argyranthemum.common.core.exception;


import org.apache.commons.lang3.StringUtils;

/**
 * 系统异常，自定义异常必须继承本类,业务模块需要抛出业务异常时使用该异常。
 * 业务异常时指定义了对应的错误代码和错误消息的异常
 * User: Estn
 * Date: 12-10-24
 * Time: 下午4:32
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -6293662498600553602L;

    private CommonError error = DefaultError.SYSTEM_INTERNAL_ERROR;

    private String exceptionMessage;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
        this.exceptionMessage = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionMessage = message;
    }

    public BaseException(String template, Object... message) {
        super(template + " | " + StringUtils.join(message, ","));
        this.exceptionMessage = String.format(template, message);
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.exceptionMessage = error.getErrorMessage();
    }


    public BaseException(CommonError error) {
        super(error.getErrorMessage());
        this.error = error;
        this.exceptionMessage = error.getErrorMessage();
    }

    public BaseException(CommonError error, Object... message) {
        super(error.getErrorMessage() + " | " + StringUtils.join(message, ","));
        this.error = error;
        this.exceptionMessage = String.format(error.getErrorMessage(), message);
    }

    public BaseException(String message, Throwable cause, CommonError error) {
        super(message, cause);
        this.error = error;
        this.exceptionMessage = error.getErrorMessage();
    }

    public BaseException(Throwable cause, CommonError error) {
        super(cause);
        this.error = error;
        this.exceptionMessage = error.getErrorMessage();
    }

    public CommonError getError() {
        return error;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
