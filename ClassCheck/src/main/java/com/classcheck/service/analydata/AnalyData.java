package com.classcheck.service.analydata;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 简单DI年华
 * @Date: 18-9-6 17:17
 * @Description: 用于分析数据
 */

@Service
public interface AnalyData {

    Map analyWebSocketData(String signid);

    List getClassData(String signid);

}
