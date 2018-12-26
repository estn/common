/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler.concrete.number;


import com.argyranthemum.common.core.util.pattern.handler.NumberHandler;

/**
 * @Description: IntegerHandler
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 21:02
 */
public class IntegerHandler extends NumberHandler {

    @Override
    protected Class getClazz() {
        return Integer.class;
    }

    @Override
    protected Number handle(String v) {
        return Integer.parseInt(v);
    }
}
