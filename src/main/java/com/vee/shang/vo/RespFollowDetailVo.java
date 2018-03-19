package com.vee.shang.vo;

import java.io.Serializable;

public class RespFollowDetailVo implements Serializable {

    private static final long serialVersionUID = 7343660051303604534L;
    private Long id;
    private String nickname="";
    private Integer dynamicStatus=0;
    private Integer blockStatus=0;
    private Integer fansBlockStatus=0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getDynamicStatus() {
        return dynamicStatus;
    }

    public void setDynamicStatus(Integer dynamicStatus) {
        this.dynamicStatus = dynamicStatus;
    }

    public Integer getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(Integer blockStatus) {
        this.blockStatus = blockStatus;
    }

    public Integer getFansBlockStatus() {
        return fansBlockStatus;
    }

    public void setFansBlockStatus(Integer fansBlockStatus) {
        this.fansBlockStatus = fansBlockStatus;
    }

}
