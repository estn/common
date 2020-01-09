package com.argyranthemum.common.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TimeSecondSerializer extends JsonSerializer<Number> {

    @Override
    public void serialize(Number value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeNumber(value.longValue() * 1000);
    }
}
