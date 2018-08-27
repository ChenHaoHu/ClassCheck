package com.classcheck.model;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 15:55
 * @Description:
 */
public class Sign {
    private Integer signid;
    private Integer adminid;
    private String pass;
    private String createtime;
    private String endtime;
    private String stulist;
    private String content;

    public Integer getSignid() {
        return signid;
    }

    public void setSignid(Integer signid) {
        this.signid = signid;
    }

    public Integer getAdminid() {
        return adminid;
    }

    public void setAdminid(Integer adminid) {
        this.adminid = adminid;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStulist() {
        return stulist;
    }

    public void setStulist(String stulist) {
        this.stulist = stulist;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sign(Integer signid, Integer adminid, String pass, String createtime, String endtime, String stulist, String content) {
        this.signid = signid;
        this.adminid = adminid;
        this.pass = pass;
        this.createtime = createtime;
        this.endtime = endtime;
        this.stulist = stulist;
        this.content = content;
    }
}

