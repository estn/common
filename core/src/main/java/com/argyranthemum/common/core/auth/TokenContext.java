package com.argyranthemum.common.core.auth;

import org.springframework.core.NamedThreadLocal;

public class TokenContext {

    private static final ThreadLocal<AuthToken> tokenThreadLocal = new NamedThreadLocal<>("Request ThreadLocal");

    public static AuthToken get() {
        return tokenThreadLocal.get();
    }

    public static void remove() {
        tokenThreadLocal.remove();
    }

    public static void set(AuthToken token) {
        tokenThreadLocal.set(token);
    }


}
