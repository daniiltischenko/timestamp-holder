package com.daniiltyshchenko.timestampholder.service.impl;

import com.daniiltyshchenko.timestampholder.domain.persistence.TimestampsEntityDB;
import com.daniiltyshchenko.timestampholder.repository.TimestampRepository;
import com.daniiltyshchenko.timestampholder.service.TimestampService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation for {@link TimestampService}.
 * Middleware layer between the repository and business logic.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class TimestampServiceImpl implements TimestampService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TimestampRepository repository;

    @Autowired
    public TimestampServiceImpl(TimestampRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(LocalDateTime timestamp) {
        LOGGER.debug("Saving timestamp to the database: [{}]", timestamp);
        repository.save(new TimestampsEntityDB()
                .setTimestamp(timestamp));
    }

    @Override
    public List<TimestampsEntityDB> getAllTimestamps() {
        return repository.findAll();
    }

    @Override
    public boolean isExists(LocalDateTime timestamp) {
        return Objects.isNull(repository.findTimestampInfoByTimestamp(timestamp));
    }
}
