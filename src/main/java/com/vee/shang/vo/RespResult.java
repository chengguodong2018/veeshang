package com.vee.shang.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

public class RespResult implements Serializable {
    private static final long serialVersionUID = -4052933084199665808L;
    private Integer errcode;
    @JsonSerialize(
            include = JsonSerialize.Inclusion.NON_NULL
    )
    private String errmsg;
    @JsonSerialize(
            include = JsonSerialize.Inclusion.NON_NULL
    )
    private Object result;

    public RespResult() {
    }


    public RespResult(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.setErrmsg(errmsg);
    }

    public RespResult(Integer errcode, String errmsg, Object result) {
        this.errcode = errcode;
        this.setErrmsg(errmsg);
        this.result = result;
    }

    public RespResult(Integer errcode, Object result) {
        this.errcode = errcode;
        this.result = result;
    }

    public RespResult(Integer errcode) {
        this.errcode = errcode;
        this.result = "";
    }

    public Integer getErrcode() {
        return this.errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}