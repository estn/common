package com.argyranthemum.common.core.pojo;

import javax.annotation.Nullable;
import java.util.Objects;

public enum Platform {
    ANDROID,
    IOS;

    @Nullable
    public static Platform from(@Nullable String raw) {
        if (Objects.isNull(raw)) {
            return null;
        }
        try {
            return Platform.valueOf(raw);
        } catch (Exception e) {
            return null;
        }
    }

}
