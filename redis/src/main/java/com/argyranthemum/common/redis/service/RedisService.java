package com.argyranthemum.common.redis.service;

import com.argyranthemum.common.core.serializer.JacksonUtil;
import com.argyranthemum.common.redis.supplier.RedisSupplier;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    // =============================common============================

    public void expire(String key, long time, TimeUnit unit) {
        if (time <= 0) {
            throw new IllegalArgumentException("timeout must be gather than zero");
        }
        redisTemplate.expire(key, time, unit);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Lists.newArrayList(key));
            }
        }
    }

    // ============================String=============================

    public <T> T get(String key, Class<T> clazz) {
        return doGet(key, clazz);
    }

    public <T> T get(String key, TypeReference<T> clazz) {
        return doGet(key, clazz);
    }

    private <T> T doGet(String key, Object clazz) {
        check(key);
        String value = (String) redisTemplate.opsForValue().get(key);
        return doConvert(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz, RedisSupplier<T> supplier) {
        return doGet(key, clazz, supplier);
    }

    public <T> T get(String key, TypeReference<T> clazz, RedisSupplier<T> supplier) {
        return doGet(key, clazz, supplier);
    }

    private <T> T doGet(String key, Object clazz, RedisSupplier<T> supplier) {
        check(key, supplier.getExpiration());
        String value = (String) redisTemplate.opsForValue().get(key);
        if (Objects.isNull(value)) {
            Object obj = supplier.get();
            value = serialize(obj);
            this.set(key, obj, supplier.getExpiration(), supplier.getTimeUnit());
        }
        return doConvert(value, clazz);
    }


    public void set(String key, Object value) {
        check(key);
        value = serialize(value);
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        check(key, timeout);
        value = serialize(value);
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    //=====================

    public Long incr(String key, long delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("delta must gather than zero");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }


    public Long decr(String key, long delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("delta must gather than zero");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    // ================================Map=================================

    public <T> T hget(String key, String hashKey, Class<T> clazz) {
        return doHget(key, hashKey, clazz);
    }

    public <T> T hget(String key, String hashKey, TypeReference<T> clazz) {
        return doHget(key, hashKey, clazz);
    }

    private <T> T doHget(String key, String hashKey, Object clazz) {
        String value = (String) redisTemplate.opsForHash().get(key, hashKey);
        return doConvert(value, clazz);
    }

    public <T> Map<String, T> hmget(String key, List<String> hashKeys, Class<T> clazz) {
        return doHmget(key, hashKeys, clazz);
    }

    public <T> Map<String, T> hmget(String key, List<String> hashKeys, TypeReference<T> clazz) {
        return doHmget(key, hashKeys, clazz);
    }

    private <T> Map<String, T> doHmget(String key, List<String> hashKeys, Object clazz) {
        Map<String, T> result = Maps.newHashMap();
        List<Object> objects = redisTemplate.opsForHash().multiGet(key, Lists.newArrayList(hashKeys));
        int i = 0;
        for (String _hashKey : hashKeys) {
            result.put(_hashKey, doConvert(objects.get(i), clazz));
            i++;
        }
        return result;
    }


    public <T> Map<String, T> hgetAll(String key, Class<T> clazz) {
        return doHgetAll(key, clazz);
    }

    public <T> Map<String, T> hgetAll(String key, TypeReference<T> clazz) {
        return doHgetAll(key, clazz);
    }

    private <T> Map<String, T> doHgetAll(String key, Object clazz) {
        Map<String, T> result = Maps.newHashMap();
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            String entryKey = entry.getKey().toString();
            String value = entry.getValue().toString();
            result.put(entryKey, doConvert(value, clazz));
        }
        return result;
    }

    public void hset(String key, String item, Object value) {
        value = serialize(value);
        redisTemplate.opsForHash().put(key, item, value);
    }

    public void hset(String key, String item, Object value, long time, TimeUnit unit) {
        value = serialize(value);
        redisTemplate.opsForHash().put(key, item, value);
        expire(key, time, unit);
    }

    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    public double hincr(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    public double hdecr(String key, String item, long delta) {
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

    // ============================set=============================

    /**
     * TODO SET
     */

    // ===============================list=================================
    public <T> List<T> lget(String key, long start, long end, Class<T> clazz) {
        return doLget(key, start, end, clazz);
    }

    public <T> List<T> lget(String key, long start, long end, TypeReference<T> clazz) {
        return doLget(key, start, end, clazz);
    }

    private <T> List<T> doLget(String key, long start, long end, Object clazz) {
        List<Object> objects = redisTemplate.opsForList().range(key, start, end);
        if (objects == null) {
            return new ArrayList<>();
        }
        List<T> result = Lists.newArrayList();
        for (Object object : objects) {
            result.add(doConvert(object, clazz));
        }
        return result;
    }

    public Long lsize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public <T> T lget(String key, long index, Class<T> clazz) {
        return doLget(key, index, clazz);
    }

    public <T> T lget(String key, long index, TypeReference<T> clazz) {
        return doLget(key, index, clazz);
    }

    private <T> T doLget(String key, long index, Object clazz) {
        Object value = redisTemplate.opsForList().index(key, index);
        return doConvert(value, clazz);
    }

    public void lset(String key, Object value) {
        value = serialize(value);
        redisTemplate.opsForList().rightPush(key, value);
    }

    public void lset(String key, Object value, long time, TimeUnit unit) {
        value = serialize(value);
        redisTemplate.opsForList().rightPush(key, value);
        expire(key, time, unit);
    }


    private <T> T doConvert(Object value, Object clazz) {
        if (value == null) {
            return null;
        }
        String content = value.toString();
        if (StringUtils.isBlank(content)) {
            return null;
        }
        if (clazz instanceof TypeReference) {
            return JacksonUtil.read(content, (TypeReference<T>) clazz);
        } else if (clazz instanceof Class) {
            return JacksonUtil.read(content, (Class<T>) clazz);
        }
        throw new RuntimeException("convert type error. clazz:" + clazz);
    }

    public String serialize(Object object) {
        return JacksonUtil.write(object);
    }

    private void check(String key) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key is null");
        }
    }

    private void check(String key, long timeout) {
        check(key);
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout must be gather than zero");
        }
    }
}
