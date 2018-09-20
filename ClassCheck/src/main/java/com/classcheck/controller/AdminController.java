package com.classcheck.controller;

import com.classcheck.common.response.RespCode;
import com.classcheck.common.response.ResponseEntity;
import com.classcheck.mapper.AdminMapper;
import com.classcheck.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 19:43
 * @Description:
 */

@RestController
public class AdminController {

    @Autowired
    AdminMapper adminMapper;


    @RequestMapping("/admin/check")
    public ResponseEntity checkPass(String name,String pass){
       List<Admin> data =  adminMapper.getPassword(name);
        if(data.size() == 0){
            return  new ResponseEntity(RespCode.SUCCESS,"没有该帐户");
        }else{
            System.out.println(data.get(0).getPassword());
            System.out.println(pass);
            if(data.get(0).getPassword().equals(pass)){
               return new ResponseEntity(RespCode.SUCCESS,data.get(0).getAdminid());
            }else{
                return new ResponseEntity(RespCode.SUCCESS,"密匙验证失败");
            }
        }
    }
}
