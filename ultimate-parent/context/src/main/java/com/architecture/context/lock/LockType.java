package com.architecture.context.lock;

/**
 * 锁的类型
 *
 * @author luyi
 */
public enum LockType {
    /**
     * 没有锁
     */
    NONE,
    /**
     * 读
     */
    READ,
    /**
     * 写
     */
    WRITE,
    /**
     * 重入
     */
    REENTRANT

}
