package com.argyranthemum.common.core.util.encrypt;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by estn on 16/11/12.
 */
public class AESUtilTest {
    @Test
    public void decrypt() throws Exception {
        String source = "abcd123456!@#";
        String key = AESUtil.generateKey();
        String encrypt = AESUtil.encrypt(source, key);
        Assert.assertNotNull(encrypt);

        String decrypt = AESUtil.decrypt(encrypt, key);
        Assert.assertNotNull(decrypt);

        Assert.assertEquals(decrypt, source);
    }

    @Test
    public void decrypt1() throws Exception {

    }

    @Test
    public void encrypt() throws Exception {
        String source = "abcd123456!@#";
        String key = AESUtil.generateKey();
        String encrypt = AESUtil.encrypt(source, key);
        Assert.assertNotNull(encrypt);
        Assert.assertTrue(encrypt.length() > 0);
    }

    @Test
    public void encrypt1() throws Exception {

    }

    @Test
    public void generateKey() throws Exception {
        String key = AESUtil.generateKey();
        Assert.assertNotNull(key);
        Assert.assertTrue(key.length() == 16);
    }


}