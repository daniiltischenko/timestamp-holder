package com.daniiltyshchenko.timestampholder;

import com.daniiltyshchenko.timestampholder.domain.TimestampMessage;
import com.daniiltyshchenko.timestampholder.service.QueueMessageService;
import com.daniiltyshchenko.timestampholder.service.impl.TimestampQueueMessageService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
public class QueueServiceTest {

    private QueueMessageService<TimestampMessage> service;

    @Before
    public void setUp() {
        service = new TimestampQueueMessageService();
    }

    @Test
    public void shouldBeEmptyByDefault() {
        TimestampMessage pull = service.pull();
        Assertions.assertThat(pull).isNull();
    }

    @Test
    public void testProduce() {
        try {
            TimestampMessage messageToPush = new TimestampMessage().setTimestamp(LocalDateTime.now());
            service.push(messageToPush);

            TimestampMessage messageFromQueue = service.pull();
            Assertions.assertThat(messageFromQueue).isNotNull();
            Assertions.assertThat(messageToPush).isEqualTo(messageFromQueue);
        } finally {
            service.remove();
        }
    }

    @Test
    public void messageNotRemovedByDefault() {
        try {
            TimestampMessage messageToPush = new TimestampMessage().setTimestamp(LocalDateTime.now());
            service.push(messageToPush);

            TimestampMessage messageFromQueue_01 = service.pull();
            Assertions.assertThat(messageFromQueue_01).isNotNull();

            TimestampMessage messageFromQueue_02 = service.pull();
            Assertions.assertThat(messageFromQueue_02).isNotNull();

            Assertions.assertThat(messageFromQueue_01).isEqualTo(messageFromQueue_02);
        } finally {
            service.remove();
        }
    }

    @Test
    public void testRemoveMessage() {
        TimestampMessage messageToPush = new TimestampMessage().setTimestamp(LocalDateTime.now());
        service.push(messageToPush);

        TimestampMessage messageFromQueue_01 = service.pull();
        Assertions.assertThat(messageFromQueue_01).isNotNull();

        service.remove();

        TimestampMessage messageFromQueue_02 = service.pull();
        Assertions.assertThat(messageFromQueue_02).isNull();
    }

    @Test
    public void testOrder() {
        TimestampMessage messageToPush_01 = new TimestampMessage().setTimestamp(LocalDateTime.now().plusDays(1L));
        service.push(messageToPush_01);

        TimestampMessage messageToPush_02 = new TimestampMessage().setTimestamp(LocalDateTime.now().plusDays(2L));
        service.push(messageToPush_02);

        TimestampMessage messageToPush_03 = new TimestampMessage().setTimestamp(LocalDateTime.now().plusDays(3L));
        service.push(messageToPush_03);

        TimestampMessage messageFromQueue_01 = service.pull();
        service.remove();
        TimestampMessage messageFromQueue_02 = service.pull();
        service.remove();
        TimestampMessage messageFromQueue_03 = service.pull();
        service.remove();

        Assertions.assertThat(messageFromQueue_01).isNotNull();
        Assertions.assertThat(messageFromQueue_02).isNotNull();
        Assertions.assertThat(messageFromQueue_03).isNotNull();

        Assertions.assertThat(messageToPush_01).isEqualTo(messageFromQueue_01);
        Assertions.assertThat(messageToPush_02).isEqualTo(messageFromQueue_02);
        Assertions.assertThat(messageToPush_03).isEqualTo(messageFromQueue_03);
    }
}
