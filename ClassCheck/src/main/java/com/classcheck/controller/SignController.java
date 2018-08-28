package com.classcheck.controller;

import com.alibaba.fastjson.JSON;
import com.classcheck.common.response.RespCode;
import com.classcheck.common.response.ResponseEntity;
import com.classcheck.mapper.SignMapper;
import com.classcheck.model.Sign;
import com.classcheck.model.SignItem;
import com.classcheck.service.math.MathUtil;
import com.classcheck.service.time.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 19:45
 * @Description:
 */

@RestController()
public class SignController {

    @Autowired
    SignMapper signMapper;
    @Autowired
    TimeUtil timeUtil;
    @Autowired
    MathUtil mathUtil;

    /**
     *
     * @param id
     * @param time
     * @param tips
     * @return
     */
    @RequestMapping("/sign/build")
    public ResponseEntity buildSign(Integer id,String time,String tips){
        Sign sign = new Sign(110,id,timeUtil.getNowTime(),time,"[]",tips);
        signMapper.buildsign(sign);
        return  new ResponseEntity(RespCode.SUCCESS,sign);
    }


    /**
     *
     * @param signid
     * @return
     */
    @RequestMapping("/sign/find")
    public ResponseEntity findSign(String signid){
        List<Sign> data = signMapper.getsignbyid(signid);
        return  new ResponseEntity(RespCode.SUCCESS,data);
    }


    /**
     *
     * @param signid
     * @param usrid
     * @param longitude
     * @param latitude
     * @return
     */
    @RequestMapping("/sign/sign")
    public ResponseEntity findSign(Integer signid,Integer usrid,String longitude,String latitude){
        SignItem  signItem = new SignItem(usrid,longitude,latitude,timeUtil.getNowTime(),"未确认");
       if( signMapper.insertstusign(JSON.toJSONString(signItem),signid) ==1){
           return  new ResponseEntity(RespCode.SUCCESS,"ok");
       }
        return  new ResponseEntity(RespCode.SUCCESS,"fail");
    }

}
