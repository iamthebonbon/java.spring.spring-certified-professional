package com.example.scp.test.slice;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonBonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;

@DataJpaTest
@Sql(statements = {
        "create table if not exists bon_bon (id integer, candy_type varchar(255));"
})
class DataJpaTests {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonBonRepository bonBonRepository;

    @Test
    void test() {
        List<BonBon> all = bonBonRepository.findAll();
        Assertions.assertTrue(all.isEmpty());
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(0, count);
    }

}
