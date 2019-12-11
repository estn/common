/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.api.interceptor;

/**
 * @Description: KeyService
 * @CreateTime: 2019-05-27 11:12
 */
public abstract class SignatureService {

    public abstract String getKey(String appId, String platform, String version);

    public String getSecret() {
        return null;
    }

    public boolean verifyNonce(String nonce) {
        return true;
    }

    public boolean verifyTimestamp(long timestamp) {
        return true;
    }


}
