package com.architecture.context.cache.proxy;


import com.architecture.context.cache.CacheAnnotationsParser;
import com.architecture.context.cache.model.InvalidCache;
import com.architecture.context.cache.operation.BaseCacheOperation;
import com.architecture.context.cache.operation.CacheOperationHandler;
import com.architecture.context.expression.ExpressionEvaluationContext;
import com.architecture.context.expression.ExpressionMetadata;
import com.architecture.context.expression.ExpressionParser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author luyi
 * 缓存拦截器
 */
public class CacheInterceptor implements MethodInterceptor {

    private CacheAnnotationsParser cacheAnnotationsParser;

    private List<CacheOperationHandler> cacheOperationHandlers;


    @Override
    @Nullable
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Collection<BaseCacheOperation> baseCacheOperations = cacheAnnotationsParser.parse(invocation.getMethod());
        if (!CollectionUtils.isEmpty(baseCacheOperations)) {
            if (baseCacheOperations.size() > 1) {
                //当多余一个注解的时候，排序，让cacheable操作在最前边
                baseCacheOperations = baseCacheOperations.stream().sorted(Comparator.comparing(BaseCacheOperation::getOrder)).collect(Collectors.toList());
            }
            return execute(invocation, baseCacheOperations);
        }
        return invocation.proceed();
    }


    /**
     * 执行拦截的操作
     */
    private Object execute(MethodInvocation invocation, Collection<BaseCacheOperation> baseCacheOperations) throws Throwable {
        ExpressionMetadata expressionMetadata = new ExpressionMetadata(Objects.requireNonNull(invocation.getThis()), invocation.getMethod(), invocation.getArguments());
        ExpressionEvaluationContext expressionEvaluationContext = ExpressionParser.createEvaluationContext(expressionMetadata);
        expressionMetadata.setEvaluationContext(expressionEvaluationContext);
        AtomicReference<Object> returnValue = new AtomicReference<>();
        MethodReturnValueFunction methodReturnValueFunction = new MethodReturnValueFunction() {
            @Override
            public Object proceed() throws Throwable {
                synchronized (this) {
                    if (returnValue.get() == null) {
                        Object value = invocation.proceed();
                        if (value == null) {
                            value = InvalidCache.INVALID_CACHE;
                        }
                        setValue(value);
                    }
                    return returnValue.get();
                }
            }

            @Override
            public void setValue(Object value) {
                synchronized (this) {
                    if (value != null && !(value instanceof InvalidCache)) {
                        expressionEvaluationContext.setVariable("result", value);
                    }
                    if (value != null) {
                        returnValue.set(value);
                    }
                }
            }
        };
        for (BaseCacheOperation baseCacheOperation : baseCacheOperations) {
            for (CacheOperationHandler cacheOperationHandler : cacheOperationHandlers) {
                if (cacheOperationHandler.match(baseCacheOperation)) {
                    cacheOperationHandler.handler(baseCacheOperation, methodReturnValueFunction, expressionMetadata);
                    continue;
                }
            }
        }
        Object value = returnValue.get();
        if (value == null) {
            return invocation.proceed();
        }
        if (value instanceof InvalidCache) {
            return null;
        }
        return value;

    }


    public void setCacheAnnotationsParser(CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheAnnotationsParser = cacheAnnotationsParser;
    }

    public CacheAnnotationsParser getCacheAnnotationsParser() {
        return cacheAnnotationsParser;
    }

    public List<CacheOperationHandler> getCacheOperationHandlers() {
        return cacheOperationHandlers;
    }

    public CacheInterceptor setCacheOperationHandlers(List<CacheOperationHandler> cacheOperationHandlers) {
        this.cacheOperationHandlers = cacheOperationHandlers;
        return this;
    }
}
