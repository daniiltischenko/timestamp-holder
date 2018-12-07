package com.daniiltyshchenko.timestampholder.service;

/**
 * Has {@link java.util.Queue} implementation that supports FIFO for temporary storing event messages.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
public interface QueueMessageService<T> {

    /**
     * Pushes {@param message} to Queue.
     *
     * @param message
     */
    void push(T message);

    /**
     * Pulls the firstly added message.
     *
     * @return
     */
    T pull();

    /**
     * Removes the last message from the queue.
     */
    void remove();
}
