package com.classcheck;

import com.classcheck.service.stu.StuAuthenticationImpl;
import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClasscheckApplicationTests {

    @Test
    public void contextLoads() {
        StuAuthenticationImpl stuAuthentication = new StuAuthenticationImpl();
        Map<String,String> map = stuAuthentication.getStuData("1611010102","hcy1314");
        System.out.println(map.size());
        for(String s : map.keySet()){
            System.out.println("key : "+s+" value : "+map.get(s));
        }
    }

}
