package com.argyranthemum.common.domain;


import com.argyranthemum.common.core.enums.AvailableEnum;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

/**
 * Description: 基础类，其他实体类集成本类
 * Author: Estn
 * CreateTime: 2014-09-17 14:12
 */
@MappedSuperclass
public class BaseDomain {

    private static final long serialVersionUID = 2820473684086191843L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 数据添加时间，当insert时设置本值
     */
    @Column(name = "create_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    /**
     * 数据修改时间，当insert和update操作时修改本字段值
     */
    @Column(name = "update_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 是否可用，标识数据的状态
     * <p/>
     * 0:不可用
     * <p/>
     * 1:可用(默认值)
     */
    @Column(name = "available")
    @Enumerated(EnumType.ORDINAL)
    private AvailableEnum available = AvailableEnum.AVAILABLE;

    /**
     * UUID，唯一代表一条记录信息
     */
    @Column(name = "uuid", length = 36)
    private String uuid = UUID.randomUUID().toString();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public AvailableEnum getAvailable() {
        return available;
    }

    public void setAvailable(AvailableEnum available) {
        this.available = available;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
