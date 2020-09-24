package com.argyranthemum.common.mybatis.handler;

import com.argyranthemum.common.core.serializer.TypeRef;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(value = {List.class})
public class ListTypeHandler extends AbsJsonTypeHandler<List<Object>> {

    @Override
    public TypeReference<List<Object>> getValueType() {
        return new TypeRef<List<Object>>().T;
    }
}
