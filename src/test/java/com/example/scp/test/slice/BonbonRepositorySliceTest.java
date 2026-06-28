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
        "create table bon_bon (id integer, candy_type varchar(255));"
})
class BonbonRepositorySliceTest {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonBonRepository bonBonRepository;

    @Test
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void test() {
        List<BonBon> all = bonBonRepository.findAll();
        Assertions.assertEquals(1, all.size());
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
    }

}
