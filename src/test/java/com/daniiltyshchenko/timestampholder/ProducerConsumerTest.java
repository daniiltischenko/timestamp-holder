package com.daniiltyshchenko.timestampholder;

import com.daniiltyshchenko.timestampholder.domain.TimestampMessage;
import com.daniiltyshchenko.timestampholder.service.QueueMessageService;
import com.daniiltyshchenko.timestampholder.service.TimestampService;
import com.daniiltyshchenko.timestampholder.service.impl.TimestampQueueMessageService;
import com.daniiltyshchenko.timestampholder.worker.TimestampEventConsumer;
import com.daniiltyshchenko.timestampholder.worker.TimestampEventProducer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

/**
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
public class ProducerConsumerTest {

    private QueueMessageService<TimestampMessage> queueMessageService;
    private TimestampService timestampService;
    private TimestampEventProducer producer;
    private TimestampEventConsumer consumer;

    @Before
    public void setUp() {
        queueMessageService = Mockito.spy(TimestampQueueMessageService.class);
        timestampService = Mockito.mock(TimestampService.class);
        producer = new TimestampEventProducer(queueMessageService);
        consumer = new TimestampEventConsumer(queueMessageService, timestampService);
    }

    @Test
    public void testPush() {
        try {
            producer.run();

            Mockito.verify(queueMessageService, Mockito.times(1))
                    .push(Mockito.any(TimestampMessage.class));
        } finally {
            queueMessageService.remove();
        }
    }

    @Test
    public void testPull() {
        TimestampMessage timestamp = new TimestampMessage().setTimestamp(LocalDateTime.now());
        queueMessageService.push(timestamp);

        consumer.run();

        Mockito.verify(timestampService, Mockito.times(1))
                .save(timestamp.getTimestamp());

        Mockito.verify(queueMessageService, Mockito.times(1))
                .remove();
    }


}
