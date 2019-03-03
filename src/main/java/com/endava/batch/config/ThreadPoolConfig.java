package com.endava.batch.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService executorService() {
        return ForkJoinPool.commonPool();
    }
}
