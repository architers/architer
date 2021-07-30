package com.business.base.start;

import com.architecture.ultimate.starter.web.module.ModulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基础数据服务
 *
 * @author luyi
 */
@SpringBootApplication
public class BaseServer {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(BaseServer.class);
        new SpringApplication(modules).run(args);
    }
}
