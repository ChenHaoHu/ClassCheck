package com.classcheck.model;

import java.io.Serializable;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 16:01
 * @Description:
 */
//implements Serializable
public class SignItem  {
    //签到的学生id
    private Integer id;
    //签到类型
    //0---签到  1--请假
    private  Integer type;
    //二维码来源
    private String codetype;
    //请假人姓名
    private  String restname;
    //请假人学号
    private  String restid;
    //请假原因
    private String reason;
    //签到时间
    private String signtime;
    //签到状态
    private String signstate;

    public SignItem(Integer id, Integer type, String codetype, String restname, String restid, String reason, String signtime, String signstate) {
        this.id = id;
        this.type = type;
        this.codetype = codetype;
        this.restname = restname;
        this.restid = restid;
        this.reason = reason;
        this.signtime = signtime;
        this.signstate = signstate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public String getRestname() {
        return restname;
    }

    public void setRestname(String restname) {
        this.restname = restname;
    }

    public String getRestid() {
        return restid;
    }

    public void setRestid(String restid) {
        this.restid = restid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSigntime() {
        return signtime;
    }

    public void setSigntime(String signtime) {
        this.signtime = signtime;
    }

    public String getSignstate() {
        return signstate;
    }

    public void setSignstate(String signstate) {
        this.signstate = signstate;
    }
}
