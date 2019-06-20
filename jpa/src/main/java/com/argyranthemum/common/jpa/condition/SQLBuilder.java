/**
 * Copyright  2018  estn.zuo
 * All Right Reserved.
 */
package com.argyranthemum.common.jpa.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: SQLBuilder
 * @Author: estn.zuo
 * @CreateTime: 2018-12-14 09:40
 */
public class SQLBuilder {

    private List<String> selects;

    private List<Where> ands;

    private List<Where> ors;

    private List<Order> orders;

    private List<Group> groups;

    private Boolean available;

    private SQLBuilder() {
        this.selects = new ArrayList<>();
        this.ands = new ArrayList<>();
        this.ors = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.available = true;
    }

    public static SQLBuilder builder() {
        return new SQLBuilder();
    }

    public SQLBuilder and(Where... wheres) {
        this.ands.addAll(Arrays.asList(wheres));
        return this;
    }

    public SQLBuilder and(String field, Operation operation, Object value) {
        Where where = new Where(field, operation, value);
        this.ands.add(where);
        return this;
    }

    public SQLBuilder and(String field, Object value) {
        Where where = new Where(field, value);
        this.ands.add(where);
        return this;
    }

    public SQLBuilder or(Where... wheres) {
        this.ors.addAll(Arrays.asList(wheres));
        return this;
    }

    public SQLBuilder or(String field, Operation operation, Object value) {
        Where where = new Where(field, operation, value);
        this.ors.add(where);
        return this;
    }

    public SQLBuilder or(String field, Object value) {
        Where where = new Where(field, value);
        this.ors.add(where);
        return this;
    }

    public SQLBuilder order(String field, OrderBy orderBy) {
        Order order = new Order(field, orderBy);
        this.orders.add(order);
        return this;
    }

    public SQLBuilder group(String name) {
        this.groups.add(new Group(name));
        return this;
    }

    public SQLBuilder select(String... selects) {
        this.selects.addAll(Arrays.asList(selects));
        return this;
    }

    public SQLBuilder available(boolean available) {
        this.available = available;
        return this;
    }

    public SQL build() {
        SQL sql = new SQL();
        sql.setSelects(selects);
        sql.setAnds(ands);
        sql.setOrs(ors);
        sql.setGroups(groups);
        sql.setOrders(orders);
        sql.setAvailable(available);
        return sql;
    }

}
