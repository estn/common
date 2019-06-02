package com.argyranthemum.common.core.exception;

public enum DefaultError implements CommonError {

    SYSTEM_INTERNAL_ERROR("0001", "系统内部错误"),
    ACCESS_DENIED_ERROR("0002", "拒绝访问"),
    HTTP_STATUS_404("0003", "404异常 PATH:{}"),
    METHOD_NOT_SUPPORTED_ERROR("0004", "请求方法错误"),

    TOKEN_NOT_FOUND("0004", "未找到访问令牌"),
    TOKEN_ERROR("0005", "访问令牌错误"),
    TOKEN_EXPIRY("0006", "访问令牌过期"),
    TOKEN_IS_NOT_ALLOW("0007", "BALABALA, YOU ARE A BAD BOY"),

    ;

    String errorCode;

    String errorMessage;

    private static final String NS = "DFT";

    DefaultError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getNamespace() {
        return NS.toUpperCase();
    }

    public String getErrorCode() {
        return NS + "." + errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}