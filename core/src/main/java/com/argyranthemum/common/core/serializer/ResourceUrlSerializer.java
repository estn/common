package com.argyranthemum.common.core.serializer;

import com.argyranthemum.common.core.constant.ConfigurationConst;
import com.argyranthemum.common.core.constant.SystemConst;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceUrlSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String values, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        List<String> urls = Lists.newArrayList();
        String[] strings = values.split(",");
        for (String value : strings) {
            Matcher matcher = Pattern.compile(SystemConst.Pattern.URL_REGULAR_EXPRESSION).matcher(value);
            if (matcher.matches()) {
                urls.add(value);
            } else {
                value = StringUtils.startsWith(value, "/") ? value : "/" + value;
                urls.add(ConfigurationConst.RESOURCE_ROOT_URL + value);
            }
        }
        jgen.writeString(Joiner.on(",").join(urls));
    }
}
