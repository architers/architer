package io.github.architers.test.thread;



import io.github.architers.thread.client.annotation.ThreadPoolClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


/**
 * @author luyi
 */
@Component
@ThreadPoolClient(name = "test", caption = "测试")
public class TestThrealPool extends ThreadPoolTaskExecutor {

    @Override
    public void initialize() {
        super.initialize();
    }
}
