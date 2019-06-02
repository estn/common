package com.argyranthemum.common.core.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RUNTIME)
public @interface Auth {

    /**
     * 通过接口参数认证
     */
    String parameter() default "";

    /**
     * 通过接口返回值认证
     */
    String response() default "";

}
