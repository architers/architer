package com.lz.core.cache.common;

/**
 * 描述:锁的方式
 *
 * @author luyi
 * @date 2020/12/26 下午5:38
 */
public enum Lock {
    /**
     *重入锁
     * 读锁
     * 写锁
     * @TODO 后续增加锁
     *
     */
    none("none"),
    reentrant("reentrantLock"),
    read("readLock"),
    write("writeLock");


    private String lockName;

     Lock(String lockName) {
        this.lockName = lockName;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }
}
