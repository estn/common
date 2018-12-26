package com.argyranthemum.common.core.util.enums;


/**
 * @Description: 返利角色
 * @Author: estn.zuo
 * @CreateTime: 2014-10-13 15:48
 */
public enum IncomeRoleEnum {

    MERCHANT("商户本金"),

    PLATFORM("平台收入"),

    SELF("消费返利"),

    FIRST("一级会员"),

    SECOND("二级会员"),

    THIRD("三级会员"),

    REFUND("用户退货"),;

    private String description;

    IncomeRoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
