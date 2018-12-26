/**
 * Copyright  2018  estn.zuo
 * All Right Reserved.
 */
package com.argyranthemum.common.jpa.condition;

/**
 * @Description: OrderBy
 * @Author: estn.zuo
 * @CreateTime: 2018-12-14 09:38
 */
public class Order {

    private String field;

    private OrderBy orderBy;


    public Order(String field, OrderBy orderBy) {
        this.field = field;
        this.orderBy = orderBy;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }
}
