package com.vee.shang.vo;

import java.io.Serializable;

public class ReqColorSizeVo implements Serializable {

    private static final long serialVersionUID = -3011697825708804355L;
    private Long id;
    private Long userId;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
