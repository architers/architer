package com.lz.core.cache.common.operation;


import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.utils.CacheUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;


/**
 * CacheableOperation 对应的处理类
 *
 * @author luyi
 */
public class CacheableOperationHandler extends CacheOperationHandler {


    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof Cacheable;
    }

    @Override
    protected Object executeCacheHandler(String key, CacheOperationMetadata metadata) {
        Object value = cacheManager.getCache(key);
        if (value == null) {
            value = invoke(metadata);
            CacheableOperation cacheableOperation = (CacheableOperation) metadata.getCacheOperation();
            String cacheValue = cacheableOperation.getCacheValue();
            long expireTime = CacheUtils.getExpireTime(cacheableOperation.getExpireTime(), cacheableOperation.getRandomExpireTime());
            if (StringUtils.isEmpty(cacheValue)) {
                cacheManager.putCache(key, value, expireTime);
            } else {
                Object needCacheValue = cacheExpressionParser.executeParse(metadata, cacheValue);
                cacheManager.putCache(key, needCacheValue, expireTime);
            }
        }
        return value;
    }

}
