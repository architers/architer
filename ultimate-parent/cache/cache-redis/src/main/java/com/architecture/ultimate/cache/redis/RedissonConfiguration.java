package com.architecture.ultimate.cache.redis;


import com.architecture.ultimate.cache.lock.LockManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * redisson配置类
 * 只有customize.redisson.enabled=true才创建
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(prefix = "customize.redisson", name = "enabled", havingValue = "true")
public class RedissonConfiguration {
    /**
     * 描述:配置redisson
     *
     * @author luyi
     * @date 2020/12/25 下午11:45
     */
    @Bean
    public RedissonClient redissonClient(RedissonProperties redissonProperties) {
        return Redisson.create(redissonProperties.getConfig());
    }

    @Bean
    public LockManager lockService(RedissonClient redissonClient) {
        return new DistributedLockManager(redissonClient);
    }
}
