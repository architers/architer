package com.architecture.ultimate.cache.local;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 描述：缓存持久化执行器
 *
 * @author luyi
 * @date 2021/3/10
 */

public class CacheEnduranceActuator {

    private static final Logger log = LoggerFactory.getLogger(CacheEnduranceActuator.class);


    /**
     * 描述：从本地读取缓存
     *
     * @author luyi
     * @date 2021/3/10
     */
    public LocalCache readCaChe() {
        File file = new File("localCache.txt");
        //说明没有初始化文件
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (LocalCache) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("加载缓存失败", e);
        }
    }


    /**
     * 描述：向本地写入缓存
     *
     * @author luyi
     * @date 2021/3/10
     */
    public synchronized boolean writeCache(LocalCache localCache) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("record.txt"));
            os.writeObject(localCache);
            return true;
        } catch (Exception e) {
            log.error("缓存持久化失败", e);
        }
        return false;
    }


}
