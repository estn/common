/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler.concrete.date;



import com.argyranthemum.common.core.util.pattern.handler.DateHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: DateTimeDateHandler
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 21:32
 */
public class DateDateHandler extends DateHandler {
    @Override
    protected Date handle(String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(value);
        } catch (ParseException ignored) {
        }
        return null;
    }
}
