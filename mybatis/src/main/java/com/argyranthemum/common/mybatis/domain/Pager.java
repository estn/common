package com.argyranthemum.common.mybatis.domain;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Pager<T> extends Page<T> {
    private static final long serialVersionUID = 5194933845448697148L;

    /**
     * 排序字段信息
     */
    @JsonIgnore
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL
     */
    @JsonIgnore
    private boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    @JsonIgnore
    private boolean searchCount = true;


    public Pager(long current, long size) {
        super(current, size);
    }

    @Override
    public List<OrderItem> orders() {
        return super.orders();
    }
}