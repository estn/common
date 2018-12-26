/**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.api.formatter;

import com.argyranthemum.common.core.util.pattern.handler.concrete.DateHandlerSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.util.Date;
import java.util.Locale;

/**
 * @Description: <p>
 * <p>
 * 将String格式的时间转换成Date格式的时间,尝试各种时间格式转换
 * <li>yyyy-MM-dd HH:mm:ss</li>
 * <li>yyyy-MM-dd'T'HH:mm:ss</li>
 * <li>yyyy-MM-dd</li>
 * <li>时间戳</li>
 * <p>
 * @Author: estn.zuo
 * @CreateTime: 2016-12-09 15:46
 */
public class StringToDateFormatter implements Formatter<Date> {

    @Override
    public Date parse(String source, Locale locale) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return DateHandlerSupport.handle(source);
    }

    @Override
    public String print(Date object, Locale locale) {
        return object.toString();
    }
}
