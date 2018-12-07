package com.daniiltyshchenko.timestampholder.worker;

import com.daniiltyshchenko.timestampholder.domain.TimestampMessage;
import com.daniiltyshchenko.timestampholder.service.QueueMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

/**
 * Produces {@link TimestampMessage} objects and pushes them to the queue by using {@link QueueMessageService}.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class TimestampEventProducer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final QueueMessageService<TimestampMessage> queueMessageService;

    @Autowired
    public TimestampEventProducer(QueueMessageService<TimestampMessage> queueMessageService) {
        this.queueMessageService = queueMessageService;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        LOGGER.debug("Pushing timestamp event to the queue: [{}]", now);
        queueMessageService.push(new TimestampMessage().setTimestamp(now));
    }
}
