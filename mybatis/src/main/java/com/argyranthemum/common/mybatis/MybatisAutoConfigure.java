package com.argyranthemum.common.mybatis;

import com.argyranthemum.common.mybatis.handler.MyMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisAutoConfigure {

    @Bean
    public MyMetaObjectHandler get() {
        return new MyMetaObjectHandler();
    }

}