package com.architecture.ultimate.cache.redis.service;

import com.architecture.ultimate.cache.common.annotation.Cacheable;
import com.architecture.ultimate.cache.common.annotation.DeleteCache;
import com.architecture.ultimate.cache.common.annotation.PutCache;
import com.architecture.ultimate.cache.redis.entity.User;
import com.architecture.ultimate.cache.common.enums.LockType;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private static final String userClassName = User.class.getName().intern();

    private final AtomicLong find_by_id_count = new AtomicLong(0);

    //@Cacheable(prefix = "'test'", suffix = "#id",lock = LockType.reentrant)
    //@Cacheable(prefix = "#root.method.returnType.name", suffix = "#id", lock = LockType.reentrant, randomExpireTime = 60 * 60 * 24)
    //@Cacheable(prefix = "#root.method.returnType.name", suffix = "#id", lock = LockType.reentrant, expireTime = 60)
    @Override
    public User findById(@NonNull Long id) {
        User user = new User("name" + id, 666);
        log.info("findById开始查询数据库({})次", find_by_id_count.incrementAndGet());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    @PutCache(key = "#user.class.name + '::' + #user.name", lock = LockType.write)
    public User update(User user) {
        System.out.println("save ..");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Cacheable(cacheName = "#root.method.returnType.name", key = "#name", lock = LockType.read)
    @Override
    public User findByName(String name) {
        System.out.println("read:" + name);
        return new User(name, 55);
    }

    //@DeleteCache(prefix = "#root.getVariable('userClassName')", suffix = "#name")
    @DeleteCache(cacheName = "#root.getMethod('findByName',T(java.lang.String)).returnType.name", key = "#name")
    @Override
    public void deleteByName(String name) {
        System.out.println("delete:" + name);
    }

    @Override
    public void updateForCacheValue(User user) {
        System.out.println("更新数据");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getUserClassName() {
        return userClassName;
    }
}
