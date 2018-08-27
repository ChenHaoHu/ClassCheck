package com.classcheck.controller;

import com.alibaba.fastjson.JSON;
import com.classcheck.common.response.RespCode;
import com.classcheck.common.response.ResponseEntity;
import com.classcheck.mapper.SignMapper;
import com.classcheck.model.Sign;
import com.classcheck.model.SignJson;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 19:45
 * @Description:
 */

@RestController
public class SignController {


    @Autowired
    SignMapper signMapper;

    @RequestMapping("/sign/build")
    public ResponseEntity buildSign(){

        List<SignJson> list = new ArrayList<>();
        list.add(new SignJson(12,1121,13135,"455","成功"));
        list.add(new SignJson(12,1121,13135,"455","成功"));
        list.add(new SignJson(12,1121,13135,"455","成功"));
        list.add(new SignJson(12,1121,13135,"455","成功"));
//        SignJson signJson= new SignJson(12,1121,13135,"455","成功");
//        System.out.println(JSON.toJSONString(signJson));

        Sign sign = new Sign(110,110,"1531","2017-8-9","2017-8-9",JSON.toJSONString(list),"content");

        signMapper.insertsign(sign);

        return  new ResponseEntity(RespCode.SUCCESS,sign);
    }
}
