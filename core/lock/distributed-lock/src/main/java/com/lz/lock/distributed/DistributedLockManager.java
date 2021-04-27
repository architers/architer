package com.lz.lock.distributed;

import com.lz.lock.LockManager;
import org.redisson.api.RedissonClient;

import java.util.concurrent.locks.Lock;

/**
 * 分布式锁
 *
 * @author luyi
 */
public class DistributedLockManager implements LockManager {
    private RedissonClient redissonClient;

    public DistributedLockManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Lock getReadLock(String lock) {
        return redissonClient.getReadWriteLock(lock).readLock();
    }

    @Override
    public Lock getWriteLock(String lock) {
        return redissonClient.getReadWriteLock(lock).writeLock();
    }

    @Override
    public Lock getReentrantLock(String lock) {
        return redissonClient.getLock(lock);
    }
}
