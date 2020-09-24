package com.argyranthemum.common.mybatis.handler;

import com.argyranthemum.common.core.serializer.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbsJsonTypeHandler<T> implements TypeHandler<Object> {

    public abstract TypeReference<T> getValueType();

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setString(i, JacksonUtil.write(parameter));
        }
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getString(columnName));
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getString(columnIndex));
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getString(columnIndex));
    }

    private Object convert(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return JacksonUtil.read(value, getValueType());
    }


}
