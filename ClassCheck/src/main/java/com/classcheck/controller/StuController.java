package com.classcheck.controller;

import com.classcheck.common.response.RespCode;
import com.classcheck.common.response.ResponseEntity;
import com.classcheck.mapper.StuMapper;
import com.classcheck.model.Stu;
import com.classcheck.service.stu.StuAuthentication;
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

    @RequestMapping("/stu/add")
    public ResponseEntity adastu(String name,String password){
       Map<String,String> map =  stuAuthentication.getStuData(name,password);
        if(map.size()==0){
            return new ResponseEntity(RespCode.SUCCESS,"fail");
        }else{
            //插入操作
            Stu stu = new Stu(0,map.get("name"),map.get("stuid"),null,null);
            stuMapper.insertuser(stu);
            Map<String,String> back = new HashMap<>();

            back.put("userid",stu.getUserid()+"");
            back.put("name",map.get("name"));
            back.put("stuid",map.get("stuid"));
            return new ResponseEntity(RespCode.SUCCESS,back);
        }

    }

    @RequestMapping("/test")
    public ResponseEntity ds(){
            return new ResponseEntity(RespCode.SUCCESS,"test");

    }
}
