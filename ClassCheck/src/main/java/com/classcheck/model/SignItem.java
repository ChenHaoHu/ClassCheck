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

    private String codetype;

    //签到时间
    private String signtime;
    //签到状态
    private String signstate;

    public SignItem(Integer id, String codetype, String signtime, String signstate) {
        this.id = id;
        this.codetype = codetype;
        this.signtime = signtime;
        this.signstate = signstate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getLatitude() {
        return codetype;
    }

    public void setLatitude(String codetype) {
        this.codetype = codetype;
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
