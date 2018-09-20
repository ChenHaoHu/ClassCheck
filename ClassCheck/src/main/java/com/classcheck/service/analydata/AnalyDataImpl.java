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
    public Map analyWebSocketData(String signid) {
        List all = getAllData(signid);
        List classname = analyList(all);

        Map map = new HashMap();
        map.put("data",all);
        map.put("classnames",classname);
        return map;
    }


    @Override
    public List getClassData(String signid) {
        List all = getAllData(signid);
        List classname = analyList(all);
        return classname;
    }

    /**
     * 获取所有详细信息
     * @param signid
     * @return
     */
    List getAllData(String signid) {
        //基本数据的集合
        List<Map> list = new ArrayList();
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
    }

    /**
     * 获取班级名 不重复
     * @param data
     * @return
     */
    List analyList(List<Map> data){
        //返回数据
        List<Map<String,Map>> classnames = new ArrayList();
        //是否存在的标识
        boolean flag = true;
        //拿出参数里面的值
        for (int i = 0; i < data.size(); i++) {
            //获得他的每个元素
            Map<String, String> map = data.get(i);
            //获取元素里面的class
            String classname = map.get("class");
            //获取他的类型
            String type = map.get("type");
            //初始化
            flag = true;
            for (int j = 0; j < classnames.size(); j++) {

                if(classnames.size()>0){
                    Map<String,Map> dd = classnames.get(j);
                    if (dd.get("class").get("data").equals(classname)) {
                        Map<String,Integer> mm = dd.get("data");
                        classnames.remove(j);
                        Map cl = new HashMap();
                        Map jj = new HashMap();
                        jj.put("data",classname);
                        cl.put("class",jj);
                        // cl.put("number", (num + 1)+"");
                        if(type.equals("0")){
                            int num =mm.get("sign");
                            num = num+1;
                            mm.put("sign",num);
                            cl.put("data", mm);
                        }else{
                            int num =mm.get("rest");
                            num = num+1;
                            mm.put("rest",num);
                            cl.put("data", mm);
                        }
                        classnames.add(cl);
                        flag = false;
                        break;
                    }
                }
            }
            if(flag == true){
                Map cl = new HashMap();
                Map jj = new HashMap();
                jj.put("data",classname);
                cl.put("class",jj);
                if(type.equals("0")){
                    Map<String,Integer> mm = new HashMap();
                    mm.put("sign",1);
                    mm.put("rest",0);
                    cl.put("data", mm);
                }else{
                    Map<String,Integer> mm = new HashMap();
                    mm.put("sign",0);
                    mm.put("rest",1);
                    cl.put("data", mm);
                }
                classnames.add(cl);
            }
        }

        for (int i = 0; i < classnames.size(); i++) {
            Map<String,Map> map = classnames.get(i);
            Map map1 = map.get("class");
            String str = map1.get("data").toString();
            map1.put("number",stuMapper.findClassnumbyclassname(str));
            map.put("class",map1);
        }

        return classnames;
    }


}
