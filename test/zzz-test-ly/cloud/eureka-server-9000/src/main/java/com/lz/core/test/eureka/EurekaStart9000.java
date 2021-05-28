package com.lz.core.test.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author luyi
 * <p>
 * Eureka服务端
 */
@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
@EnableEurekaServer
public class EurekaStart9000 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaStart9000.class, args);
    }
}
