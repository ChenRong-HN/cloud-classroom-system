package com.yanque.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * SpringCache参数声明
 *
 * @author cr
 */
@Slf4j
@Configuration
@EnableCaching // 开启SpringCache缓存功能
public class SpringCacheConfiguration {

    // 注入Redis连接工厂用于创建RedisCacheManager
    @Resource
    private RedisConnectionFactory factory;

    /**
     * 自定义缓存Key生成器
     */
    @Bean
    public KeyGenerator keyGenerator() {
        // target:目标对象、method:目标方法、params:方法参数数组
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName()).append("."); // 拼接类全限定名
            sb.append(method.getName()).append("."); // 拼接方法名，并加一个点
            for (Object obj : params) { // 遍历所有方法参数
                sb.append(obj);
            }
            // 获取最终缓存Key并返回
            return sb.toString();
        };
    }

    /**
     * 注入缓存解析器
     */
    @Bean // 注册为 Spring Bean
    public CacheResolver cacheResolver(CacheManager cacheManager) {
        return new SimpleCacheResolver(cacheManager);
    }

    /**
     * 自定义缓存异常处理器
     */
    @Bean
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            /**
             * 处理缓存获取异常
             *
             * @param exception 缓存获取异常
             * @param cache     缓存
             * @param key       缓存Key
             */
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                // 查询缓存时发生异常、记录异常信息避免继续向上抛出
                log.error("缓存获取异常 异常Key名称 {}, 异常信息 {}", key, exception.getMessage());
            }

            /**
             * 处理缓存写入异常
             *
             * @param exception 缓存写入异常
             * @param cache     缓存
             * @param key       缓存Key
             * @param value     缓存值
             */
            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                // 写入缓存时发生异常、记录异常信息避免继续向上抛出
                log.error("缓存写入异常 异常Key名称 {}, 异常信息 {}", key, exception.getMessage());
            }

            /**
             * 处理缓存删除异常
             *
             * @param exception 缓存删除异常
             * @param cache     缓存
             * @param key       缓存Key
             */
            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                // 删除缓存时发生异常、记录异常信息避免继续向上抛出
                log.error("缓存删除异常 异常Key名称 {}, 异常信息 {}", key, exception.getMessage());
            }

            /**
             * 处理缓存清空异常
             *
             * @param exception 缓存清空异常
             * @param cache     缓存
             */
            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                // 清空缓存时发生异常、记录异常信息避免继续向上抛出
                log.error("缓存清空异常 异常信息 {}", exception.getMessage());
            }
        };
    }

    /**
     * Redis缓存管理器
     * 声明当前项目使用SpringCache相关注解的时候操作的数据源
     * 📌 使用SpringCache操作Redis的时候,底层使用的RedisTemplate的模板默认不走RedisConfiguration声明的序列化方式,默认情况下也会出现Key不可读的问题
     * 可以在方法中声明基于SpringCache操作Redis的时候默认序列化器,避免出现不可读的问题
     */
    @Bean
    public CacheManager cacheManager() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.EVERYTHING
        );
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(cacheName -> cacheName + ":") // 修改为单:拼接
                .disableCachingNullValues() // 关闭null值缓存
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // 使用StringRedisSerializer序列化缓存Key
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))); // 使用GenericJackson2JsonRedisSerializer序列化缓存值
        return RedisCacheManager.builder(factory)
                .cacheDefaults(cacheConfiguration)
                .build();
    }
}