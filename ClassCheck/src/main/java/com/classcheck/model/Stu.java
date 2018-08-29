package com.classcheck.model;

import java.util.List;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 15:15
 * @Description:
 */
public class Stu {
    private Integer userid;
    //姓名
    private String name;
    //学号
    private String stuid;
    //签到成功的id
    private String sign;
    //签到失败的id
    private String unsign;

    public Stu(Integer userid, String name, String stuid, String sign, String unsign) {
        this.userid = userid;
        this.name = name;
        this.stuid = stuid;
        this.sign = sign;
        this.unsign = unsign;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUnsign() {
        return unsign;
    }

    public void setUnsign(String unsign) {
        this.unsign = unsign;
    }
}
