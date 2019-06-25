/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.sandman.filmCrawler;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunpeikai
 * @version Film, v0.1 2019/1/29 9:22
 */
public class Film implements Serializable {
    private Integer id;

    /**
     * 电影名
     *
     * @mbggenerated
     */
    private String filmName;

    /**
     * 在线电影url
     *
     * @mbggenerated
     */
    private String filmUrl;

    /**
     * 磁力链接
     *
     * @mbggenerated
     */
    private String magnetUrl;

    /**
     * 迅雷链接
     *
     * @mbggenerated
     */
    private String thunderUrl;

    /**
     * 百度网盘url
     *
     * @mbggenerated
     */
    private String panUrl;

    /**
     * 链接的密码
     *
     * @mbggenerated
     */
    private String panPassword;

    /**
     * 电影封面
     *
     * @mbggenerated
     */
    private String filmImage;

    /**
     * 电影主演
     *
     * @mbggenerated
     */
    private String filmActor;

    private Integer filmType;

    /**
     * 上映时间
     *
     * @mbggenerated
     */
    private String filmTime;

    /**
     * 影片清晰度
     *
     * @mbggenerated
     */
    private String filmClarity;

    /**
     * 电影地区
     *
     * @mbggenerated
     */
    private String filmArea;

    /**
     * 电影导演
     *
     * @mbggenerated
     */
    private String filmDirector;

    /**
     * 电影语言
     *
     * @mbggenerated
     */
    private String filmLanguage;

    /**
     * 电影介绍
     *
     * @mbggenerated
     */
    private String filmDesc;

    private Integer filmGold;

    /**
     * 购买次数
     *
     * @mbggenerated
     */
    private Integer buyCount;

    /**
     * 状态(0:未启用;1:启用)
     *
     * @mbggenerated
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

    /**
     * 删除标记(0:未被删除,1:已被删除)
     *
     * @mbggenerated
     */
    private Integer delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmUrl() {
        return filmUrl;
    }

    public void setFilmUrl(String filmUrl) {
        this.filmUrl = filmUrl;
    }

    public String getMagnetUrl() {
        return magnetUrl;
    }

    public void setMagnetUrl(String magnetUrl) {
        this.magnetUrl = magnetUrl;
    }

    public String getThunderUrl() {
        return thunderUrl;
    }

    public void setThunderUrl(String thunderUrl) {
        this.thunderUrl = thunderUrl;
    }

    public String getPanUrl() {
        return panUrl;
    }

    public void setPanUrl(String panUrl) {
        this.panUrl = panUrl;
    }

    public String getPanPassword() {
        return panPassword;
    }

    public void setPanPassword(String panPassword) {
        this.panPassword = panPassword;
    }

    public String getFilmImage() {
        return filmImage;
    }

    public void setFilmImage(String filmImage) {
        this.filmImage = filmImage;
    }

    public String getFilmActor() {
        return filmActor;
    }

    public void setFilmActor(String filmActor) {
        this.filmActor = filmActor;
    }

    public Integer getFilmType() {
        return filmType;
    }

    public void setFilmType(Integer filmType) {
        this.filmType = filmType;
    }

    public String getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(String filmTime) {
        this.filmTime = filmTime;
    }

    public String getFilmClarity() {
        return filmClarity;
    }

    public void setFilmClarity(String filmClarity) {
        this.filmClarity = filmClarity;
    }

    public String getFilmArea() {
        return filmArea;
    }

    public void setFilmArea(String filmArea) {
        this.filmArea = filmArea;
    }

    public String getFilmDirector() {
        return filmDirector;
    }

    public void setFilmDirector(String filmDirector) {
        this.filmDirector = filmDirector;
    }

    public String getFilmLanguage() {
        return filmLanguage;
    }

    public void setFilmLanguage(String filmLanguage) {
        this.filmLanguage = filmLanguage;
    }

    public String getFilmDesc() {
        return filmDesc;
    }

    public void setFilmDesc(String filmDesc) {
        this.filmDesc = filmDesc;
    }

    public Integer getFilmGold() {
        return filmGold;
    }

    public void setFilmGold(Integer filmGold) {
        this.filmGold = filmGold;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
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

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", filmName='" + filmName + '\'' +
                ", filmUrl='" + filmUrl + '\'' +
                ", magnetUrl='" + magnetUrl + '\'' +
                ", thunderUrl='" + thunderUrl + '\'' +
                ", panUrl='" + panUrl + '\'' +
                ", panPassword='" + panPassword + '\'' +
                ", filmImage='" + filmImage + '\'' +
                ", filmActor='" + filmActor + '\'' +
                ", filmType=" + filmType +
                ", filmTime='" + filmTime + '\'' +
                ", filmClarity='" + filmClarity + '\'' +
                ", filmArea='" + filmArea + '\'' +
                ", filmDirector='" + filmDirector + '\'' +
                ", filmLanguage='" + filmLanguage + '\'' +
                ", filmDesc='" + filmDesc + '\'' +
                ", filmGold=" + filmGold +
                ", buyCount=" + buyCount +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }

    public String getInfoString(){
        return "Film{" +
                "id=" + id +
                ", filmName='" + filmName + '\'' +
                ", filmTime='" + filmTime + '\'' +
                ", filmDirector='" + filmDirector + '\'' +
                '}';
    }
}
