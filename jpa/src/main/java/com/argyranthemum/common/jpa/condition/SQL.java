/**
 * Copyright  2018  estn.zuo
 * All Right Reserved.
 */
package com.argyranthemum.common.jpa.condition;

import java.util.List;

/**
 * @Description: JPA查询条件
 * @Author: estn.zuo
 * @CreateTime: 2018-12-14 09:38
 */
public class SQL {

    /**
     * 查询列
     */
    private List<String> selects;

    /**
     * 查询条件集合
     */
    private List<Where> wheres;

    /**
     * 排序字段
     */
    private List<Order> orders;

    /**
     * 分组字段
     */
    private List<Group> groups;

    /**
     * 是否查询只查询可用字段
     * 默认:true
     * true:只查询available=AVAILABLE的数据
     * false:查询全部的数据available=[AVAILABLE,UNAVAILABLE]
     */
    private Boolean available = true;

    SQL() {
    }

    public List<String> getSelects() {
        return selects;
    }

    public void setSelects(List<String> selects) {
        this.selects = selects;
    }

    public List<Where> getWheres() {
        return wheres;
    }

    public void setWheres(List<Where> wheres) {
        this.wheres = wheres;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
