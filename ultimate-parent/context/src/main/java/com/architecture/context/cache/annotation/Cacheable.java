package com.architecture.context.cache.annotation;


import com.architecture.context.cache.CacheConstants;
import com.architecture.context.lock.Locked;
import com.architecture.context.lock.LockEnum;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * 功能：缓存数据，缓存中有的时候，就从缓存中取值，没有就将换回结果放入缓存中
 * ------------------------------------缓存的中key规则--------------------------
 * <br/>
 * 1.所有的时间单位都是秒
 * 2.缓存的中的key的取值为：
 * <li> a.如果cacheName为空就为key的取值</li>
 * <li>b.如果cacheName不为空，就为cacheName+缓存分隔符+key的取值</li>
 * -----------------------------------取默认配置值---------------------------
 * 当值为CacheConstants.DEFAULT_CONFIG_TIME的时候，会根据默认的配置取值，用户可以配置全局值
 * ------------------------------------------------------------------
 *
 * @author luyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cacheable {

    /**
     * 缓存名称,
     * <li>支持EL表达式，解析后会缓存起来，下次直接取值</li>
     */
    String[] cacheName() default "";

    /**
     * 缓存key,支持SpEL
     */
    String key();

    /**
     * 缓存值,默认为方法返回值，支持spEL表达式
     */
    String cacheValue() default "";

    /**
     * 缓存随机失效时间
     * ps:主要用户解决缓存雪崩，同一时刻大量缓存数据失效，大量请求到达数据库
     */
    long randomExpireTime() default CacheConstants.DEFAULT_CONFIG_TIME;

    /**
     * 缓存失效时间
     */
    long expireTime() default CacheConstants.DEFAULT_CONFIG_TIME;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * 默认没有锁
     */
    Locked locked() default @Locked(lock = LockEnum.NONE, key = "");

    /**
     * 是否异步
     */
    boolean async() default false;

    /**
     * 条件满足的时候，进行缓存操作
     */
    String condition() default "";

    /**
     * 条件满足的时候，不进行缓存操作
     */
    String unless() default "";

}
