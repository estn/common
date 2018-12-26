package com.argyranthemum.common.redis;

import com.argyranthemum.common.redis.lock.RedisLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

import javax.annotation.Resource;

@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisAutoConfigure {

    @Resource
    private RedisOperations<String, String> redisOps;

    @Bean
    @ConditionalOnMissingBean
    RedisLock configRedisLock() {
        return new RedisLock(redisOps);
    }

}