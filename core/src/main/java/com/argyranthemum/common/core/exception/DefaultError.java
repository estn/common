package com.argyranthemum.common.core.exception;

public enum DefaultError implements CommonError {

    SYSTEM_INTERNAL_ERROR("0001", "系统内部错误"),
    ACCESS_DENIED_ERROR("0002", "拒绝访问"),
    HTTP_STATUS_404("0003", "404异常 PATH:{}"),
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
