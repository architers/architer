package com.architecture.context.cache.annotation;


import com.architecture.context.cache.CacheConstants;
import com.architecture.context.lock.LockType;

import java.lang.annotation.*;

/**
 * 向缓存中放数据
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PutCache {

    /**
     * @see Cacheable#cacheName()
     */
    String[] cacheName() default "";

    /**
     * @see Cacheable#key()
     */
    String key();

    /**
     * @see Cacheable#randomExpireTime()
     */
    long randomExpireTime() default -1;

    /**
     * @see Cacheable#expireTime()
     */
    long expireTime() default CacheConstants.DEFAULT_CACHE_EXPIRE_TIME;

    /**
     * @see Cacheable#lockType()
     */
    LockType lockType() default LockType.NONE;

    /**
     * @see Cacheable#lock()
     */
    String lock() default "";

    /**
     * @see Cacheable#async()
     */
    boolean async() default false;

    /**
     * @see Cacheable#cacheValue()
     */
    String cacheValue() default "";

    /**
     * @see Cacheable#condition()
     */
    String condition() default "";

    /**
     * @see Cacheable#unless()
     */
    String unless() default "";
}
