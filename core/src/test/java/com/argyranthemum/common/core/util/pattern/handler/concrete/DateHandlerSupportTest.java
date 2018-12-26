/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler.concrete;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Description: Test DateHandlerSupportTest
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 22:10
 */
public class DateHandlerSupportTest {
    @Test
    public void handle() throws Exception {

        Date date = DateHandlerSupport.handle("2017-10-10 09:30:10");
        Assert.assertNotNull(date);
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        Assert.assertEquals(ldt.getYear(), 2017);
        Assert.assertEquals(ldt.getMonth().getValue(), 10);
        Assert.assertEquals(ldt.getDayOfMonth(), 10);
        Assert.assertEquals(ldt.getHour(), 9);
        Assert.assertEquals(ldt.getMinute(), 30);
        Assert.assertEquals(ldt.getSecond(), 10);


        date = DateHandlerSupport.handle("2017-10-10T09:30:10");
        Assert.assertNotNull(date);
        ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        Assert.assertEquals(ldt.getYear(), 2017);
        Assert.assertEquals(ldt.getMonth().getValue(), 10);
        Assert.assertEquals(ldt.getDayOfMonth(), 10);
        Assert.assertEquals(ldt.getHour(), 9);
        Assert.assertEquals(ldt.getMinute(), 30);
        Assert.assertEquals(ldt.getSecond(), 10);

        date = DateHandlerSupport.handle("2017-10-10");
        Assert.assertNotNull(date);
        ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        Assert.assertEquals(ldt.getYear(), 2017);
        Assert.assertEquals(ldt.getMonth().getValue(), 10);
        Assert.assertEquals(ldt.getDayOfMonth(), 10);


        date = DateHandlerSupport.handle("2017-10-10");
        Assert.assertNotNull(date);
        ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        Assert.assertEquals(ldt.getYear(), 2017);
        Assert.assertEquals(ldt.getMonth().getValue(), 10);
        Assert.assertEquals(ldt.getDayOfMonth(), 10);


        date = DateHandlerSupport.handle("1507599010000");
        Assert.assertNotNull(date);
        ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        Assert.assertEquals(ldt.getYear(), 2017);
        Assert.assertEquals(ldt.getMonth().getValue(), 10);
        Assert.assertEquals(ldt.getDayOfMonth(), 10);
        Assert.assertEquals(ldt.getHour(), 9);
        Assert.assertEquals(ldt.getMinute(), 30);
        Assert.assertEquals(ldt.getSecond(), 10);


    }

}