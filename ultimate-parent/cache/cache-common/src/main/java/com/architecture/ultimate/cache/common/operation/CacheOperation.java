package com.architecture.ultimate.cache.common.operation;


import com.architecture.ultimate.cache.common.enums.LockType;
import lombok.Data;

import java.lang.annotation.Annotation;


/**
 * @author luyi
 * 缓存操作
 * @see org.springframework.cache.interceptor.CacheOperation 参考的这个类
 */
@Data
public class CacheOperation {
    /**
     * 名称（注解的名称）
     */
    private String name;
    /**
     * 操作的注解
     */
    private Annotation annotation;
    /**
     * @see Cacheable#cacheName()
     */
    private String cacheName;

    private LockType lock;
    /**
     * @see Cacheable#key()
     */
    private String key;
    /**
     * @see Cacheable#async()
     */
    private boolean async;


}
