/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.auth;

/**
 * @Description: AuthService
 * @CreateTime: 2019-05-18 15:23
 */
public interface AuthTokenService {

    AuthToken retrieveToken(String token);
}
