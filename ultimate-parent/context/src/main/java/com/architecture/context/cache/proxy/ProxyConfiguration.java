package com.architecture.context.cache.proxy;


import com.architecture.context.cache.CacheAnnotationsParser;
import com.architecture.context.cache.operation.CacheOperationHandler;
import com.architecture.context.expression.ExpressionParser;
import com.architecture.context.lock.LockFactory;
import com.architecture.context.lock.LockService;
import org.springframework.beans.factory.config.BeanDefinition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.List;

/**
 * @author luyi
 **/
@Configuration
public class ProxyConfiguration {


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAnnotationsParser cacheAnnotationsParser() {
        return new CacheAnnotationsParser();
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AnnotationCacheOperationSource annotationCacheOperationSource() {
        return new AnnotationCacheOperationSource(cacheAnnotationsParser());
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor cacheInterceptor(List<CacheOperationHandler> cacheOperationHandlers, CacheAnnotationsParser cacheAnnotationsParser) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        cacheInterceptor.setCacheOperationHandlers(cacheOperationHandlers);
        cacheInterceptor.setCacheAnnotationsParser(cacheAnnotationsParser);
        return cacheInterceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryCacheSourceAdvisor beanFactoryCacheSourceAdvisor(AnnotationCacheOperationSource annotationCacheOperationSource, CacheInterceptor cacheInterceptor) {
        BeanFactoryCacheSourceAdvisor advisor = new BeanFactoryCacheSourceAdvisor();
        advisor.setCacheOperationSource(annotationCacheOperationSource);
        advisor.setAdvice(cacheInterceptor);
        return advisor;
    }
}
