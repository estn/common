/**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.support;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @Description: Test SignSupportTest
 * @Author: estn.zuo
 * @CreateTime: 2016-12-09 16:19
 */
public class SignSupportTest {

    @Test
    public void createLinkString() throws Exception {

        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "estn");
        map.put("appid", "123456");
        map.put("sign", "");
        map.put("token", "");

        String result = SignSupport.createLinkString(map);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.contains("token"));
        Assert.assertFalse(result.contains("sign"));
    }

    @Test
    public void createLinkStringNoIgnore() throws Exception {

        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "estn");
        map.put("appid", "123456");
        map.put("sign", "");
        map.put("token", "sdf");
        map.put("key", "");

        String result = SignSupport.createLinkString(map, new String[]{});
        Assert.assertNotNull(result);
        Assert.assertFalse(result.contains("sign"));
        Assert.assertTrue(result.contains("token"));
        Assert.assertFalse(result.contains("key"));
    }

    @Test
    public void testConvert() {
        Map<String, String[]> map = Maps.newHashMap();
        map.put("name", new String[]{"estn"});
        map.put("appid", new String[]{"a", "b"});

        Map<String, Object> convert = SignSupport.convert(map);
        Assert.assertNotNull(convert);
        Assert.assertEquals(convert.size(), map.size());
    }
}