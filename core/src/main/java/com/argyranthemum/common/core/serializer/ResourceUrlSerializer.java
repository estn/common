package com.argyranthemum.common.core.serializer;

import com.argyranthemum.common.core.constant.ConfigurationConst;
import com.argyranthemum.common.core.constant.SystemConst;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceUrlSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Matcher matcher = Pattern.compile(SystemConst.Pattern.URL_REGULAR_EXPRESSION).matcher(value);
        if (matcher.matches()) {
            jgen.writeString(value);
        } else {
            value = StringUtils.startsWith(value, "/") ? value : "/" + value;
            jgen.writeString(ConfigurationConst.RESOURCE_ROOT_URL + value);
        }
    }
}
