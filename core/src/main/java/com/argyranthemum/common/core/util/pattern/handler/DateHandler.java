/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler;

import java.util.Date;

/**
 * @Description: String$DateHandler
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 21:15
 */
public abstract class DateHandler {

    private DateHandler nextHandler;

    public Date handleRequest(String value) {
        Date date = this.handle(value);
        if (date == null && nextHandler != null) {
            return nextHandler.handleRequest(value);
        }
        return date;
    }

    public void setNextHandler(DateHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract Date handle(String value);

}
