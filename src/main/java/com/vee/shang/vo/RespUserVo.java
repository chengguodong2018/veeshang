package com.vee.shang.vo;

import java.io.Serializable;

public class RespUserVo implements Serializable {

    private static final long serialVersionUID = -8488537204427041257L;

    private String nickname = "";

    private String headImg = "";

    private String introduction = "";

    private String contactPhone = "";

    private String wechat = "";

    private String coverImg = "";

    private Integer newProductCount;

    private Integer productCount;

    private Object followDetail;

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

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
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

    public Object getFollowDetail() {
        return followDetail;
    }

    public void setFollowDetail(Object followDetail) {
        this.followDetail = followDetail;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
}
