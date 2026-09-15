package com.yanque.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis核心配置类
 *
 * @author cr
 */
@Configuration
public class RedisConfiguration {

    // 注入RedisTemplate到IOC容器(默认使用方法中规定好的序列化器)
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建RedisTemplate(使用的是默认的序列化器 ==> 存储中文序列化后的内容看不懂)
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 关联Redis连接对象工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 声明自定义的值序列化器(StringSerializer 序列化中文内容的时候会存储明文、而不是按照特定序列化方式转换)
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 声明当序列化Key、序列化Hash中的Key的时候使用stringRedisSerializer
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 声明自定义的值序列化器(序列化中文内容的时候会存储明文)
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
