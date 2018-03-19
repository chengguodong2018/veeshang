package com.vee.shang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class StyleChild implements Serializable {
    private static final long serialVersionUID = -4117559319461226147L;

    @Id
    private Long styleChildId;

    @JsonIgnore
    private Long styleId;

    private String name;

    private String img;

    private Integer sort;

    private Integer status;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    public Long getStyleId() {
        return styleId;
    }

    public void setStyleId(Long styleId) {
        this.styleId = styleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public Long getStyleChildId() {
        return styleChildId;
    }

    public void setStyleChildId(Long styleChildId) {
        this.styleChildId = styleChildId;
    }
}