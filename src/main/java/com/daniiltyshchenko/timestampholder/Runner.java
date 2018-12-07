package com.daniiltyshchenko.timestampholder;

import com.daniiltyshchenko.timestampholder.service.TimestampService;
import com.daniiltyshchenko.timestampholder.worker.TimestampEventConsumer;
import com.daniiltyshchenko.timestampholder.worker.TimestampEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Gets application arguments list and decides which functionality
 * to run depending on provided args.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class Runner implements ApplicationRunner {

    private static final String PRINT_COMMAND = "-p";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TimestampService timestampService;
    private final TimestampEventProducer producer;
    private final TimestampEventConsumer consumer;

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final long producerDelay;
    private final long consumerDelay;

    @Autowired
    public Runner(TimestampService timestampService,
                  TimestampEventProducer producer,
                  TimestampEventConsumer consumer,
                  ThreadPoolTaskScheduler threadPoolTaskScheduler,
                  @Value("${scheduler.delay.producer}") long producerDelay,
                  @Value("${scheduler.delay.consumer}") long consumerDelay) {
        this.timestampService = timestampService;
        this.producer = producer;
        this.consumer = consumer;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.producerDelay = producerDelay;
        this.consumerDelay = consumerDelay;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<String> nonOptionArgs = args.getNonOptionArgs();

        if (nonOptionArgs.isEmpty()) {
            scheduleWritingTimestampsToDB();
        } else if (nonOptionArgs.contains(PRINT_COMMAND)) {
            printAllTimestampsFromDB();
        } else {
            LOGGER.error("Please do not put any parameters to collect timestamps OR use -p to retrieve all timestamps from the database");
        }
    }

    private void printAllTimestampsFromDB() {
        timestampService.getAllTimestamps().stream()
                .map(timestamp -> timestamp.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME))
                .forEach(LOGGER::info);
    }

    private void scheduleWritingTimestampsToDB() {
        LOGGER.debug("Scheduling producer with delay");
        threadPoolTaskScheduler.scheduleAtFixedRate(producer, producerDelay);
        threadPoolTaskScheduler.scheduleWithFixedDelay(consumer, consumerDelay);
    }
}
