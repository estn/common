/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.auth;

/**
 * @Description: Token
 * @CreateTime: 2019-05-18 15:38
 */
public interface AuthToken {

    /**
     * token值
     */
    String value();

    /**
     * token关联ID
     */
    String targetId();

    /**
     * token是否过期
     */
    Boolean expired();
}
