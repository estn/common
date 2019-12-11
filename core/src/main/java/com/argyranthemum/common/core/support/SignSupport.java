/**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.support;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description: SignSupport
 * @Author: estn.zuo
 * @CreateTime: 2016-12-09 16:14
 */
public class SignSupport {

    private SignSupport() {
    }

    public static String sign(Map<String, Object> param, String key) {
        String linkString = createLinkString(param);
        linkString = linkString + "&" + key;
        return DigestUtils.md5DigestAsHex(linkString.getBytes(StandardCharsets.UTF_8)).toLowerCase();
    }

    /**
     * 将Map中的Value为数组的类型转换成Object类型
     *
     * @param param
     * @return
     */
    public static Map<String, Object> convert(Map<String, String[]> param) {
        Map<String, Object> result = Maps.newHashMap();
        for (String key : param.keySet()) {
            String[] values = param.get(key);
            if (values != null && values.length > 0) {
                StringBuilder value = new StringBuilder();
                for (String _v : values) {
                    value.append(_v).append(",");
                }
                value = new StringBuilder(value.substring(0, value.length() - 1));
                result.put(key, value.toString());
            }
        }
        return result;
    }


    /**
     * 1.默认除去数组中的空值、sign参数和token参数
     * 2.把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {
        return createLinkString(params, new String[]{"token", "sign"});
    }


    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params  需要排序并参与字符拼接的参数组
     * @param ignores 需要排除的KEY
     * @return
     */
    public static String createLinkString(Map<String, Object> params, String[] ignores) {
        if (params == null) {
            return null;
        }

        List<String> ignoreList = new ArrayList<String>();
        Collections.addAll(ignoreList, ignores);

        /* 1.过滤 */
        params = parameterFilter(params, ignoreList);

        /* 2.排序Key */
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        /* 3.拼接 */
        StringBuilder sb = new StringBuilder(keys.size() * 4);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);
            if (i == keys.size() - 1) {
                sb.append(key).append("=").append(value);
            } else {
                sb.append(key).append("=").append(value).append("&");
            }
        }

        return sb.toString();
    }

    private static Map<String, Object> parameterFilter(Map<String, Object> params, List<String> ignore) {
        Map<String, Object> result = Maps.newHashMap();
        if (params == null || params.size() <= 0) {
            return result;
        }
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (ignore.contains(key)) {
                continue;
            }
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

}
