package com.argyranthemum.common.core.auth;

import org.springframework.core.NamedThreadLocal;

public class TokenContext {

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
