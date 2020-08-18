/**
 * Copyright  2020  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.serializer;

/**
 * @Description: JacksonSerializerException
 * @CreateTime: 2020-08-18 14:37
 */
public class JacksonSerializerException extends RuntimeException {

    public JacksonSerializerException(String message, Exception e) {
        super(message, e);
    }
}
