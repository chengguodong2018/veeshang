package com.vee.shang.vo;

import java.io.Serializable;

public class RespProductAllVo implements Serializable {

    private static final long serialVersionUID = -2836867876634445090L;
    private Long id;
    private String productName;
    private Integer isTop;
    private String img = "";

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
}
