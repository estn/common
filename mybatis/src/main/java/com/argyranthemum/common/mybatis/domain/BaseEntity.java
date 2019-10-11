package com.argyranthemum.common.mybatis.domain;


import com.argyranthemum.common.core.util.UUIDUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Description: 基础类，其他实体类集成本类
 * Author: Estn
 * CreateTime: 2014-09-17 14:12
 */
@Data
@Accessors(chain = true)
public class BaseEntity {

    private static final long serialVersionUID = 2820473684086191843L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据添加时间，当insert时设置本值
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 数据修改时间，当insert和update操作时修改本字段值
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否可用，标识数据的状态
     * <p/>
     * 0:不可用
     * <p/>
     * 1:可用(默认值)
     */
    @TableLogic(value = "1", delval = "0")
    @TableField(select = false)
    private Integer available = 1;

    /**
     * UUID，唯一代表一条记录信息
     */
    private String uuid = UUIDUtil.random();


}
