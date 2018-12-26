/**
 * Copyright  2017  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.core.util.pattern.handler.concrete;


import com.argyranthemum.common.core.util.pattern.handler.NumberHandler;
import com.argyranthemum.common.core.util.pattern.handler.concrete.number.*;

/**
 * @Description: NumberHandlerSupport
 * @Author: estn.zuo
 * @CreateTime: 2017-04-09 21:18
 */
public class NumberHandlerSupport {

    public static Number handle(Object v, Class parameterType) {

        NumberHandler integerHandler = new IntegerHandler();
        NumberHandler longHandler = new LongHandler();
        NumberHandler shortHandler = new ShortHandler();
        NumberHandler bigDecimalHandler = new BigDecimalHandler();
        NumberHandler floatHandler = new FloatHandler();
        NumberHandler doubleHandler = new DoubleHandler();

        integerHandler.setNextHandler(longHandler);
        longHandler.setNextHandler(shortHandler);
        shortHandler.setNextHandler(bigDecimalHandler);
        bigDecimalHandler.setNextHandler(floatHandler);
        floatHandler.setNextHandler(doubleHandler);
        return integerHandler.handleRequest(v, parameterType);
    }
}
