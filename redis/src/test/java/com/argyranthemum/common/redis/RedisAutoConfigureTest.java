/**
 * Copyright  2020  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.redis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: Test RedisAutoConfigureTest
 * @Author: estn.zuo
 * @CreateTime: 2020-08-18 14:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "classpath:application.yml")
@ContextConfiguration(classes = {RedisAutoConfigure.class})
@SpringBootConfiguration
public class RedisAutoConfigureTest {

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

    @Test
    public void configRedisLock() {
    }

    @Test
    public void redisTemplate() {
    }

    @Test
    public void redisUtil() {
    }
}