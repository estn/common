package com.argyranthemum.common.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis Lock 实现
 */
public class RedisLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedisOperations<String, String> redisOps;

    public RedisLock(RedisOperations<String, String> redisOps) {
        this.redisOps = redisOps;
    }

    /**
     * 加锁
     *
     * @param key        锁KEY
     * @param requestId  锁内容
     * @param expiration 过期时间. 单位:秒
     * @return
     */
    public boolean lock(String key, String requestId, int expiration) {
        logger.debug("redis lock. key:{}, requestId:{},  expiration:{}", key, requestId, expiration);
        Assert.isTrue(expiration > 0, "expiration is must great than zero!");

        Boolean result = redisOps.opsForValue().setIfAbsent(key, requestId, expiration, TimeUnit.SECONDS);
        logger.debug("lock result:{}. for key:{}, requestId:{},  expiration:{}", result, key, requestId, expiration);
        if (result == null) {
            return false;
        }
        return result;
    }

    /**
     * 解锁
     *
     * @param key       锁KEY
     * @param requestId 锁内容
     */
    public boolean unlock(String key, String requestId) {
        logger.debug("redis unlock. key:{}, requestId:{}", key, requestId);

        String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(lua, Long.TYPE);
        List<String> keys = new ArrayList<>();
        keys.add(key);
        Long result = redisOps.execute(script,keys, requestId);

        logger.debug("lock result:{}. for key:{}, requestId:{}, ", result, key, requestId);
        return Long.valueOf(1L).equals(result);
    }


}
