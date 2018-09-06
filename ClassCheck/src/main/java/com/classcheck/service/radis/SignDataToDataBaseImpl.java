package com.classcheck.service.radis;

import com.classcheck.mapper.SignMapper;
import com.classcheck.model.Sign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Auther: 简单DI年华
 * @Date: 18-9-4 20:49
 * @Description:
 */
@Service
public class SignDataToDataBaseImpl implements SignDataToDataBase {

    @Autowired
    SignMapper  signMapper;
    @Autowired
    RedisTemplate<Object, Sign> signRedisTemplate;

    @Async
    @Override
    public void run(Integer signid) {

        try{
            Thread.sleep(6000);

        }catch (Exception e) {
            System.out.println(e);
        }

       Object ob =  signRedisTemplate.opsForValue().get(signid+"");

     if(ob == null){
         System.out.println("内存中查询为空");
     }else{
         System.out.println("ojbk");
         signMapper.insertsigndata(((Sign) ob).getStulist(),signid);
     }
    }
}
