/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler.concrete;

import com.argyranthemum.common.core.util.pattern.handler.DateHandler;
import com.argyranthemum.common.core.util.pattern.handler.concrete.date.DateDateHandler;
import com.argyranthemum.common.core.util.pattern.handler.concrete.date.DateTimeDateHandler;
import com.argyranthemum.common.core.util.pattern.handler.concrete.date.DateTimeWithTDateHandler;
import com.argyranthemum.common.core.util.pattern.handler.concrete.date.LongDateHandler;

import java.util.Date;

/**
 * @Description: DateHandlerSupport
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 21:34
 */
public class DateHandlerSupport {

    public static Date handle(String value) {

        DateHandler dateTimeDateHandler = new DateTimeDateHandler();
        DateHandler dateTimeWithTDateHandler = new DateTimeWithTDateHandler();
        DateHandler longDateHandler = new LongDateHandler();
        DateHandler dateDateHandler = new DateDateHandler();

        dateTimeDateHandler.setNextHandler(dateTimeWithTDateHandler);

        dateTimeWithTDateHandler.setNextHandler(longDateHandler);

        longDateHandler.setNextHandler(dateDateHandler);

        Date date = dateTimeDateHandler.handleRequest(value);
        if (date == null) {
            throw new IllegalArgumentException();
        }

        return date;
    }
}
