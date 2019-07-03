package com.argyranthemum.common.core.auth;

import org.springframework.core.NamedThreadLocal;

public class AuthTokenContext {

    private static final ThreadLocal<AuthToken> targetThreadLocal = new NamedThreadLocal<>("Request ThreadLocal");

    public static AuthToken get() {
        return targetThreadLocal.get();
    }

    public static void remove() {
        targetThreadLocal.remove();
    }

    public static void set(AuthToken token) {
        targetThreadLocal.set(token);
    }


}
