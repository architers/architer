package com.architecture.context.cache.operation;


import com.architecture.context.cache.proxy.MethodReturnValueFunction;
import com.architecture.context.cache.utils.CacheUtils;
import com.architecture.context.expression.ExpressionMetadata;

import java.util.List;

/**
 * 对应PutCacheOperation
 *
 * @author luyi
 */
public class PutCacheOperationHandler extends CacheOperationHandler {

    private static final int SECOND_ORDER = 2;

    @Override
    public boolean match(BaseCacheOperation operation) {
        return operation instanceof PutCacheOperation;
    }

    @Override
    protected void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        PutCacheOperation putCacheOperation = (PutCacheOperation) operation;
        List<String> cacheKeys = getCacheKeys(operation, expressionMetadata);
        long expireTime = CacheUtils.getExpireTime(putCacheOperation.getExpireTime(), putCacheOperation.getRandomTime());
        String cacheValue = putCacheOperation.getCacheValue();
        methodReturnValueFunction.proceed();
        Object value = expressionParser.parserExpression(expressionMetadata, cacheValue);
        if (this.canHandler(operation, expressionMetadata, false)) {
            for (String cacheKey : cacheKeys) {
                cacheService.set(cacheKey, value, expireTime, putCacheOperation.getExpireTimeUnit());
            }
        }
    }

    @Override
    public int getOrder() {
        return SECOND_ORDER;
    }
}
