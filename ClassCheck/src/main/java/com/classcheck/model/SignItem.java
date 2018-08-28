package com.classcheck.model;

import java.io.Serializable;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 16:01
 * @Description:
 */
public class SignItem implements Serializable {
    //签到的学生id
    private Integer id;
    //签到学生的位置 经度
    private String longitude;
    //签到学生的位置 纬度
    private String latitude;
    //签到时间
    private String signtime;
    //签到状态
    private String signstate;

    public SignItem(Integer id, String longitude, String latitude, String signtime, String signstate) {
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
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
