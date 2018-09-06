package com.classcheck;

import com.classcheck.model.Stu;
import com.classcheck.service.stu.StuAuthenticationImpl;
import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClasscheckApplicationTests {

    @Autowired
    RedisConnectionFactory factory;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
//    RedisTemplate<Object, Stu> stuRedisTemplate;

    @Test
    public void contextLoads() {
        StuAuthenticationImpl stuAuthentication = new StuAuthenticationImpl();
        Map<String,String> map = stuAuthentication.getStuData("1611010102","hcy1314");
        System.out.println(map.size());
        for(String s : map.keySet()){
            System.out.println("key : "+s+" value : "+map.get(s));
        }
    }

    @Test
    public void textRedis1() {
        RedisConnection conn = factory.getConnection();
   //     conn.set("hello".getBytes(), "world".getBytes());
        System.out.println(new String(conn.get("hello".getBytes())));
    }

    @Test
    public void textRedis2() {
//    Stu stu = new Stu(1,"21","21","312","321","#21");
//    stuRedisTemplate.opsForValue().set("test",stu);
//    Object obs = stuRedisTemplate.opsForValue().get("test");
//        System.out.println(((Stu) obs).getName());
    }
}
