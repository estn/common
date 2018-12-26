/*
 * 文件名：Response.java
 * 版权：
 * 描述：
 * 修改人：estn.zuo
 * 修改时间：2013-6-24 下午10:26:35
 */
package com.argyranthemum.common.api.base;

/**
 * @author estn.zuo
 * @description API返回数据结构
 * @date 2013-6-24
 */
public class Response {

    /**
     * 返回状态码
     * ACK.0001
     */
    private String code = "ACK.0001";

    /**
     * 构建版本号各分类之间使用"|"分隔
     */
    private String build;

    /**
     * 数据返回节点
     */
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }
}
