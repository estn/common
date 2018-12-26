/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.exception;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @Description: Test BaseExceptionTest
 * @Author: estn.zuo
 * @CreateTime: 2017-08-08 12:04
 */
public class BaseExceptionTest {

    @Test
    public void testFormat() {
        BaseException exception = new BaseException(DefaultError.HTTP_STATUS_404, "/user");
        System.out.println(exception.getMessage());
        Assert.assertEquals(exception.getMessage(), "404异常 PATH:/user");
    }

    public static void main(String[] args) throws IOException {

        Integer result = null;
        try {
            result = say("/1.txt");
            if (result == null) {
                say("2");
            }
        } catch (IOException e) {
            e.printStackTrace();


        }


    }

    public static int say(String s) throws IOException {
        throw new IOException();
    }
}