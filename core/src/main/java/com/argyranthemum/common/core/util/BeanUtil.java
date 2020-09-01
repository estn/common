/**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util;

import com.argyranthemum.common.core.exception.BaseException;
import com.argyranthemum.common.core.util.pattern.handler.concrete.DateHandlerSupport;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description: BeanUtil
 * @author: estn.zuo
 * @createTime: 2016-06-12 21:43
 */
public class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private static final String SOURCE_NULL = "merge. source is null";
    private static final String TARGET_NULL = "merge. target is null";

    private BeanUtil() {
    }

    /**
     * 合并List
     *
     * @param source 源List
     * @param clazz  目标List泛型类型
     * @return 合并后的结果
     */
    public static <T> List<T> mergeList(List source, Class<T> clazz) {
        return mergeList(source, clazz, null);
    }


    /**
     * 合并List
     *
     * @param source           源List
     * @param clazz            目标List泛型类型
     * @param ignoreProperties 忽略字段集合
     * @return 合并后的结果
     */
    public static <T> List<T> mergeList(List source, Class<T> clazz, String[] ignoreProperties) {
        try {
            return copyProperties4List(source, clazz, ignoreProperties);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 合并两个实体，将source字段设置到target对象
     * <p/>
     * 只有当source里面字段值不为null时，才会将该字段的值更新到target
     * <p/>
     * 两个实体必须属于同一类型
     *
     * @param source 源实体    (一般指前台传递的对象)
     * @param target 目标实体   (数据库查询对象)
     */
    public static <T> T merge(Object source, T target) {
        try {
            Preconditions.checkNotNull(source, SOURCE_NULL);
            Preconditions.checkNotNull(target, TARGET_NULL);
            return copyProperties(source, target, null);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }


    /**
     * 合并两个实体，将source字段设置到target对象
     * <p/>
     * 只有当source里面字段值不为null时，才会将该字段的值更新到target
     * <p/>
     * 两个实体必须属于同一类型
     *
     * @param source 源实体    (一般指前台传递的对象)
     * @param clazz  目标实体类型 (一般指POJO对象)
     */
    public static <T> T merge(Object source, Class<T> clazz) {
        Preconditions.checkNotNull(source, SOURCE_NULL);
        return merge(source, clazz, null);
    }


    /**
     * @param source           源实体    (一般指前台传递的对象)
     * @param target           目标实体   (数据库查询对象)
     * @param ignoreProperties 忽略字段集合
     * @return
     */
    public static <T> T merge(Object source, T target, String[] ignoreProperties) {
        try {
            Preconditions.checkNotNull(source, SOURCE_NULL);
            Preconditions.checkNotNull(target, TARGET_NULL);
            return copyProperties(source, target, ignoreProperties);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }


    /**
     * @param source           源实体          (一般指前台传递的对象)
     * @param clazz            目标实体类型     (一般指POJO对象)
     * @param ignoreProperties 忽略字段集合
     */
    public static <T> T merge(Object source, Class<T> clazz, String[] ignoreProperties) {
        try {
            Preconditions.checkNotNull(source, SOURCE_NULL);
            T target = clazz.newInstance();
            return copyProperties(source, target, ignoreProperties);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    private static <T> T copyProperties(Object source, T target, String[] ignoreProperties) throws Exception {
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
        copyObject(targetPds, ignoreList, source, target);
        return target;
    }

    private static <T> List<T> copyProperties4List(Object source, Class<T> clazz, String[] ignoreProperties) throws Exception {
        Preconditions.checkNotNull(source, "Source must not be null");
        Preconditions.checkNotNull(clazz, "Target must not be null");
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
        return copyList((List) source, clazz, ignoreList);
    }


    private static <T> List<T> copyList(List source, Class<T> clazz, List<String> ignoreList) throws Exception {
        List<T> targetList = Lists.newArrayList();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(clazz);
        for (Object obj : source) {
            T target = clazz.newInstance();
            copyObject(targetPds, ignoreList, obj, target);
            targetList.add(target);
        }
        return targetList;
    }

    private static void copyObject(PropertyDescriptor[] targetPds, List<String> ignoreList, Object obj, Object _target) throws BaseException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(obj.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    Method writeMethod = null;
                    Object value = null;
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        value = readMethod.invoke(obj);
                        if (value == null) {
                            continue;
                        }
                        writeMethod = targetPd.getWriteMethod();
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        value = shouldConvertParameterType(writeMethod, value);
                        writeMethod.invoke(_target, value);
                    } catch (Throwable ex) {
                        throw new BaseException("Could not copy properties. invoke  method [{}], value [{}]({})", writeMethod, value, value.getClass());
                    }
                }
            }
        }
    }

    /**
     * Bean对象转换成Map对象
     */
    public static Map<String, Object> convert$Map(Object bean) {
        Preconditions.checkNotNull(bean);
        Map<String, Object> map = Maps.newHashMap();
        try {
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(bean.getClass());
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(bean);
                    if (value == null) {
                        continue;
                    }

                    if (value instanceof String) {
                        String valueStr = (String) value;
                        if (valueStr.length() == 0) {
                            continue;
                        }
                    }
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return map;
    }

    /**
     * Map对象转换成Bean对象
     */
    public static <T> T convert$Bean(Class<T> clazz, Map<String, Object> map) {

        // 创建对象
        T obj;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            throw new BaseException(e);
        }
        // 处理map的key
        Map<String, Object> newMap = Maps.newHashMap();
        for (Map.Entry<String, Object> en : map.entrySet()) {
            newMap.put("set" + en.getKey().trim().replaceAll("_", "").toLowerCase(), en.getValue());
        }
        // 进行值装入
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
        for (Method method : methods) {
            String methodName = method.getName().toLowerCase();
            if (method.getName().toLowerCase().startsWith("set")) {
                Object value = newMap.get(methodName);
                if (value != null) {
                    try {
                        value = shouldConvertParameterType(method, value);
                        method.invoke(obj, value);
                    } catch (Exception e) {
                        throw new BaseException("Could not copy properties. invoke  method [{}], value [{}]({})", methodName, value, value.getClass());
                    }
                }
            }
        }
        return obj;
    }

    /**
     * 设置属性值
     */
    public static void setProperty(final Object bean, final String name, final Object value) {
        Preconditions.checkNotNull(bean, "bean is null");
        Preconditions.checkNotNull(name, "method name is null");
        Preconditions.checkNotNull(value, "value is null");
        Field field = ReflectionUtils.findField(bean.getClass(), name);
        assert field != null;
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, bean, value);
    }

    private static Object shouldConvertParameterType(Method method, Object value) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return value;
        }

        Class<?> parameterType = parameterTypes[0];
        if (parameterType.equals(value.getClass())) {
            return value;
        }

        if (String.class.equals(parameterType)) {
            return value.toString();
        }

        //解析基本数据类型
        Object basicValue = parseBasic(value, parameterType);
        if (basicValue != null) {
            return basicValue;
        }

        /*日期格式*/
        if (Date.class.equals(parameterType)) {
            value = DateHandlerSupport.handle(value.toString());
        }

        /*枚举格式*/
        else if (parameterType.isEnum()) {
            value = parseEnum(parameterType, value);
        }

        /*BigDecimal*/
        else if (parameterType.getSuperclass() != null && parameterType.getSuperclass().equals(BigDecimal.class)) {
            value = new BigDecimal(value.toString());
        }
        return value;
    }

    private static Object parseBasic(Object value, Class<?> parameterType) {
        BasicType basicType;
        try {
            basicType = BasicType.valueOf(parameterType.getSimpleName().toUpperCase());
        } catch (Exception e) {
            // 非基本类型数据
            return null;
        }

        switch (basicType) {
            case BYTE:
                if (parameterType == byte.class) {
                    return Byte.parseByte(value.toString());
                }
                return Byte.valueOf(value.toString());
            case SHORT:
                if (parameterType == short.class) {
                    return Short.parseShort(value.toString());
                }
                return Short.valueOf(value.toString());
            case INT:
                return Integer.parseInt(value.toString());
            case INTEGER:
                return Integer.valueOf(value.toString());
            case LONG:
                if (parameterType == long.class) {
                    return new BigDecimal(value.toString()).longValue();
                }
                return Long.valueOf(value.toString());
            case DOUBLE:
                if (parameterType == double.class) {
                    return new BigDecimal(value.toString()).doubleValue();
                }
            case FLOAT:
                if (parameterType == float.class) {
                    return Float.parseFloat(value.toString());
                }
                return Float.valueOf(value.toString());
            case BOOLEAN:
                if (parameterType == boolean.class) {
                    return Boolean.parseBoolean(value.toString());
                }
                return Boolean.valueOf(value.toString());
            case CHAR:
            case CHARACTER:
                return value.toString().charAt(0);
            default:
                return null;
        }
    }

    private static Object parseEnum(Class parameterType, Object v) {
        if (v instanceof Integer) {
            EnumSet enumSet = EnumSet.allOf(parameterType);
            return enumSet.toArray()[Integer.parseInt(v.toString())];
        }
        return Enum.valueOf(parameterType, v.toString());
    }


    /**
     * 基本变量类型的枚举
     */
    private enum BasicType {
        BYTE, SHORT, INT, INTEGER, LONG, DOUBLE, FLOAT, BOOLEAN, CHAR, CHARACTER, STRING;

        /**
         * 原始类型为Key，包装类型为Value，例如： int.class -> Integer.class.
         */
        public static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>(8);
        /**
         * 包装类型为Key，原始类型为Value，例如： Integer.class -> int.class.
         */
        public static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>(8);

        static {
            wrapperPrimitiveMap.put(Boolean.class, boolean.class);
            wrapperPrimitiveMap.put(Byte.class, byte.class);
            wrapperPrimitiveMap.put(Character.class, char.class);
            wrapperPrimitiveMap.put(Double.class, double.class);
            wrapperPrimitiveMap.put(Float.class, float.class);
            wrapperPrimitiveMap.put(Integer.class, int.class);
            wrapperPrimitiveMap.put(Long.class, long.class);
            wrapperPrimitiveMap.put(Short.class, short.class);

            for (Map.Entry<Class<?>, Class<?>> entry : wrapperPrimitiveMap.entrySet()) {
                primitiveWrapperMap.put(entry.getValue(), entry.getKey());
            }
        }
    }
}
