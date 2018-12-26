package com.argyranthemum.common.core.pojo;

import com.argyranthemum.common.core.serializer.BodySerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = BodySerializer.class)
public class BodyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private BodyType type;

    private String value;

    private String title;

    public BodyType getType() {
        return type;
    }

    public void setType(BodyType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}