package com.argyranthemum.common.core.constant;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Locale;

/**
 * Description: 系统常量，主要放置一些系统级别的常量，不需要用户在配置文件中设置
 * Author: Estn
 * CreateTime: 2014-07-10 01:17
 */
public class SystemConst {

    private SystemConst() {
    }

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static final String UTF8 = "UTF-8";

    // 时间格式
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    //mysql DATE_FORMAT 时间格式化
    public static final String SQL_DATE_FORMAT = "%Y-%m-%d";
    // i18n
    public static final Locale LOCALE = Locale.CHINA;
    // token在请求头中的key
    public static final String TOKEN_KEY = "token";
    // 分隔符
    public static final String SEPARATOR_SYMBOL = ",";

    public static final String REDIS_KEY_SEPARATOR_SYMBOL = ":";

    public static final Double DEFAULT_SEQUENCE = 1.0;

    //将来某个时间点,泛指将来
    public static final Date FUTURE_TIME = new Date(2500000000000L);

    public static final String RESOURCE_DIR = "resources";

    public static final String SUCCESS = "SUCCESS";

    public static final String FAIL = "FAIL";

    /**
     * 常用正则表达式
     */
    public interface Pattern {
        String STATIC_RESOURCE_REGULAR_EXPRESSION = "^.+\\.(jpg|jpeg|gif|png|swf|rar|zip|css|js|js\\.map|apk|ipa|bmp|pdf)$";
        String URL_REGULAR_EXPRESSION = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=()]*)?$";
        String EMAIL_REGULAR_EXPRESSION = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    }

    /**
     * 常用角色定义
     */
    public interface Role {
        String ROLE_ADMIN = "ROLE_ADMIN";
        String ROLE_USER = "ROLE_USER";
        String ROLE_VIP = "ROLE_VIP";
    }

}
