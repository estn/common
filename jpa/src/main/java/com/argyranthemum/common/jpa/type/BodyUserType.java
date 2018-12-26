package com.argyranthemum.common.jpa.type;

import com.argyranthemum.common.core.pojo.Body;
import com.argyranthemum.common.core.serializer.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: List\<Body\> 持久化转换
 * @Author: estn.zuo
 * @CreateTime: 2014-10-13 15:48
 */
public class BodyUserType implements UserType {

    private static Logger logger = LoggerFactory.getLogger(BodyUserType.class);

    private static final int[] SQL_TYPES = {Types.VARCHAR};

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class<List> returnedClass() {
        return List.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;
        if (x != null && y != null) {
            List xList = (List) x;
            List yList = (List) y;

            if (xList.size() != yList.size())
                return false;

            for (int i = 0; i < xList.size(); i++) {
                Body strx = (Body) xList.get(i);
                Body stry = (Body) yList.get(i);
                if (!(strx.equals(stry)))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        try {
            String bodyString = rs.getString(names[0]);
            if (StringUtils.isBlank(bodyString)) {
                return null;
            }
            return JacksonUtil.getInstance().readValue(bodyString, new TypeReference<List<Body>>() {
            });
        } catch (IOException e) {
            logger.error("========>>>" + e);
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        try {
            if (value == null) {
                st.setNull(index, Types.VARCHAR);
            } else {
                String bodyString = JacksonUtil.getInstance().writeValueAsString(value);
                st.setString(index, bodyString);
            }
        } catch (Exception e) {
            logger.error("========>>>" + e);
        }
    }


    @Override
    public Object deepCopy(Object value) throws HibernateException {
        List sourcelist = (List) value;
        List targetlist = new ArrayList();
        if (sourcelist != null) {
            targetlist.addAll(sourcelist);
        }
        return targetlist;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }
} 


