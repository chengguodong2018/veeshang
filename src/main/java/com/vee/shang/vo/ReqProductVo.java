package com.vee.shang.vo;

import java.io.Serializable;
import java.util.List;

public class ReqProductVo implements Serializable {

    private static final long serialVersionUID = 1600913202625440356L;
    private Long productId;
    private Long userId;
    private String productName;
    private String productDsc;
    private String productImg;
    private List<ReqProductModelVo> productModel;
    private String styleId;
    private String styleChildId;
    private Integer status;
    private Integer source;
    private Integer isTop;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
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

    public List<ReqProductModelVo> getProductModel() {
        return productModel;
    }

    public void setProductModel(List<ReqProductModelVo> productModel) {
        this.productModel = productModel;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
}
