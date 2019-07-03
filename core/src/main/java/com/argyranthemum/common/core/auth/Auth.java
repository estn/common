package com.argyranthemum.common.core.auth;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RUNTIME)
public @interface Auth {

    /**
     * 参数认证
     */
    String parameter() default "";

    @AliasFor("parameter")
    String value() default "";

    /**
     * 认证类型校验
     *
     * @return
     */
    String[] roles() default {};

}
