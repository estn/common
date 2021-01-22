/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util;

import com.argyranthemum.common.core.constant.SystemConst;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

import static com.argyranthemum.common.core.constant.SystemConst.DATE_FORMAT;
import static com.argyranthemum.common.core.constant.SystemConst.TIME_FORMAT;

/**
 * @Description: DateUtil
 * @CreateTime: 2019-06-01 11:44
 */
public class DateUtil {

    public static Date parseDate(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            throw new DateTimeException(text);
        }
    }

    public static Date parseTime(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            throw new DateTimeException(text);
        }
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static String formatDate(DateTime dateTime) {
        return formatDate(dateTime.toDate());
    }

    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SystemConst.TIME_FORMAT);
        return sdf.format(date);
    }

    public static String formatTime(DateTime dateTime) {
        return formatTime(dateTime.toDate());
    }

    public static int getYear() {
        return new DateTime().getYear();
    }

    public static int getYear(Date date) {
        return new DateTime(date).getYear();
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
