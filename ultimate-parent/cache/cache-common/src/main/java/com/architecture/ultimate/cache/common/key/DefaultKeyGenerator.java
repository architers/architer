package com.architecture.ultimate.cache.common.key;



import com.architecture.ultimate.cache.common.CacheExpressionParser;
import com.architecture.ultimate.cache.common.exception.CacheAnnotationIllegalException;
import com.architecture.ultimate.cache.common.operation.CacheOperation;
import com.architecture.ultimate.cache.common.operation.CacheOperationMetadata;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author luyi
 * 默认缓存key 生成器
 */
public class DefaultKeyGenerator implements KeyGenerator {

    private final CacheExpressionParser cacheExpressionParser;

    /**
     * 前后缀的分割符号
     */
    private String separator = "::";

    public DefaultKeyGenerator(CacheExpressionParser cacheExpressionParser) {
        this.cacheExpressionParser = cacheExpressionParser;
    }

    @Override
    public String getKey(CacheOperationMetadata metadata) {
        CacheOperation cacheOperation = metadata.getCacheOperation();
        String cacheName = cacheOperation.getCacheName();
        String cacheKey = cacheOperation.getKey();
        if (StringUtils.isEmpty(cacheKey)) {
            throw new CacheAnnotationIllegalException("cacheKey is null");
        }
        String cacheSuffix = Objects.requireNonNull(cacheExpressionParser.executeParse(metadata, cacheKey)).toString();
        if (StringUtils.isEmpty(cacheName)) {
            return cacheSuffix;
        } else {
            String cachePrefix = Objects.requireNonNull(cacheExpressionParser.executeParse(metadata, cacheName)).toString();
            return cachePrefix + getSeparator() + cacheSuffix;
        }
    }


    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
