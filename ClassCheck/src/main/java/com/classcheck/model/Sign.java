package com.classcheck.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 15:55
 * @Description:
 */
public class Sign  implements Serializable {
    private Integer signid;
    private Integer adminid;
    private String createtime;
    private String time;
    private String stulist;
    private String content;

    public Sign() {
    }

    public Sign(Integer signid, Integer adminid, String createtime, String time, String stulist, String content) {
        this.signid = signid;
        this.adminid = adminid;
        this.createtime = createtime;
        this.time = time;
        this.stulist = stulist;
        this.content = content;
    }

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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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


}


