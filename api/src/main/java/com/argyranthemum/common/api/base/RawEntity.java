/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.api.base;

/**
 * @Description: 该实体无需包装直接返回
 * @Author: estn.zuo
 * @CreateTime: 2017-06-02 16:48
 */
@FunctionalInterface
public interface RawEntity {
    String getResult();
}
