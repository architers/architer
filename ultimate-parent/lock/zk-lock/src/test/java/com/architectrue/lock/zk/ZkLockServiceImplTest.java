package com.architectrue.lock.zk;

import com.architecture.lock.common.LockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZkLockServiceImplTest {

    @Resource(name = LockService.ZK_LOCK_BEAN)
    private LockService lockService;

    private static Long code = 0L;

    /**
     * 多线程不能保证可见性code，所以code的会小于<=200
     */
    @Test
    public void test() throws InterruptedException {
        int count = 200;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++) {
            new Thread(() -> {
                code = code + 1;
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(code);
        Assertions.assertTrue(code < 200);
    }

    @Test
    public void testLock() throws Exception {
        int count = 200;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 1; i <= count; i++) {
            new Thread(() -> {
                try {
                    addLockCode();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(code);
        assertEquals(200, (long) code);

    }


    /**
     * 减少库存
     */
    public void addLockCode() throws InterruptedException {
        if (lockService.acquire("code-lock", 2, TimeUnit.SECONDS)) {
            try {
                code = code + 1;
            } finally {
                lockService.release();
            }
        } else {
            //休眠重新去抢锁
            Thread.sleep((long) (Math.random() * 10L));
            addLockCode();
        }
    }

}