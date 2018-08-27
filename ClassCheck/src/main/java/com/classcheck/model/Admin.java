package com.classcheck.model;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 15:55
 * @Description:
 */
public class Admin {
    private  Integer adminid;
    private String account;
    private String password;


    public Admin(Integer adminid, String account, String password) {
        this.adminid = adminid;
        this.account = account;
        this.password = password;
    }

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
