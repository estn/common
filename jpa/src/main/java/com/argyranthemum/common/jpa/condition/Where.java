/**
 * Copyright  2018  estn.zuo
 * All Right Reserved.
 */
package com.argyranthemum.common.jpa.condition;

/**
 * @Description: Condition
 * @Author: estn.zuo
 * @CreateTime: 2018-12-14 10:04
 */
public class Where {

    private String field;  //查询字段名称

    private Operation operation; //筛选操作 =、<=等

    private Object value; //查询值

    public Where(String field, Object value) {
        this.field = field;
        this.operation = Operation.EQUALS;
        this.value = value;
    }

    public Where(String field, Operation operation, Object value) {
        this.field = field;
        this.operation = operation;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public Operation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }
}
