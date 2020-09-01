/*
 * 文件名：Response.java
 * 版权：
 * 描述：
 * 修改人：estn.zuo
 * 修改时间：2013-6-24 下午10:26:35
 */
package com.argyranthemum.common.api.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @author estn.zuo
 * @description API返回数据结构
 * @date 2013-6-24
 */
@Data
public class Response {

    @JsonIgnore
    public static final String MSG_SUCCESS = "success";

    @JsonIgnore
    private int status = HttpServletResponse.SC_OK;

    /**
     * 返回状态码
     * ACK.0001
     */
    private String code = "ACK.0001";

    /**
     * 状态码说明
     */
    private String msg;

    /**
     * 数据返回节点
     */
    private Object data;
}
