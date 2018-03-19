package com.vee.shang.vo;

import java.io.Serializable;

public class ReqLoginVo implements Serializable {

    private static final long serialVersionUID = 4994739698112685022L;
    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
