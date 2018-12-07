package com.daniiltyshchenko.timestampholder.service.impl;

import com.daniiltyshchenko.timestampholder.domain.TimestampMessage;
import com.daniiltyshchenko.timestampholder.service.QueueMessageService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Default implementation for {@link QueueMessageService}.
 * Uses {@link Queue} implementation for objects manipulation.
 *
 * While only 1 producer and 1 consumer use the queue, no sense to use concurrent implementation.
 * But in case of extending functionality with multiple consumers or producers,
 * better to consider some implementation like {@link java.util.concurrent.ConcurrentLinkedQueue}.
 *
 * Also, current implementation of service makes it possible to extend functionality with use of topics
 * (Amazon SQS may be taken as the example).
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class TimestampQueueMessageService implements QueueMessageService<TimestampMessage> {

    private final Queue<TimestampMessage> queue = new LinkedList<>();

    @Override
    public void push(TimestampMessage timestamp) {
        this.queue.add(timestamp);
    }

    @Override
    public TimestampMessage pull() {
        return this.queue.peek();
    }

    @Override
    public void remove() {
        this.queue.remove();
    }
}
