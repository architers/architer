package com.architecture.test.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CacheableUserInfoServiceImpl implements CacheableUserInfoService {
    private final Logger logger = LoggerFactory.getLogger(CacheableUserInfoService.class);

    public UserInfo oneCacheable1(String userName) {
        logger.info("{}查询数据库", "oneCacheable1");
        return new UserInfo().setUsername(userName);
    }
}
