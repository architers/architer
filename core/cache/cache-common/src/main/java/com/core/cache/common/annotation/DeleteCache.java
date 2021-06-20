package com.core.cache.common.annotation;


import com.core.cache.common.enums.LockType;

import java.lang.annotation.*;

/**
 * 删除缓存
 *
 * @author luyi
 * @date 2020/12/26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DeleteCache {
    /**
     * @see Cacheable#cacheName()
     */
    String cacheName() default "";

    /**
     * @see Cacheable#key()
     */
    String key();

    /**
     * 锁的类型，比如删除锁的时候是否允许查询接口读取数据
     *
     * @see Cacheable#lock()
     */
    LockType lock() default LockType.none;

    /**
     * 是否异步
     */
    boolean async() default false;

    /**
     * true表示在方法直接删除缓存
     * false 表示在方法执行之后删除
     */
    boolean before() default true;

    /**
     * @see Cacheable#condition()
     */
    String condition() default "";

    /**
     * @see Cacheable#unless()
     */
    String unless() default "";

}
