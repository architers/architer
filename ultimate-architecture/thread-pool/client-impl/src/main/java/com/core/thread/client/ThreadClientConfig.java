package com.core.thread.client;


import com.core.thread.client.parse.ThreadPoolParser;
import com.core.thread.client.properties.ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class ThreadClientConfig {

    @Bean
    public ThreadPoolParser threadPoolParser() {
        return new ThreadPoolParser();
    }
}
