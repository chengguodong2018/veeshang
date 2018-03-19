package com.vee.shang.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RespProductNewVo implements Serializable {

    private static final long serialVersionUID = -3873355701566551969L;
    private Long id;
    private String productName;
    private String productDsc="";
    private Integer isTop;
    private Date createTime;
    private List<String> imgList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
}
