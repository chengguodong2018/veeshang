package com.vee.shang.vo;

import java.io.Serializable;

public class ReqProductPraiseVo implements Serializable {

    private static final long serialVersionUID = 5884437699329858227L;
    private Long userId;
    private Long productId;
    private Integer praiseType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getPraiseType() {
        return praiseType;
    }

    public void setPraiseType(Integer praiseType) {
        this.praiseType = praiseType;
    }
}
