package com.classcheck.service.stu;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 17:57
 * @Description:
 */
@Service
public interface StuAuthentication {
    Map<String,String> getStuData(String name,String password);
}
