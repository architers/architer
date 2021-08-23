package com.architecture.test.cache.delete;

import com.architecture.test.cache.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteServiceImpl implements DeleteService {
    private Logger logger = LoggerFactory.getLogger(DeleteServiceImpl.class);

    @Override
    public void oneDelete(UserInfo userInfo) {
        logger.info("oneDelete:{}", userInfo.getUsername());
    }
}
