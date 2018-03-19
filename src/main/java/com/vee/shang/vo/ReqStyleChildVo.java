package com.vee.shang.vo;

import java.io.Serializable;

public class ReqStyleChildVo implements Serializable {

    private static final long serialVersionUID = 1383378400864467687L;
    private Long styleChildId;
    private Long styleId;
    private Long userId;
    private String name;
    private String img;
    private Integer status;

    public Long getStyleChildId() {
        return styleChildId;
    }

    public void setStyleChildId(Long styleChildId) {
        this.styleChildId = styleChildId;
    }

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
