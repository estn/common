/**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.domain;

import com.argyranthemum.common.domain.pojo.DomainPage;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Description: Test DomainPageTest
 * @Author: estn.zuo
 * @CreateTime: 2016-12-06 16:32
 */
public class DomainPageTest {
    @Test
    public void testConstructor1() {
        DomainPage<Object> domainPage = new DomainPage<>(0, 10);
    }

    @Test
    public void testConstructor2() {
        DomainPage<Object> domainPage = new DomainPage<>(1, 101);
    }

    @Test
    public void testConstructor3() {
        DomainPage<Object> domainPage = new DomainPage<>(1, 10);

        Assert.assertNotNull(domainPage);
        Assert.assertEquals(domainPage.getPageCount(), new Integer(0));
        Assert.assertEquals(domainPage.getTotalCount(), new Integer(0));
    }

    @Test
    public void testConstructor4() {
        DomainPage<Object> domainPage = new DomainPage<>(1, 10, 101);

        Assert.assertNotNull(domainPage);
        Assert.assertEquals(domainPage.getPageCount(), new Integer(11));
        Assert.assertEquals(domainPage.getTotalCount(), new Integer(101));
    }

    @Test
    public void testConstructor5() {
        DomainPage<Object> domainPage = new DomainPage<>(1, 10, 100);

        Assert.assertNotNull(domainPage);
        Assert.assertEquals(domainPage.getPageCount(), new Integer(10));
        Assert.assertEquals(domainPage.getTotalCount(), new Integer(100));
    }
}