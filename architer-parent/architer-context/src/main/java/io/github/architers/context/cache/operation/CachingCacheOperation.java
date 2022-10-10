package io.github.architers.context.cache.operation;


/**
 * @author luyi
 * 多个缓存注解操作
 */
public class CachingCacheOperation extends BaseCacheOperation {
    private BaseCacheOperation[] operations;

    public BaseCacheOperation[] getOperations() {
        return operations;
    }

    public void setOperations(BaseCacheOperation[] operations) {
        this.operations = operations;
    }
}
