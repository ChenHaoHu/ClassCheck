package com.classcheck.service.radis;

import org.springframework.stereotype.Service;

/**
 * @Auther: 简单DI年华
 * @Date: 18-9-4 20:48
 * @Description:
 */
@Service
public interface SignDataToDataBase {
    void run(Integer signid);
}
