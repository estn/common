/**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pojo;


import com.argyranthemum.common.core.util.enums.IncomeEnum;

/**
 * @Description: UserPojo
 * @Author: estn.zuo
 * @CreateTime: 2016-11-12 17:00
 */
public class UserPojo {

    private String id;

    private int age;

    private String username;

    private String password;

    private IncomeEnum incomeRole;

    private String incomeRole2;

    private String income;


    public IncomeEnum getIncomeRole() {
        return incomeRole;
    }

    public void setIncomeRole(IncomeEnum incomeRole) {
        this.incomeRole = incomeRole;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIncomeRole2() {
        return incomeRole2;
    }

    public void setIncomeRole2(String incomeRole2) {
        this.incomeRole2 = incomeRole2;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
