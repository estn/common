package com.argyranthemum.common.jpa.entity;


import com.argyranthemum.common.jpa.domain.BaseDomain;

import javax.persistence.Entity;

@Entity
public class Article extends BaseDomain {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}