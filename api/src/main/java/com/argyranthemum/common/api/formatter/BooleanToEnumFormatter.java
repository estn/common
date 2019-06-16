/**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.api.formatter;

import com.argyranthemum.common.core.enums.BooleanEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.util.Locale;

/**
 * @Description: <p>
 * <p>
 * @Author: estn.zuo
 * @CreateTime: 2016-12-09 15:46
 */
public class BooleanToEnumFormatter implements Formatter<BooleanEnum> {

    @Override
    public BooleanEnum parse(String source, Locale locale) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        if ("true".equalsIgnoreCase(source)) {
            return BooleanEnum.TRUE;
        }
        if ("false".equalsIgnoreCase(source)) {
            return BooleanEnum.FALSE;
        }
        throw new IllegalArgumentException("source format error. source:" + source);
    }

    @Override
    public String print(BooleanEnum booleanEnum, Locale locale) {
        return booleanEnum.name();
    }
}
