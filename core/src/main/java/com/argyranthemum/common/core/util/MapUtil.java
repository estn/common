/**
 * Copyright  2021  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Description: MapUtil
 * @CreateTime: 2021-02-18 14:44
 */
public class MapUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

    public static String getString(Map map, String key) {
        Object value = map.get(key);
        if (value != null) {
            return value.toString();
        }
        logger.error("parse key:{} error. map:{}", key, map);
        return "";
    }

    public static int getInt(Map map, String key) {
        String value = getString(map, key);
        if (StringUtils.isNotBlank(value)) {
            return Integer.parseInt(value);
        }
        return 0;
    }
}
