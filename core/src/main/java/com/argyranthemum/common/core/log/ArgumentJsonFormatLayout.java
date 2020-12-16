package com.argyranthemum.common.core.log;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.argyranthemum.common.core.serializer.JacksonUtil;
import org.slf4j.helpers.MessageFormatter;

import java.util.stream.Stream;

public class ArgumentJsonFormatLayout extends MessageConverter {

    @Override
    public String convert(ILoggingEvent event) {
        try {
            return MessageFormatter.arrayFormat(event.getMessage(), Stream.of(event.getArgumentArray())
                    .map(JacksonUtil::write).toArray()).getMessage();
        } catch (Exception e) {
            return event.getMessage();
        }
    }
}