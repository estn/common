package com.argyranthemum.common.core.auth;

import org.springframework.core.NamedThreadLocal;

public class TargetContext {

    private static final ThreadLocal<Long> targetThreadLocal = new NamedThreadLocal<>("Request ThreadLocal");

    public static Long get() {
        return targetThreadLocal.get();
    }

    public static void remove() {
        targetThreadLocal.remove();
    }

    public static void set(Long targetId) {
        targetThreadLocal.set(targetId);
    }


}
