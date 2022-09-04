package io.github.architers.lock.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * redisson分布式锁自动配置
 *
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(RedissonLockPropeties.class)
@Slf4j
public class RedissonLockAutoConfiguration {

    @Resource
    private ApplicationContext ctx;

    private final RedissonLockPropeties redissonLockPropeties;

    RedissonLockAutoConfiguration(RedissonLockPropeties redissonLockPropeties) {
        this.redissonLockPropeties = redissonLockPropeties;
    }

    @Bean(destroyMethod = "shutdown", value = "redissonLockClient")
    @ConditionalOnMissingBean(name = "redissonLockClient")
    @ConditionalOnProperty(prefix = "architer.lock.redisson", name = "isolation", havingValue = "true", matchIfMissing = true)
    public RedissonClient redissonLockClient() throws IOException {
        Config config;
        if (redissonLockPropeties == null) {
            throw new IllegalArgumentException("redisson锁配置缺失");
        }
        String file = redissonLockPropeties.getFile();
        if (StringUtils.hasText(file)) {
            InputStream is = this.getConfigStream();
            config = Config.fromYAML(is);
        } else if (redissonLockPropeties.getConfig() != null) {
            config = redissonLockPropeties.getConfig();
        } else {
            //默认连接本地
            config = new Config();
            config.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379");
            config.setCodec(new JsonJacksonCodec());
            log.info("没有配置redisson锁配置，使用默认连接");
        }
        return Redisson.create(config);
    }

    private InputStream getConfigStream() throws IOException {
        org.springframework.core.io.Resource resource = this.ctx.getResource(this.redissonLockPropeties.getFile());
        return resource.getInputStream();
    }

    /**
     * 使用redissonLockClient这个beanName的客户端（锁隔离）
     */
    @ConditionalOnProperty(prefix = "architer.lock.redisson", name = "isolation", havingValue = "true", matchIfMissing = true)
    @ConditionalOnBean(name = "redissonLockClient")
    @Bean
    public RedissonLockServiceImpl redissonLockServiceIsolation(@Qualifier("redissonLockClient") RedissonClient redissonClient) {
        log.info("redissonLock是否隔离：true");
        return new RedissonLockServiceImpl(redissonClient);
    }


    /**
     * 锁没有隔离的客户端（代表系统通过redissonClient）
     */
    @ConditionalOnProperty(prefix = "architer.lock.redisson", name = "isolation", havingValue = "false")
    @Bean
    public RedissonLockServiceImpl redissonLockServiceNotIsolation(RedissonClient redissonClient) {
        log.info("redissonLock是否隔离：false");
        return new RedissonLockServiceImpl(redissonClient);
    }


}
