package com.vee.shang.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Product implements Serializable {

    private static final long serialVersionUID = 7558329442686870145L;

    private Long id;
    @JsonIgnore
    private Long userId;
    private String productName;
    private String productDsc;
    @JsonIgnore
    private String styleId;
    @JsonIgnore
    private String styleChildId;
    @JsonIgnore
    private String is_top;
    private Integer status;
    @JsonIgnore
    private Integer source;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;

    @Transient
    private String webUrl;

    @Transient
    private Integer isPraise=0;

    @Transient
    private String nickname;

    private List<String> imgList;

    private List<Map<String,Object>> colorList;

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDsc() {
        return productDsc;
    }

    public void setProductDsc(String productDsc) {
        this.productDsc = productDsc;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getStyleChildId() {
        return styleChildId;
    }

    public void setStyleChildId(String styleChildId) {
        this.styleChildId = styleChildId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
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

    public List<Map<String, Object>> getColorList() {
        return colorList;
    }

    public void setColorList(List<Map<String, Object>> colorList) {
        this.colorList = colorList;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Integer getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(Integer isPraise) {
        this.isPraise = isPraise;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
