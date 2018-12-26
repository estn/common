/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler.concrete;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Description: Test NumberHandlerSupportTest
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 22:05
 */
public class NumberHandlerSupportTest {
    @Test
    public void handle() throws Exception {
        Number handle = NumberHandlerSupport.handle("1", Integer.class);
        Assert.assertTrue(handle instanceof Integer);
        Assert.assertEquals(handle, 1);

        handle = NumberHandlerSupport.handle("1", Long.class);
        Assert.assertTrue(handle instanceof Long);
        Assert.assertEquals(handle, 1L);

        handle = NumberHandlerSupport.handle("1", Short.class);
        Assert.assertTrue(handle instanceof Short);
        Assert.assertEquals(handle, new Short("1"));

        handle = NumberHandlerSupport.handle("1.01", BigDecimal.class);
        Assert.assertTrue(handle instanceof BigDecimal);
        Assert.assertEquals(handle, new BigDecimal("1.01"));

        handle = NumberHandlerSupport.handle("1.0f", Float.class);
        Assert.assertTrue(handle instanceof Float);
        Assert.assertEquals(handle, 1.0f);

        handle = NumberHandlerSupport.handle("1.01", Double.class);
        Assert.assertTrue(handle instanceof Double);
        Assert.assertEquals(handle, new Double("1.01"));
    }

}