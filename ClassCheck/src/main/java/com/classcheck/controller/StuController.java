package com.classcheck.controller;

import com.classcheck.common.response.RespCode;
import com.classcheck.common.response.ResponseEntity;
import com.classcheck.mapper.StuMapper;
import com.classcheck.model.Stu;
import com.classcheck.service.stu.StuAuthentication;
import com.classcheck.service.time.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 16:15
 * @Description:
 */
@RestController
public class StuController {
    @Autowired
    StuMapper stuMapper;
    @Autowired
    StuAuthentication stuAuthentication;
    @Autowired
    TimeUtil timeUtil;


    @RequestMapping("/stu/add")
    public ResponseEntity adastu(String name,String password){
       Map<String,String> map =  stuAuthentication.getStuData(name,password);
        if(map.size()==0){
            return new ResponseEntity(RespCode.SUCCESS,"密码错误，绑定失败");
        }else{
           if (stuMapper.findnamebystuid(map.get("stuid")) > 0){
               return new ResponseEntity(RespCode.SUCCESS,"无法在不同手机上重复登录，请联系管理员");
           }else {
               //插入操作
               Stu stu = new Stu(0,map.get("name"),map.get("stuid"),map.get("class"),map.get("college"),timeUtil.getNowTime());
               stuMapper.insertuser(stu);
               map.put("userid",stu.getUserid()+"");
               return new ResponseEntity(RespCode.SUCCESS,map);
           }
        }

    }

    @RequestMapping("/test")
    public ResponseEntity ds(){
            return new ResponseEntity(RespCode.SUCCESS,"test");

    }
}
