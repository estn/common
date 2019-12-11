package com.argyranthemum.common.core.serializer;

import com.argyranthemum.common.core.constant.SystemConst;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSecondSerializer extends JsonSerializer<Number> {

    @Override
    public void serialize(Number value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat(SystemConst.TIME_FORMAT);
        String date = sdf.format(new Date(value.longValue() * 1000));
        jgen.writeString(date);
    }
}
