package com.sandman.filmCrawler;

import java.io.Serializable;
import java.util.Date;

public class FilmType implements Serializable {
    private Integer id;

    /**
     * 分类名
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 状态(0:未启用;1:启用)
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}