package com.architecture.context.cache.operation;


import com.architecture.context.cache.CacheAsyncExecutorService;
import com.architecture.context.cache.CacheExpressionParser;


import com.architecture.context.cache.exception.CacheHandlerException;
import com.architecture.context.cache.key.KeyGenerator;
import com.architecture.context.lock.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import com.architecture.context.cache.CacheService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 缓存operation
 */
public abstract class CacheOperationHandler {

    /**
     * 缓存manager,定义protected，让实现类也可以直接使用
     */
    protected CacheService cacheService;

    private KeyGenerator keyGenerator;

    private LockService lockService;

    protected CacheExpressionParser cacheExpressionParser;

    protected CacheAsyncExecutorService cacheAsyncExecutorService;

    public CacheOperationHandler() {

    }

    /**
     * operation是否匹配
     *
     * @param operationAnnotation operation对应的缓存注解
     * @return 是否匹配，如果true就对这个operation的进行缓存处理
     */
    public abstract boolean match(Annotation operationAnnotation);

    /**
     * 开始处理
     *
     * @return 处理后的结果值，这个值就是缓存注解方法对应的返回值
     */
    public final Object handler(CacheOperationMetadata metadata) {
        String[] keys = keyGenerator.getKey(metadata);
        Lock lock = this.getLock(metadata);
        if (lock == null) {
            return executeCacheHandler(keys, metadata);
        }
        lock.lock();
        try {
            return executeCacheHandler(keys, metadata);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 执行缓存处理器
     *
     * @param key      缓存的key
     * @param metadata 缓存操作元数据
     * @return 调用方法的返回值
     */
    protected abstract Object executeCacheHandler(String[] key, CacheOperationMetadata metadata);

    /**
     * 执行缓存写操作
     */
    public void writeCache(boolean async, CacheWriteExecute cacheWriteExecute) {
        if (async) {
            if (cacheAsyncExecutorService == null) {
                throw new CacheHandlerException("cacheAsyncExecutorService is null");
            }
            cacheAsyncExecutorService.submit(cacheWriteExecute::write);
        } else {
            cacheWriteExecute.write();
        }
    }

    /**
     * 通过缓存注解得到对应的锁
     */
    protected Lock getLock(CacheOperationMetadata metadata) {
//        if (LockType.none == metadata.getCacheOperation().getLockType()) {
//            return null;
//        }
//        String lock = metadata.getCacheOperation().getLock();
//        if (StringUtils.isEmpty(lock)) {
//            lock = metadata.getTarget().getClass() + "." + metadata.getTargetMethod().getName();
//        }
//        String lockName = "lock." + lock;
//        switch (metadata.getCacheOperation().getLockType()) {
//            case read:
//                return lockManager.getReadLock(lockName);
//            case write:
//                return lockManager.getWriteLock(lockName);
//            case reentrant:
//                return lockManager.getReentrantLock(lockName);
//            default:
//                throw new IllegalArgumentException("lock not match");
//        }
        return null;
    }

    public Object invoke(CacheOperationMetadata metadata) {
        return invoke(metadata.getTarget(), metadata.getTargetMethod(), metadata.getArgs());
    }

    /**
     * 反射invoke,得到值
     */
    public Object invoke(Object target, Method method, Object[] args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("操作失败", e);
        }
    }

    @Autowired
    public CacheOperationHandler setCacheManager(CacheService cacheService) {
        this.cacheService = cacheService;
        return this;
    }

    @Autowired(required = false)
    public CacheOperationHandler setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
        return this;
    }

    @Autowired
    public void setLockService(LockService lockService) {
        this.lockService = lockService;
    }

    @Autowired(required = false)
    public void setCacheExpressionParser(CacheExpressionParser cacheExpressionParser) {
        this.cacheExpressionParser = cacheExpressionParser;
    }

    @Autowired(required = false)
    public void setCacheAsyncExecutorService(CacheAsyncExecutorService cacheAsyncExecutorService) {
        this.cacheAsyncExecutorService = cacheAsyncExecutorService;
    }
}
