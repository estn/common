package com.argyranthemum.common.domain.pojo;

import java.util.List;

public class DomainCursor<T> {
    public static final Integer START_CURSOR = 0;
    public static final Integer END_CURSOR = -1;
    private final List<T> list;
    private final Integer nextCursor;

    public DomainCursor(List<T> list, Integer nextCursor) {
        this.list = list;
        this.nextCursor = nextCursor;
    }

    public List<T> getList() {
        return this.list;
    }

    public Integer getNextCursor() {
        return this.nextCursor;
    }
}