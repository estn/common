/**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pojo;


import com.argyranthemum.common.core.util.enums.IncomeRoleEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: User
 * @Author: estn.zuo
 * @CreateTime: 2016-11-12 16:59
 */
public class User {

    private Long id;

    private String username;

    private Integer age;

    private String password;

    private String homeLocation;

    private Date createTime;

    private BigDecimal income;

    private IncomeRoleEnum incomeRole;

    private IncomeRoleEnum incomeRole2;

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

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public IncomeRoleEnum getIncomeRole() {
        return incomeRole;
    }

    public void setIncomeRole(IncomeRoleEnum incomeRole) {
        this.incomeRole = incomeRole;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public IncomeRoleEnum getIncomeRole2() {
        return incomeRole2;
    }

    public void setIncomeRole2(IncomeRoleEnum incomeRole2) {
        this.incomeRole2 = incomeRole2;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", homeLocation='" + homeLocation + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
