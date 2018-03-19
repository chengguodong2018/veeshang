package com.vee.shang.vo;

import java.io.Serializable;

public class ReqProductModelVo implements Serializable {

    private static final long serialVersionUID = 8852081439160723107L;
    private Long colorId;
    private String sizeIds;

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public String getSizeIds() {
        return sizeIds;
    }

    public void setSizeIds(String sizeIds) {
        this.sizeIds = sizeIds;
    }
}
