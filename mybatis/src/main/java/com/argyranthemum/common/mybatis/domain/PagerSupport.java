/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.mybatis.domain;


import com.argyranthemum.common.core.util.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @Description: CopyDomainPage
 * @Author: estn.zuo
 * @CreateTime: 2017-05-13 19:44
 */
public class PagerSupport {

    public static Pager merge(IPage page, Class clazz) {
        List list = BeanUtil.mergeList(page.getRecords(), clazz);
        return copy(page, list);
    }

    public static Pager copy(IPage page, List list) {
        Pager pager = new Pager(page.getCurrent(), page.getSize());
        pager.setTotal(page.getTotal());
        pager.setRecords(list);
        return pager;
    }


}
