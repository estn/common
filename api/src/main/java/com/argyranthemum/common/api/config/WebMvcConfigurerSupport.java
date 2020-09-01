package com.argyranthemum.common.api.config;

import com.argyranthemum.common.api.convert.JacksonObjectMapperHttpMessageConvert;
import com.argyranthemum.common.api.exception.ServerExceptionResolver;
import com.argyranthemum.common.api.formatter.BooleanToEnumFormatter;
import com.argyranthemum.common.api.formatter.StringToDateFormatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class WebMvcConfigurerSupport implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * 配置消息转换器
     * <p>
     * 统一使用json消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(new JacksonObjectMapperHttpMessageConvert());
    }

    /**
     * 配置异常处理器
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        ServerExceptionResolver resolver = new ServerExceptionResolver();
        exceptionResolvers.add(resolver);
    }

    /**
     * 配置Date格式的参数转换器
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new StringToDateFormatter());
        registry.addFormatter(new BooleanToEnumFormatter());
    }

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
