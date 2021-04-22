package com.lz.core.test.cache;

import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.annotation.DeleteCache;
import com.lz.core.cache.common.annotation.PutCache;
import org.springframework.stereotype.Service;

/**
 * @author luyi
 */
@Service
public class CacheService {

    @Cacheable(suffix = "#id#xxx")
    public String getXxx(String id, String xxx) {
        System.out.println("查询数据库");
        return "xxx" + id;
    }

    //@PutCache(spElSuffix = "#user.name + '_' +#user.age")
    @PutCache(suffix = "#user.class.name")
    public User updateUser(User user) {
        System.out.println(user.getClass().getName());
        return user;
    }

    @DeleteCache
    public void delete(String id) {
        System.out.println("删除：" + id);
    }

    @PutCache
    public String put(String id) {
        return "xxx" + id;
    }
}
