package com.classcheck.service.analydata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.classcheck.mapper.SignMapper;
import com.classcheck.mapper.StuMapper;
import com.classcheck.model.Sign;
import com.classcheck.model.Stu;
import com.classcheck.service.math.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 简单DI年华
 * @Date: 18-9-6 17:19
 * @Description:
 */
@Service
public class AnalyDataImpl implements AnalyData {

    @Autowired
    private SignMapper signMapper;
    @Autowired
    private StuMapper stuMapper;
    @Autowired
    private MathUtil mathUtil;
    @Autowired
    private RedisTemplate<Object, Sign> signRedisTemplate;


    @Override
    public List analyWebSocketData(String signid) {

            return null;
        }

    List getAllData(String signid) {
        //基本数据的集合
        List list = new ArrayList();
        //获取报名信息
        Sign sign = signRedisTemplate.opsForValue().get(signid + "");
        if (sign == null) {
            List<Sign> signs = signMapper.getsignbyid(signid);
            if (signs.size() == 0) {
                return list;
            } else {
                Sign data = signs.get(0);
                JSONArray json = JSON.parseArray(data.getStulist());
                for (int i = 0; i < json.size(); i++) {
                    Map<String, String> map = new HashMap<>();
                    Stu studata = stuMapper.findnamebyuserid(JSON.parseObject((String) (json.get(i))).get("id").toString()).get(0);
                    map.put("name", studata.getName());
                    map.put("college", studata.getCollege());
                    map.put("class", studata.getClassname());
                    map.put("stuid", studata.getStuid());
                    map.put("type", JSON.parseObject((String) (json.get(i))).get("type").toString());
                    map.put("codetype", JSON.parseObject((String) (json.get(i))).get("codetype").toString());
                    map.put("restname", JSON.parseObject((String) (json.get(i))).get("restname").toString());
                    map.put("restid", JSON.parseObject((String) (json.get(i))).get("restid").toString());
                    map.put("reason", JSON.parseObject((String) (json.get(i))).get("reason").toString());
                    map.put("time", JSON.parseObject((String) (json.get(i))).get("signtime").toString());
                    map.put("signstate", JSON.parseObject((String) (json.get(i))).get("signstate").toString());
                    map.put("signid", sign.getSignid().toString());
                    list.add(map);
                }
                return list;
            }
        } else {
            JSONArray json = JSON.parseArray(sign.getStulist());
            for (int i = 0; i < json.size(); i++) {
                Map<String, String> map = new HashMap<>();
                Stu studata = stuMapper.findnamebyuserid(JSON.parseObject((String) (json.get(i))).get("id").toString()).get(0);
                map.put("name", studata.getName());
                map.put("stuid", studata.getStuid());
                map.put("type", JSON.parseObject((String) (json.get(i))).get("type").toString());
                map.put("codetype", JSON.parseObject((String) (json.get(i))).get("codetype").toString());
                map.put("restname", JSON.parseObject((String) (json.get(i))).get("restname").toString());
                map.put("restid", JSON.parseObject((String) (json.get(i))).get("restid").toString());
                map.put("reason", JSON.parseObject((String) (json.get(i))).get("reason").toString());
                map.put("time", JSON.parseObject((String) (json.get(i))).get("signtime").toString());
                map.put("signstate", JSON.parseObject((String) (json.get(i))).get("signstate").toString());
                map.put("signid", sign.getSignid().toString());
                list.add(map);
            }
            return list;
        }
    }


    List analyLit(List data){





        return null;
    }


}
