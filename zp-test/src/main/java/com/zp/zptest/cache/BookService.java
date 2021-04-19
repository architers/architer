package com.zp.zptest.cache;

import com.lz.core.cache.common.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

/**
 * @author zhoupei
 * @create 2021/4/13
 **/
@Component
public class BookService {

//    @Cacheable("book")
//    @Cacheable(randomExpireTime = 60*5)
//    public Book getByIsbn(String isbn){
//        try {
//            Thread.sleep(1000);
//            System.out.println("没有执行缓存取数据...");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return new Book(isbn,"111");
//    }

    @Cacheable(randomExpireTime = 60*5)
    public String getByIsbn(String isbn){
        try {
            Thread.sleep(1000);
            System.out.println("没有执行缓存取数据...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isbn;
    }
}
