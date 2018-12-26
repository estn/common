package com.argyranthemum.common.core.serializer;

import com.argyranthemum.common.core.constant.ConfigurationConst;
import com.argyranthemum.common.core.pojo.BodyType;
import com.argyranthemum.common.core.pojo.BodyVO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class BodySerializer extends JsonSerializer<BodyVO> {

    @Override
    public void serialize(BodyVO body, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        if (StringUtils.isNotBlank(body.getValue())) {

            String bodyValue = body.getValue();

            //类型为IMG并且不是绝对路径,则进行URL地址处理
            if (BodyType.IMAGE == body.getType()
                    && !bodyValue.startsWith("http")) {
                //当body的value以SystemConst.WEB_START开头时，表示为插入数据
                String url = StringUtils.startsWith(bodyValue, "/") ? bodyValue : "/" + bodyValue;
                body.setValue(ConfigurationConst.RESOURCE_ROOT_URL + url.trim());
            }
        } else {
            body.setValue(null);
        }

        if (body.getType() != null) {
            jgen.writeStringField("type", body.getType().name());
        }

        if (StringUtils.isNotBlank(body.getValue())) {
            jgen.writeStringField("value", body.getValue());
        }

        if (StringUtils.isNotBlank(body.getTitle())) {
            jgen.writeStringField("title", body.getTitle());
        }

        jgen.writeEndObject();
    }
}
