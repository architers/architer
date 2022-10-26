package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.*;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.expression.ExpressionParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.util.Map;


/**
 * @author luyi
 * 缓存operation处理基类
 * <li>对于实现类order排序,按照操作的频率排好序，增加程序效率：比如缓存读多，就把cacheable对应的处理器放最前边</li>
 */
public abstract class CacheOperationHandler implements ApplicationContextAware {


    private Map<Class<? extends CacheOperate>, CacheOperate> cacheManagerMap;
    @Autowired(required = false)
    protected ExpressionParser expressionParser;

    protected CacheKeyWrapper cacheKeyWrapper;

    private CacheOperate defaultCacheOperate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, CacheOperate> beanNameCacheManagerMap =
                applicationContext.getBeansOfType(CacheOperate.class);
        cacheManagerMap = CollectionUtils.newHashMap(beanNameCacheManagerMap.size());
        beanNameCacheManagerMap.forEach((key, value) -> cacheManagerMap.put(value.getClass(), value));
    }

    public Object value(String valueExpression, ExpressionMetadata expressionMetadata) {
        return expressionParser.parserExpression(expressionMetadata, valueExpression);
    }

    /**
     * 选择缓存
     */
    public CacheOperate chooseCacheOperate(Class<? extends CacheOperate> clazz) {

        CacheOperate cacheOperate = null;
        if (clazz.equals(DefaultCacheOperate.class)) {
            //默认的缓存操作器
            cacheOperate = defaultCacheOperate;
        }
        if (cacheOperate == null) {
            cacheOperate = cacheManagerMap.get(clazz);
        }
        if (cacheOperate == null) {
            throw new IllegalArgumentException("cacheManger is null");
        }
        return cacheOperate;
    }

    /**
     * operation是否匹配
     *
     * @param operationAnnotation operation对应的操作缓存的注解
     * @return 是否匹配，如果true就对这个operation的进行缓存处理
     */
    public abstract boolean match(Annotation operationAnnotation);

    protected String parseCacheKey(ExpressionMetadata expressionMetadata, String key) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("key is empty");
        }
        if (CacheConstants.BATCH_CACHE_KEY.equals(key)) {
            return key;
        }
        Object cacheKey = expressionParser.parserExpression(expressionMetadata, key);
        if (cacheKey == null) {
            throw new RuntimeException("cacheKey 为空");
        }
        return cacheKeyWrapper.getCacheKey(null, cacheKey.toString());
    }

    /**
     * 处理缓存operation
     *
     * @param methodReturnValueFunction
     * @param expressionMetadata
     * @throws Throwable
     */
    public void handler(Annotation operationAnnotation,
                        MethodReturnValueFunction methodReturnValueFunction,
                        ExpressionMetadata expressionMetadata) throws Throwable {

        this.execute(operationAnnotation, expressionMetadata, methodReturnValueFunction);
        // }
    }

    /**
     * condition是否为true
     */
    public boolean isCondition(String condition, ExpressionMetadata expressionMetadata) {
        //条件满足才缓存
        if (StringUtils.hasText(condition)) {
            if (isContainsResult(condition)) {
                return true;
            }
            Object isCondition = expressionParser.parserExpression(expressionMetadata, condition);
            if (!(isCondition instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("condition[{0}]有误,必须为Boolean类型", condition));
            }
            return (boolean) isCondition;
        }
        return true;
    }


    /**
     * 是否unless
     * 返回为true说明就不能进行缓存操作
     */
    public boolean isUnless(String unless, ExpressionMetadata expressionMetadata) {
        if (StringUtils.hasText(unless)) {
            Object isUnless = expressionParser.parserExpression(expressionMetadata, unless);
            if (!(isUnless instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("unless[{0}]有误,必须为Boolean类型", unless));
            }
            return (boolean) isUnless;
        }
        return true;
    }





    private boolean isContainsResult(String expression) {
        return expression.contains("#result");
    }

    /**
     * 执行缓存处理
     *
     * @param operationAnnotation       缓存注解
     * @param expressionMetadata        表达式元数据
     * @param methodReturnValueFunction 返回值功能函数
     * @throws Throwable
     */
    protected abstract void execute(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable;


    public CacheOperationHandler setDefaultCacheManager(CacheOperate defaultCacheOperate) {
        this.defaultCacheOperate = defaultCacheOperate;
        return this;
    }

    public CacheOperationHandler setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
        return this;
    }


}
