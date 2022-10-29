package io.github.architers.context.cache;

import io.github.architers.context.cache.operation.CacheOperate;
import io.github.architers.context.lock.LockEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 默认的缓存属性配置
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "architers.cache")
@Data
public class CacheProperties {

    /**
     * 默认的处理器
     */
    private Class<? extends CacheOperate> defaultCacheOperateClass;


    /**
     * 使用什么锁
     */
    private LockEnum lockEnum = LockEnum.JDK;

    public LockEnum getLock() {
        return lockEnum;
    }

    public void setLock(LockEnum lockEnum) {
        this.lockEnum = lockEnum;
    }

}
