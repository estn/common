package com.argyranthemum.common.core.pojo; /**
 * Copyright  2016  Pemass
 * All Right Reserved.
 */

import com.google.common.base.Preconditions;

/**
 * @Description: Http请求返回数据格式
 * @Author: estn.zuo
 * @CreateTime: 2016-06-13 13:53
 */
public final class Result {

    /**
     * 是否成功 true-成功、false-失败
     */
    public final Boolean succeed;

    /**
     * Http状态码
     */
    private Integer code;

    /**
     * Http内容
     */
    private String data;

    public Result() {
        succeed = false;
        code = 0;
        data = null;
    }

    public Result(int code, String data) {
        Preconditions.checkArgument(code > 0, "Http Status Code is must greater than zero");
        this.code = code;
        this.data = data;
        String _code = Integer.toString(code);
        succeed = _code.startsWith("2");
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public Integer getCode() {
        return code;
    }

    public String getData() {
        return data;
    }


    @Override
    public String toString() {
        return "Result{" +
                "succeed=" + succeed +
                ", code=" + code +
                ", data='" + data + '\'' +
                '}';
    }
}
