/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.jpa.domain;


import com.argyranthemum.common.core.util.BeanUtil;

import java.util.List;

/**
 * @Description: CopyDomainPage
 * @Author: estn.zuo
 * @CreateTime: 2017-05-13 19:44
 */
public class DomainSupport {

    public static DomainPage merge(DomainPage domainPage, Class clazz) {
        List list = BeanUtil.mergeList(domainPage.getDomains(), clazz);
        return copy(domainPage, list);
    }

    public static DomainCursor merge(DomainCursor domainCursor, Class clazz) {
        List list = BeanUtil.mergeList(domainCursor.getList(), clazz);
        return new DomainCursor(list, domainCursor.getNextCursor());
    }

    /**
     * 拷贝DomainPage对象
     *
     * @param domainPage 源对象
     * @param domains    目标对象的实体集合
     * @return
     */
    public static DomainPage copy(DomainPage domainPage, List domains) {
        DomainPage _domainPage = new DomainPage();
        _domainPage.setPageCount(domainPage.getPageCount());
        _domainPage.setPageIndex(domainPage.getPageIndex());
        _domainPage.setPageSize(domainPage.getPageSize());
        _domainPage.setTotalCount(domainPage.getTotalCount());
        _domainPage.setDomains(domains);
        return _domainPage;
    }


}
