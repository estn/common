package com.argyranthemum.common.core.util.encrypt;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by estn on 16/11/12.
 */
public class BCryptUtilTest {
    @Test
    public void encrypt() throws Exception {

        String source = "abced123";

        String encrypt = BCryptUtil.encrypt(source);
        Assert.assertNotNull(encrypt);
        Assert.assertTrue(encrypt.length() > 0);
    }

    @Test
    public void verify() throws Exception {

        String source = "abced123";

        String encrypt = BCryptUtil.encrypt(source);
        Assert.assertNotNull(encrypt);
        Assert.assertTrue(encrypt.length() > 0);

        Boolean verify = BCryptUtil.verify(source, encrypt);
        Assert.assertTrue(verify);


        verify = BCryptUtil.verify(source+" ", encrypt);
        Assert.assertFalse(verify);

    }

}