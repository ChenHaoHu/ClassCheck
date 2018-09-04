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

        if(data.size() == 0){
            return  new ResponseEntity(RespCode.SUCCESS,"null");
        }else{
            return  new ResponseEntity(RespCode.SUCCESS,data);
        }
    }


    /**
     *
     * @param signid
     * @param usrid
     * @param codetype
     * @return
     */
    @RequestMapping("/sign/sign")
    public ResponseEntity toSign(Integer signid,Integer usrid,String codetype){

        String now = timeUtil.getNowTime();
        Sign sign = signMapper.getsignbyid(signid+"").get(0);
//        System.out.println(sign.getCreatetime().substring(11,13));
//        System.out.println(sign.getCreatetime().substring(14,16));
//        System.out.println(sign.getCreatetime().substring(17,19));
        int creattime = Integer.parseInt(sign.getCreatetime().substring(11,13))*60*60+
                Integer.parseInt(sign.getCreatetime().substring(14,16))*60+
                Integer.parseInt(sign.getCreatetime().substring(17,19));

        int signtime = Integer.parseInt(now.substring(11,13))*60*60+
                Integer.parseInt(now.substring(14,16))*60+
                Integer.parseInt(now.substring(17,19));
        int time = Integer.parseInt(sign.getTime())*60;
        System.out.println(creattime);
        System.out.println(signtime);
        System.out.println(time);

        //签到超时
        if(creattime - signtime > time){
            SignItem  signItem = new SignItem(usrid,codetype,now,"签到超时");
            if( signMapper.insertstusign(JSON.toJSONString(signItem),signid) ==1){
                return  new ResponseEntity(RespCode.SUCCESS,"签到超时");
            }
            return  new ResponseEntity(RespCode.SUCCESS,"fail");
        }
        //签到成功
        SignItem  signItem = new SignItem(usrid,codetype,now,"签到成功");
       if( signMapper.insertstusign(JSON.toJSONString(signItem),signid) ==1){

           return  new ResponseEntity(RespCode.SUCCESS,"签到成功");
       }
        return  new ResponseEntity(RespCode.SUCCESS,"fail");
    }

    /**
     *
     * @param signid
     * @return
     */
    @RequestMapping("/sign/rest")
    public ResponseEntity toRest(Integer signid,Integer userid,String reststuname,String reststuid,String reason){
        String now = timeUtil.getNowTime();

        return  new ResponseEntity(RespCode.SUCCESS,"fail");
    }

//    /**
//     *
//     * @param signid
//     * @param usrid
//     * @return
//     */
//    @RequestMapping("/sign/leaf")
//    public ResponseEntity toLeave(Integer signid,Integer usrid,String signstate){
//        SignItem  signItem = new SignItem(usrid,timeUtil.getNowTime(),signstate);
//        if( signMapper.insertstusign(JSON.toJSONString(signItem),signid) ==1){
//            return  new ResponseEntity(RespCode.SUCCESS,"ok");
//        }
//        return  new ResponseEntity(RespCode.SUCCESS,"fail");
//    }

}
