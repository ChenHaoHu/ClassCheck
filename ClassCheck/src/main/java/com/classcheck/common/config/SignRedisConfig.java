package com.classcheck.common.config;

import com.classcheck.model.Sign;
import com.classcheck.model.Stu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @Auther: 简单DI年华
 * @Date: 18-9-4 19:27
 * @Description:
 */
@Configuration
public class SignRedisConfig {
    @Bean
    public RedisTemplate<Object, Sign> signRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object,Sign> template = new RedisTemplate<Object, Sign>();
        template.setConnectionFactory(connectionFactory);
        Jackson2JsonRedisSerializer<Sign> serializer = new Jackson2JsonRedisSerializer<Sign>(Sign.class);
        template.setDefaultSerializer(serializer);
        return template;
    }
}
