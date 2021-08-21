package com.architectrue.lock.zk;


import com.architecture.context.lock.LockService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * Zk分布式锁
 * <li>zk只支持公平锁</li>
 */
@Service("zkLock")
public class ZkLockServiceImpl implements LockService {
    private CuratorFramework client;
    private static final String ZK_LOCK_START = "/";

    @Override
    public Lock tryFairLock(String lockName) throws Exception {
        return tryFairLock(lockName, -1, null);
    }

    @Override
    public Lock tryFairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        lockName = getPathLock(lockName);
        InterProcessMutex mutex = new InterProcessMutex(client, lockName);
        if (mutex.acquire(time, timeUnit)) {
            return new ZookeeperLock(mutex);
        }
        return null;
    }

    @Override
    public Lock tryUnfairLock(String lockName) throws Exception {
        throw new Exception("不支持");
    }

    @Override
    public Lock tryUnfairLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        throw new Exception("不支持");
    }

    @Override
    public Lock getWriteLock(String lockName) throws Exception {
        return getWriteLock(lockName, -1, null);
    }

    @Override
    public Lock getWriteLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        lockName = getPathLock(lockName);
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, lockName);
        InterProcessMutex mutex = interProcessReadWriteLock.writeLock();
        boolean acquire = mutex.acquire(time, timeUnit);
        if (acquire) {
            return new ZookeeperLock(mutex);
        }
        return null;
    }

    @Override
    public Lock getReadLock(String lockName) throws Exception {
        return getReadLock(lockName, -1, null);
    }

    @Override
    public Lock getReadLock(String lockName, long time, TimeUnit timeUnit) throws Exception {
        lockName = getPathLock(lockName);
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(client, lockName);
        InterProcessMutex mutex = interProcessReadWriteLock.readLock();
        boolean acquire = mutex.acquire(time, timeUnit);
        if (acquire) {
            return new ZookeeperLock(mutex);
        }
        return null;
    }

    private String getPathLock(String lockName) {
        Assert.hasText(lockName, "lock is null");
        if (!lockName.startsWith(ZK_LOCK_START)) {
            lockName = ZK_LOCK_START + lockName;
        }
        return lockName;
    }

    @Autowired
    public void setClient(CuratorFramework client) {
        this.client = client;
    }
}
