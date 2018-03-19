package com.vee.shang.vo;

import java.io.Serializable;

public class RespFollowVo implements Serializable {

    private static final long serialVersionUID = 8825170838395106162L;
    private Long id;
    private Long userId;
    private String nickname="";
    private String headImg="";
    private String introduction="";
    private Integer newProductCount=0;
    private Integer productCount=0;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getNewProductCount() {
        return newProductCount;
    }

    public void setNewProductCount(Integer newProductCount) {
        this.newProductCount = newProductCount;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }
}
