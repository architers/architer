package com.architecture.context.lock;

import com.architecture.context.expression.ExpressionParser;
import com.architecture.context.expression.ExpressionMetadata;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 锁的key的解析器
 */
public class LockFactory {

    private ExpressionParser expressionParser;
    private Map<LockEnum, LockService> lockServiceMap;


    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }

    public LockFactory setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
        return this;
    }

    public Map<LockEnum, LockService> getLockServiceMap() {
        return lockServiceMap;
    }

    public LockFactory setLockServiceMap(Map<LockEnum, LockService> lockServiceMap) {
        this.lockServiceMap = lockServiceMap;
        return this;
    }

    public Lock get(Locked locked, ExpressionMetadata expressionMetadata) throws Exception {
        if (locked == null || LockEnum.NONE.equals(locked.lock())) {
            return null;
        }
        String lockName = (String) expressionParser.parserExpression(expressionMetadata, locked.key());
        LockService lockService = lockServiceMap.get(locked.lock());
        LockType lockType = locked.lockType();
        if (LockType.READ.equals(lockType)) {
            return lockService.tryReadLock(lockName);
        } else if (LockType.WRITE.equals(lockType)) {
            return lockService.tryWriteLock(lockName);
        } else if (LockType.REENTRANT_FAIR.equals(lockType)) {
            return lockService.tryFairLock(lockName);
        } else if (LockType.REENTRANT_UNFAIR.equals(lockType)) {
            return lockService.tryUnfairLock(lockName);
        } else {
            return LockService.FAIL_LOCK;
        }
    }
}
