package com.daniiltyshchenko.timestampholder.worker;

import com.daniiltyshchenko.timestampholder.domain.TimestampMessage;
import com.daniiltyshchenko.timestampholder.service.QueueMessageService;
import com.daniiltyshchenko.timestampholder.service.TimestampService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

/**
 * Consumes {@link TimestampMessage} objects from the queue service.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class TimestampEventConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final QueueMessageService<TimestampMessage> queueMessageService;
    private final TimestampService timestampService;

    @Autowired
    public TimestampEventConsumer(QueueMessageService<TimestampMessage> queueMessageService,
                                  TimestampService timestampService) {
        this.queueMessageService = queueMessageService;
        this.timestampService = timestampService;
    }

    @Override
    public void run() {
        TimestampMessage message = queueMessageService.pull();

        while (Objects.nonNull(message)) {
            try {
                timestampService.save(message.getTimestamp());

                queueMessageService.remove();

                message = queueMessageService.pull();
            } catch (Exception e) {
                LOGGER.error("Failed to write timestamp to the database, the reason is: [{}]", e.getMessage());
                if (timestampService.isExists(message.getTimestamp())) {
                    queueMessageService.remove();
                }
                message = null;
            }
        }
    }
}
