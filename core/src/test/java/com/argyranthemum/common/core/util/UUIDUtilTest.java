package com.argyranthemum.common.core.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by estn on 16/11/12.
 */
public class UUIDUtilTest {

    @Test
    public void randomUUID() throws Exception {
        String uuid = UUIDUtil.randomUUID();
        Assert.assertNotNull(uuid);
        Assert.assertEquals(uuid.length(), 36);

    }

    @Test
    public void randomWithoutBar() throws Exception {
        String uuid = UUIDUtil.randomWithoutBar();
        Assert.assertNotNull(uuid);
        Assert.assertEquals(uuid.length(), 32);
        Assert.assertFalse(uuid.contains("-"));

    }

    @Test
    public void randomChar() throws Exception {
        String randomChar = UUIDUtil.randomChar(8);
        Assert.assertNotNull(randomChar);
        Assert.assertEquals(randomChar.length(), 8);
        Assert.assertFalse(randomChar.contains("-"));


    }


    @Test(expected = IllegalArgumentException.class)
    public void randomCharThrowError() throws Exception {

        String randomChar = UUIDUtil.randomChar(0);
        Assert.assertNotNull(randomChar);
        Assert.assertEquals(randomChar.length(), 0);
        Assert.assertFalse(randomChar.contains("-"));
    }

    @Test
    public void randomString() throws Exception {
        String s = UUIDUtil.randomString(8);
        Assert.assertNotNull(s);
        Assert.assertEquals(s.length(), 8);
        Assert.assertFalse(s.contains("-"));

    }

    @Test
    public void randomNumber() throws Exception {
        String randomNumber = UUIDUtil.randomNumber(8);
        Assert.assertNotNull(randomNumber);
        Assert.assertEquals(randomNumber.length(), 8);
        for (int i = 0; i < randomNumber.length(); i++) {
            char c = randomNumber.charAt(i);
            Assert.assertTrue((int) c >= 48 && (int) c <= 57);
        }
    }

    @Test
    public void random() throws Exception {
        String random = UUIDUtil.random(new char[]{'a', 'b', 'c'}, 8);
        Assert.assertNotNull(random);
        Assert.assertEquals(random.length(), 8);
        for (int i = 0; i < random.length(); i++) {
            int _c = random.charAt(i);
            Assert.assertTrue(_c == 'a' || _c == 'b' || _c == 'c');
        }
    }

}