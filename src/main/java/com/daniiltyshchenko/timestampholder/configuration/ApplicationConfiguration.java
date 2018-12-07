package com.daniiltyshchenko.timestampholder.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.lang.invoke.MethodHandles;

/**
 * Contains external configuration.
 * Currently, only initializes {@link ThreadPoolTaskScheduler}.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class ApplicationConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${scheduler.pool-size}")
    private int defaultPoolSize;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        LOGGER.debug("Setting ThreadPoolTaskScheduler with pool size: [{}].", defaultPoolSize);
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(defaultPoolSize);
        return threadPoolTaskScheduler;
    }
}
