package com.argyranthemum.common.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorResult<T> {
    public static Long START_CURSOR = 0L;
    public static Long END_CURSOR = -1L;
    private List<T> records;
    private Long nextCursor;
}