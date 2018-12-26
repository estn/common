/**
 * Copyright  2018  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.domain;

import com.argyranthemum.common.domain.enumeration.AvailableEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Description: Test BaseDomainTest
 * @Author: estn.zuo
 * @CreateTime: 2018-12-25 10:54
 */
public class BaseDomainTest {

    @Test
    public void test001() {

        BaseDomain domain = new BaseDomain();
        Assert.assertNotNull(domain);
        Assert.assertNotNull(domain.getCreateTime());
        Assert.assertNull(domain.getUpdateTime());
        Assert.assertNotNull(domain.getAvailable());
        Assert.assertNotNull(domain.getUuid());

        Assert.assertEquals(AvailableEnum.AVAILABLE, domain.getAvailable());
    }

}