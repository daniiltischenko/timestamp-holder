package com.daniiltyshchenko.timestampholder;

import com.daniiltyshchenko.timestampholder.domain.persistence.TimestampsEntityDB;
import com.daniiltyshchenko.timestampholder.repository.TimestampRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TimestampRepository repository;

    @Test
    public void testWriteToDb() {
        LocalDateTime now = LocalDateTime.now();
        TimestampsEntityDB toWrite = new TimestampsEntityDB().setTimestamp(now).setId(1);
        repository.save(toWrite);
        TimestampsEntityDB fromDB = this.entityManager.find(TimestampsEntityDB.class, 1);

        Assertions.assertThat(fromDB).isEqualTo(toWrite);
    }

    @Test
    public void testFindByTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        this.entityManager.persistAndFlush(new TimestampsEntityDB().setTimestamp(now));

        TimestampsEntityDB timestamp = this.repository.findTimestampInfoByTimestamp(now);
        Assertions.assertThat(timestamp.getTimestamp()).isEqualTo(now);
    }
}
