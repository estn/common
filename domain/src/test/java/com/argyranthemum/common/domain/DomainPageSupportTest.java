/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.domain;

import com.argyranthemum.common.domain.pojo.DomainPage;
import com.argyranthemum.common.domain.pojo.DomainSupport;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @Description: Test DomainPageSupportTest
 * @Author: estn.zuo
 * @CreateTime: 2017-08-08 12:40
 */
public class DomainPageSupportTest {
    @Test
    public void merge() throws Exception {
    }

    @Test
    public void copy() throws Exception {
        DomainPage domainPage = new DomainPage();
        domainPage.setPageIndex(2);
        domainPage.setPageSize(20);
        domainPage.setTotalCount(100);
        domainPage.setPageCount(5);
        domainPage.setDomains(new ArrayList());

        DomainPage result = DomainSupport.copy(domainPage, new ArrayList());

        Assert.assertEquals(result.getPageIndex(), domainPage.getPageIndex());
        Assert.assertEquals(result.getPageSize(), domainPage.getPageSize());
        Assert.assertEquals(result.getTotalCount(), domainPage.getTotalCount());
        Assert.assertEquals(result.getPageCount(), domainPage.getPageCount());
        Assert.assertNotNull(result.getDomains());
    }

}