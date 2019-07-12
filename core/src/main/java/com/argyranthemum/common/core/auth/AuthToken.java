/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.auth;

import java.util.List;

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
     * token关联类型
     *
     * @return
     */
    String type();

    /**
     * token是否过期
     */
    Boolean expired();


    default List<String> roles() {
        return null;
    }
}
