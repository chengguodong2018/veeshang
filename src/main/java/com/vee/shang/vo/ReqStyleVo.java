package com.vee.shang.vo;

import java.io.Serializable;

public class ReqStyleVo implements Serializable {

    private static final long serialVersionUID = 6073320729129128526L;

    private Long styleId;
    private Long userId;
    private String name;
    private String img;
    private Integer status;

    public Long getStyleId() {
        return styleId;
    }

    public void setStyleId(Long styleId) {
        this.styleId = styleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
