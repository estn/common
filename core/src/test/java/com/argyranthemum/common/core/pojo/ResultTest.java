package com.argyranthemum.common.core.pojo;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by estn on 16/11/13.
 */
public class ResultTest {

    @Test
    public void testConstructor1() {

        Result success = new Result(200, "success");
        Assert.assertTrue(success.getSucceed());

        success = new Result(201, "success");
        Assert.assertTrue(success.getSucceed());

        success = new Result(400, "error");
        Assert.assertFalse(success.getSucceed());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor2() {
        Result success = new Result(0, "success");
        Assert.assertTrue(success.getSucceed());
    }

}