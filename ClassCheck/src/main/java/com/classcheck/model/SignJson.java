package com.classcheck.model;

import java.io.Serializable;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 16:01
 * @Description:
 */
public class SignJson implements Serializable {
    //签到的学生id
    private Integer id;
    //签到学生的位置 经度
    private Integer longitude;
    //签到学生的位置 纬度
    private Integer latitude;
    //签到时间
    private String signtime;
    //签到状态
    private String signstate;

    public SignJson(Integer id, Integer longitude, Integer latitude, String signtime, String signstate) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.signtime = signtime;
        this.signstate = signstate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
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
