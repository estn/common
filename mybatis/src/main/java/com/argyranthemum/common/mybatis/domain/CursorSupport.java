/**
 * Copyright  2019  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.mybatis.domain;

import com.argyranthemum.common.core.util.BeanUtil;

import java.util.List;

/**
 * @Description: CursorSupport
 * @CreateTime: 2019-12-08 11:14
 */
public class CursorSupport {

    public static CursorResult merge(CursorResult result, Class clazz) {
        List list = BeanUtil.mergeList(result.getRecords(), clazz);
        result.setRecords(list);
        return result;
    }
}
