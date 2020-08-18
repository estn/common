/**
 * Copyright  2020  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.redis.service;

import com.argyranthemum.common.redis.RedisAutoConfigureTest;
import com.argyranthemum.common.redis.supplier.RedisSingleSupplier;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: Test RedisUtilTest
 * @Author: estn.zuo
 * @CreateTime: 2020-08-18 14:04
 */
public class RedisServiceTest extends RedisAutoConfigureTest {

    @Resource
    private RedisService redisService;

    @Test
    public void expire() {
    }

    @Test
    public void getExpire() {
    }

    @Test
    public void hasKey() {
    }

    @Test
    public void del() {
    }

    @Test
    public void get() {
        List<Object> a = redisService.get("b", new TypeReference<List<Object>>() {
        });
        System.out.println(a);

        redisService.get("c", List.class, new RedisSingleSupplier<>(10, TimeUnit.MINUTES, () -> Lists.newArrayList("a", "B")));
    }

    @Test
    public void testGet() {
    }

    @Test
    public void set() {
        redisService.set("b", 1);
        Integer b = redisService.get("b", new TypeReference<Integer>() {
        });
        System.out.println(b);
    }

    @Test
    public void testSet() {
    }

    @Test
    public void increment() {
    }

    @Test
    public void decrement() {
    }

    @Test
    public void hget() {


    }

    @Test
    public void hmget() {
        redisService.hset("a", "1", 1);
        redisService.hset("a", "2", "b");


        String value = redisService.hget("a", "1", new TypeReference<String>() {
        });

        System.out.println(value);
    }
}