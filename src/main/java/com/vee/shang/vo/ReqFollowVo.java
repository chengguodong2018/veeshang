package com.vee.shang.vo;

import java.io.Serializable;

public class ReqFollowVo implements Serializable {

    private static final long serialVersionUID = -3011697825708804355L;
    private Long userId;
    private Long id;
    private Long dynamicStatus;//是否查看动态 0：否 1：是
    private Integer blockStatus;//粉丝将关注用户拉人黑名单标记 0：否 1：是
    private Integer fansBlockStatus;//粉丝黑名单标记 0：否 1：是
    private String remarkFollowName;//'我的关注备注'
    private String remarkFansName;//'我的粉丝备注'
    private Integer takeOff = 0;//取关 1:取关

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDynamicStatus() {
        return dynamicStatus;
    }

    public void setDynamicStatus(Long dynamicStatus) {
        this.dynamicStatus = dynamicStatus;
    }

    public Integer getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(Integer blockStatus) {
        this.blockStatus = blockStatus;
    }

    public String getRemarkFollowName() {
        return remarkFollowName;
    }

    public void setRemarkFollowName(String remarkFollowName) {
        this.remarkFollowName = remarkFollowName;
    }

    public String getRemarkFansName() {
        return remarkFansName;
    }

    public void setRemarkFansName(String remarkFansName) {
        this.remarkFansName = remarkFansName;
    }

    public Integer getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(Integer takeOff) {
        this.takeOff = takeOff;
    }

    public Integer getFansBlockStatus() {
        return fansBlockStatus;
    }

    public void setFansBlockStatus(Integer fansBlockStatus) {
        this.fansBlockStatus = fansBlockStatus;
    }
}
