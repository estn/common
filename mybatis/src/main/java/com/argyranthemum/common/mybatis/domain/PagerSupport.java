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
public class PagerSupport<S, T> {

    public <S, T> PagerSupport() {
    }

    public Pager<T> merge(IPage<S> page, Class<T> clazz) {
        List<T> list = BeanUtil.mergeList(page.getRecords(), clazz);
        return copy(page, list);
    }

    public Pager<T> copy(IPage<S> page, List<T> list) {
        Pager<T> pager = new Pager<>(page.getCurrent(), page.getSize());
        pager.setTotal(page.getTotal());
        pager.setRecords(list);
        return pager;
    }
}
