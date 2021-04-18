package com.lz.core.cache.common.annotation;

import com.lz.core.cache.common.enums.KeyStrategy;
import com.lz.core.cache.common.enums.LockType;

import java.lang.annotation.*;

/**
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
    String cacheName() default "";

    /**
     * @see Cacheable#cachePrefix()
     */
    String cachePrefix() default "";

    /**
     * @see Cacheable#lock()
     */
    LockType lock() default LockType.none;

    /**
     * @see Cacheable#async() ()
     */
    boolean async() default false;

    KeyStrategy keyStrategy() default KeyStrategy.NONE;
}
