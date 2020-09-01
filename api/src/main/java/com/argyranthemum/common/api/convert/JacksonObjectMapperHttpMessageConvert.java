package com.argyranthemum.common.api.convert;

import com.argyranthemum.common.api.base.RawEntity;
import com.argyranthemum.common.api.base.Response;
import com.argyranthemum.common.core.serializer.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.argyranthemum.common.api.base.Response.MSG_SUCCESS;

public class JacksonObjectMapperHttpMessageConvert extends AbstractHttpMessageConverter<Object> {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private static final Logger logger = LoggerFactory.getLogger(JacksonObjectMapperHttpMessageConvert.class);

    public JacksonObjectMapperHttpMessageConvert() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    @Override
    public boolean canRead(@Nullable Class<?> clazz, MediaType mediaType) {
        JavaType javaType = getJavaType(clazz);
        return JacksonUtil.getInstance().canDeserialize(javaType) && canRead(mediaType);
    }

    @Override
    public boolean canWrite(@Nullable Class<?> clazz, MediaType mediaType) {
        return true;
    }

    @Override
    protected boolean supports(@Nullable Class<?> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object readInternal(@Nullable Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        JavaType javaType = getJavaType(clazz);
        try {
            return JacksonUtil.getInstance().readValue(inputMessage.getBody(), javaType);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void writeInternal(@Nullable Object object, @Nullable HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            logger.debug("object. {}", object);

            if (object instanceof RawEntity) {
                assert outputMessage != null;
                outputMessage.getBody().write(((RawEntity) object).getResult().getBytes(StandardCharsets.UTF_8));
                return;
            }

            assert outputMessage != null;
            JacksonUtil.getInstance().writeValue(outputMessage.getBody(), this.handleResponse(object));
        } catch (JsonProcessingException ex) {
            logger.error("Could not write JSON: " + ex.getMessage(), ex);
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    private JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

    private Response handleResponse(Object object) {
        logger.debug("handle object with json  object : " + object);
        Response response = new Response();
        response.setMsg(MSG_SUCCESS);
        response.setData(object);
        return response;
    }

}
