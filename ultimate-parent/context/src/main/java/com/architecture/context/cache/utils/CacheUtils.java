package com.architecture.context.cache.utils;


import com.architecture.context.cache.CacheConstants;
import com.architecture.context.cache.operation.CacheOperation;

/**
 * @author luyi
 * key过期工具类
 */
public class CacheUtils {


    public static long getExpireTime(long expireTime, long randomExpireTime) {
        if (CacheConstants.NEVER_EXPIRE == expireTime) {
            return CacheConstants.NEVER_EXPIRE;
        }
        if (expireTime < 0) {
            throw new IllegalArgumentException("expireTime is Illegal");
        }
        if (randomExpireTime > 0) {
            return expireTime + (long) (Math.random() * randomExpireTime);
        }
        return expireTime;
    }


}
