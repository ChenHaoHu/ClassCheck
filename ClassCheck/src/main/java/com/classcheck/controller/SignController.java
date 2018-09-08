package com.classcheck.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.classcheck.common.config.SignRedisConfig;
import com.classcheck.common.response.RespCode;
import com.classcheck.common.response.ResponseEntity;
import com.classcheck.mapper.SignMapper;
import com.classcheck.mapper.StuMapper;
import com.classcheck.model.Sign;
import com.classcheck.model.SignItem;
import com.classcheck.model.Stu;
import com.classcheck.service.analydata.AnalyData;
import com.classcheck.service.math.MathUtil;
import com.classcheck.service.radis.SignDataToDataBase;
import com.classcheck.service.time.TimeUtil;
import com.classcheck.websocket.MyWebSocket;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    StuMapper stuMapper;
    @Autowired
    AnalyData analyData;
    @Autowired
    MathUtil mathUtil;
    @Autowired
    RedisTemplate<Object, Sign> signRedisTemplate;
    @Autowired
    SignDataToDataBase signDataToDataBase;


    @RequestMapping("/sign/class")
    public ResponseEntity getClassnameList(@Param("signid") String signid){
        List list = analyData.getClassData(signid);
        return new ResponseEntity(RespCode.SUCCESS,list);
    }


    /**
     * @param signid
     * @return
     * 展示所有信息
     */
    @RequestMapping("/sign/detail")
    public ResponseEntity getdetail(String signid) {
            Map data = analyData.analyWebSocketData(signid);
           signDataToDataBase.run(Integer.parseInt(signid));
           return new ResponseEntity(RespCode.SUCCESS,data);
    }


    /**
     * @param id
     * @param time
     * @param tips
     * @return
     */
    @RequestMapping("/sign/build")
    public ResponseEntity buildSign(Integer id, String time, String tips,String pass) {
      if ("abcde".equals(pass)){
          Sign sign = new Sign(110, id, timeUtil.getNowTime(), time, "[]", tips);
          signMapper.buildsign(sign);
          signRedisTemplate.opsForValue().set(sign.getSignid()+"", sign);
          return new ResponseEntity(RespCode.SUCCESS, sign);
      }else{
          return new ResponseEntity(RespCode.SUCCESS, "密匙错误");
      }
    }


    /**
     * @param signid
     * @return
     */
    @RequestMapping("/sign/find")
    public ResponseEntity findSign(String signid) {
       Object data = signRedisTemplate.opsForValue().get(signid+"");
        if (data == null) {
            return new ResponseEntity(RespCode.SUCCESS, "已经结束或不存在");
        } else {
            ((Sign) data).setStulist("");
            return new ResponseEntity(RespCode.SUCCESS, data);
        }
    }


    /**
     * @param signid
     * @param usrid
     * @param codetype
     * @return
     */
    @RequestMapping("/sign/sign")
    public ResponseEntity toSign(Integer signid, Integer usrid, String codetype) {
        String now = timeUtil.getNowTime();
        Sign sign = signMapper.getsignbyid(signid + "").get(0);
        int creattime = Integer.parseInt(sign.getCreatetime().substring(11, 13)) * 60 * 60 +
                Integer.parseInt(sign.getCreatetime().substring(14, 16)) * 60 +
                Integer.parseInt(sign.getCreatetime().substring(17, 19));

        int signtime = Integer.parseInt(now.substring(11, 13)) * 60 * 60 +
                Integer.parseInt(now.substring(14, 16)) * 60 +
                Integer.parseInt(now.substring(17, 19));
        int time = Integer.parseInt(sign.getTime()) * 60;
        System.out.println(creattime);
        System.out.println(signtime);
        System.out.println(time);
        System.out.println(signtime - creattime);
        //签到超时
        if (signtime - creattime > time) {
            SignItem signItem = new SignItem(usrid, 0,codetype, "none","none","none",now, "签到超时");
            if (signMapper.insertstusign(JSON.toJSONString(signItem), signid) == 1) {
                return new ResponseEntity(RespCode.SUCCESS, "签到超时");
            }
            return new ResponseEntity(RespCode.SUCCESS, "fail");
        }
        //签到成功
        SignItem signItem = new SignItem(usrid, 0,codetype, "none","none","none",now, "签到成功");
        if (signMapper.insertstusign(JSON.toJSONString(signItem), signid) == 1) {


            return new ResponseEntity(RespCode.SUCCESS, "签到成功");
        }
        return new ResponseEntity(RespCode.SUCCESS, "fail");
    }

    /**
     *
     * @param signid
     * @param userid
     * @param codetype
     * @param reststuname
     * @param reststuid
     * @param reason
     * @return
     */
    @RequestMapping("/sign/r/rest")
    public ResponseEntity toRest(Integer signid, Integer userid,String codetype, String reststuname, String reststuid, String reason) {
       //判断内存中是否存在
        Sign sign = signRedisTemplate.opsForValue().get(signid + "");
        if (sign == null) {
            return new ResponseEntity(RespCode.SUCCESS, "您访问的签到项目不存在或者已经过期");
        }
        else {

             //存在时
             String now = timeUtil.getNowTime();

             //转换时间
             int creattime = Integer.parseInt(sign.getCreatetime().substring(11, 13)) * 60 * 60 +
                     Integer.parseInt(sign.getCreatetime().substring(14, 16)) * 60 +
                     Integer.parseInt(sign.getCreatetime().substring(17, 19));
             int signtime = Integer.parseInt(now.substring(11, 13)) * 60 * 60 +
                     Integer.parseInt(now.substring(14, 16)) * 60 +
                     Integer.parseInt(now.substring(17, 19));
             int time = Integer.parseInt(sign.getTime()) * 60;

             //请假超时
             if (signtime - creattime > time) {
                 JSONArray json = JSON.parseArray((sign).getStulist());
                 SignItem signItem = new SignItem(userid, 1,codetype, reststuname,reststuid,reason,now, "请假超时");
                 json.add(JSON.toJSONString(signItem));
                 sign.setStulist(JSON.toJSONString(json));
                 signRedisTemplate.opsForValue().set(signid+"",sign);
                 //转存到数据库
                 //signDataToDataBase.run(signid);
                 try {
                     MyWebSocket.sendInfo(signid.toString());
                 }catch (Exception e){

                 }
                 return new ResponseEntity(RespCode.SUCCESS, "请假超时");
             }else {
                 JSONArray json = JSON.parseArray((sign).getStulist());
                 SignItem signItem = new SignItem(userid,1,codetype,  reststuname,reststuid,reason,now, "请假成功");
                 json.add(JSON.toJSONString(signItem));
                 (sign).setStulist(JSON.toJSONString(json));
                 signRedisTemplate.opsForValue().set(signid+"",sign);
                 try {
                     MyWebSocket.sendInfo(signid.toString());
                 }catch (Exception e){

                 }
                 return new ResponseEntity(RespCode.SUCCESS, "请假成功");
             }
        }
    }


    /**
     *
     * @param signid
     * @param userid
     * @param codetype
     * @return
     */
    @RequestMapping("/sign/r/sign")
    public ResponseEntity SignInRedis(Integer signid, Integer userid, String codetype) {
        //判断内存中是否存在
        Sign sign = signRedisTemplate.opsForValue().get(signid + "");
        if (sign == null) {
            return new ResponseEntity(RespCode.SUCCESS, "您访问的签到项目不存在或者已经过期");
        }
        else {
            boolean isSigned = false;
            String stulist = sign.getStulist();
            JSONArray jj = JSON.parseArray(stulist);

            for (int i = 0; i < jj.size(); i++) {
                String id = JSON.parseObject((String)jj.get(i)).get("id").toString();
                String type = JSON.parseObject((String)jj.get(i)).get("type").toString();
                if (userid.toString().equals(id)&& Integer.parseInt(type)==0) {
                    isSigned = true;
                }
            }
            if(!isSigned) {
              //存在时
              String now = timeUtil.getNowTime();
//              Sign sign = signRedisTemplate.opsForValue().get(signid + "");
              //转换时间
              int creattime = Integer.parseInt(sign.getCreatetime().substring(11, 13)) * 60 * 60 +
                      Integer.parseInt(sign.getCreatetime().substring(14, 16)) * 60 +
                      Integer.parseInt(sign.getCreatetime().substring(17, 19));
              int signtime = Integer.parseInt(now.substring(11, 13)) * 60 * 60 +
                      Integer.parseInt(now.substring(14, 16)) * 60 +
                      Integer.parseInt(now.substring(17, 19));
              int time = Integer.parseInt(sign.getTime()) * 60;

              //签到超时
              if (signtime - creattime > time) {
                  JSONArray json = JSON.parseArray((sign).getStulist());
                  SignItem signItem = new SignItem(userid, 0, codetype, "none", "none", "none", now, "签到超时");
                  json.add(JSON.toJSONString(signItem));
                  sign.setStulist(JSON.toJSONString(json));
                  signRedisTemplate.opsForValue().set(signid + "", sign);
                  //转存到数据库
                  //signDataToDataBase.run(signid);
                  try {
                      MyWebSocket.sendInfo(signid.toString());
                  }catch (Exception e){

                  }
                  return new ResponseEntity(RespCode.SUCCESS, "签到超时");
              } else {
                  JSONArray json = JSON.parseArray((sign).getStulist());
                  SignItem signItem = new SignItem(userid, 0, codetype, "none", "none", "none", now, "签到成功");
                  json.add(JSON.toJSONString(signItem));
                  (sign).setStulist(JSON.toJSONString(json));
                  signRedisTemplate.opsForValue().set(signid + "", sign);
                  try {
                      MyWebSocket.sendInfo(signid.toString());
                  }catch (Exception e){

                  }
                  return new ResponseEntity(RespCode.SUCCESS, "签到成功");
              }
          }else {
              return new ResponseEntity(RespCode.SUCCESS, "已经签到过了");
          }
        }
    }
}
