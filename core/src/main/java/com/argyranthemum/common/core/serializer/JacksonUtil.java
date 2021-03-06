package com.argyranthemum.common.core.serializer;

import com.argyranthemum.common.core.enums.BooleanEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JacksonUtil extends ObjectMapper {

    private static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    private static JacksonUtil instance = new JacksonUtil();

    private JacksonUtil() {

        configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        // 不予输出null字段
        setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 当数组中只有一个元素时，也按照数组输出
        configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        // 忽略不认识的字段
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 时间格式化输出
//        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        //针对特殊的字段进行设置序列化
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BooleanEnum.class, new BooleanEnumSerializer());
        this.registerModule(simpleModule);
    }

    public static JacksonUtil getInstance() {
        return instance;
    }

    public static String write(Object object) {
        try {
            return getInstance().writeValueAsString(object);
        } catch (IOException e) {
            throw new JacksonSerializerException("write object error" + object, e);
        }
    }

    public static <T> T read(String content, TypeReference<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (IOException e) {
            throw new JacksonSerializerException("read object error. content" + content + ". to:" + valueType, e);
        }
    }

    public static <T> T read(String content, Class<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (IOException e) {
            throw new JacksonSerializerException("read object error. content" + content + ". to:" + valueType, e);
        }
    }

}
