package com.argyranthemum.common.core.enums;

/**
 * @Description: AvailableEnum枚举
 * <p>
 * 用于逻辑删除数据库中的记录
 * <strong>不能将本字段拿来做任何业务逻辑<strong/>
 * </p>
 * @Author: estn.zuo
 * @CreateTime: 2016-12-15 14:36
 */
public enum AvailableEnum {

    /**
     * 不可用
     */
    UNAVAILABLE,

    /**
     * 可用
     */
    AVAILABLE
}
