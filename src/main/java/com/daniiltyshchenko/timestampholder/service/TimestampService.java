package com.daniiltyshchenko.timestampholder.service;

import com.daniiltyshchenko.timestampholder.domain.persistence.TimestampsEntityDB;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contains logic for manipulating with timestamps with help of {@link org.springframework.stereotype.Repository}.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
public interface TimestampService {

    void save(LocalDateTime timestamp);

    List<TimestampsEntityDB> getAllTimestamps();

    boolean isExists(LocalDateTime timestamp);

}
