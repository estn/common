/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler;

/**
 * @Description: Handler
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 20:58
 */
public abstract class NumberHandler {

    private NumberHandler nextHandler;

    public Number handleRequest(Object v, Class parameterType) {

        if (getClazz().equals(parameterType)) {
            return this.handle(v.toString());
        } else {
            if (null != nextHandler) {
                return nextHandler.handleRequest(v, parameterType);
            }
        }
        throw new IllegalArgumentException();
    }

    public void setNextHandler(NumberHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract Class getClazz();

    protected abstract Number handle(String v);

}
