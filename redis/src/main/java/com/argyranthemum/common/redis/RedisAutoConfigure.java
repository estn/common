package com.argyranthemum.common.redis;

import com.argyranthemum.common.redis.lock.RedisLock;
import com.argyranthemum.common.redis.service.RedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisAutoConfigure {

    @Resource
    private RedisConnectionFactory factory;

    @Resource
    private RedisOperations<String, String> redisOps;

    @Bean
    @ConditionalOnMissingBean
    public RedisLock configRedisLock() {
        return new RedisLock(redisOps);
    }

    @Bean
    public RedisService redisService() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setValueSerializer(stringRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();

        return new RedisService(template);
    }

}