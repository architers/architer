package com.architecture.context.cache.operation;


import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.context.lock.Locked;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * @see com.architecture.context.cache.annotation.PutCache
 */
@Data
public class PutCacheOperation extends CacheOperation {

    /**
     * @see Cacheable#randomTime()
     */
    private long randomTime;

    /**
     * @see Cacheable#randomTimeUnit()
     */
    private TimeUnit randomTimeUnit;

    /**
     * @see Cacheable#expireTime()
     */
    private long expireTime;

    /**
     * @see Cacheable#expireTimeUnit()
     */
    private TimeUnit expireTimeUnit;
    /**
     * 缓存值
     *
     * @see Cacheable#cacheValue()
     */
    private String cacheValue;
}
