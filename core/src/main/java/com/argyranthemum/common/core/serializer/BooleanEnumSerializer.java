package com.argyranthemum.common.core.serializer;

import com.argyranthemum.common.core.enums.BooleanEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BooleanEnumSerializer extends JsonSerializer<BooleanEnum> {

    @Override
    public void serialize(BooleanEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value.equals(BooleanEnum.TRUE)) {
            jgen.writeBoolean(true);
        } else {
            jgen.writeBoolean(false);
        }
    }
}
