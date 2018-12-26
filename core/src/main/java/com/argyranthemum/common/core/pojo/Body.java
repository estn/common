/*
 * 文件名：Body.java
 * 版权：Copyright (c) 2014, doit.com All Rights Reserved.
 * 描述：
 * 修改人：zuo liubo
 * 修改时间：2013-8-16 下午9:35:19
 */
package com.argyranthemum.common.core.pojo;

import java.io.Serializable;

/**
 * @author zuo liubo
 * @description Body类型数据结构
 * @date 2013-8-16
 */
public class Body implements Serializable {

    private static final long serialVersionUID = 1L;

    private BodyType type;

    private String value;

    private String title;

    public Body() {
    }

    public Body(BodyType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Body(BodyType type, String value, String title) {
        this.type = type;
        this.value = value;
        this.title = title;
    }

    public BodyType getType() {
        return type;
    }

    public void setType(BodyType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Body{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }


}
