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

    private String classname;

    private String college;

    private String creattime;

    public Stu(Integer userid, String name, String stuid, String classname, String college, String creattime) {
        this.userid = userid;
        this.name = name;
        this.stuid = stuid;
        this.classname = classname;
        this.college = college;
        this.creattime = creattime;
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

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }
}
