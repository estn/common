/**
 * Copyright  2020  weibo
 * All Right Reserved.
 */
package com.argyranthemum.common.core.serializer;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @Description: TypeRef
 * @CreateTime: 2020-08-27 09:33
 */
public class TypeRef<V> {
    public static final TypeReference<Integer> INT = new TypeReference<Integer>() {
    };

    public static final TypeReference<String> STR = new TypeReference<String>() {
    };

    public static final TypeReference<Object> OBJ = new TypeReference<Object>() {
    };

    public static final TypeReference<List<Integer>> LIST_INT = new TypeReference<List<Integer>>() {
    };

    public static final TypeReference<List<String>> LIST_STR = new TypeReference<List<String>>() {
    };

    public static final TypeReference<List<Object>> LIST_OBJ = new TypeReference<List<Object>>() {
    };

    public final TypeReference<V> T = new TypeReference<V>() {
    };
}
