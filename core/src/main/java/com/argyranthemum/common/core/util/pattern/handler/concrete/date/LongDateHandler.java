/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler.concrete.date;



import com.argyranthemum.common.core.util.pattern.handler.DateHandler;

import java.util.Date;

/**
 * @Description: LongDateHandler
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 21:27
 */
public class LongDateHandler extends DateHandler {

    @Override
    protected Date handle(String value) {
        try {
            return new Date(Long.parseLong(value));
        } catch (Exception ignored) {
        }
        return null;
    }
}
