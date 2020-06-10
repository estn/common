/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

/**
 * @Description: DateUtil
 * @CreateTime: 2019-06-01 11:44
 */
public class DateUtil {

    public static Date parse(String text, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            throw new DateTimeException(text);
        }
    }

    public static Date startOfDay() {
        return new DateTime().withTimeAtStartOfDay().toDate();
    }

    public static Date endOfDay() {
        return new DateTime().plusDays(1).withTimeAtStartOfDay().plusSeconds(-1).toDate();
    }

    public static Date startOfDay(Date date) {
        return new DateTime(date).withTimeAtStartOfDay().toDate();
    }

    public static Date endOfDay(Date date) {
        return new DateTime(date).plusDays(1).withTimeAtStartOfDay().plusSeconds(-1).toDate();
    }

    public static DateTime startOfDay(DateTime date) {
        return new DateTime(date).withTimeAtStartOfDay();
    }

    public static DateTime endOfDay(DateTime date) {
        return new DateTime(date).plusDays(1).withTimeAtStartOfDay().plusSeconds(-1);
    }
}
