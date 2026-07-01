package com.example.scp.test.slice;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@DataJpaTest
@Sql(statements = {
        "create table bon_bon (id integer, candy_type varchar(255));"
})
class BonbonRepositorySliceTest {

    @TestConfiguration
    @EnableJpaAuditing(auditorAwareRef = "auditorProvider")
    public static class DataConfiguration {

        @Bean
        public AuditorAware<String> auditorProvider() {
            return () -> Optional.ofNullable(
                    SecurityContextHolder.getContext().getAuthentication()
            ).map(Authentication::getName);
        }

    }

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonbonRepository bonbonRepository;

    @Test
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, created_at timestamp, updated_at timestamp, created_by varchar(255), updated_by varchar(255), candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void test() {
        List<BonBon> all = StreamSupport.stream(bonbonRepository.findAll().spliterator(), false).toList();
        Assertions.assertEquals(1, all.size());
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
    }

    @Test
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id bigserial primary key, created_at timestamp, updated_at timestamp, created_by varchar(255), updated_by varchar(255), candy_type varchar(255));",
            "insert into bon_bon (candy_type) values ('cookie')"
    })
    void testSave() {
        BonBon bonBon = new BonBon();
        bonBon.setCandyType("bubblegum");
        bonbonRepository.save(bonBon);
        Assertions.assertNotNull(bonBon.getCreatedAt());
        Assertions.assertNotNull(bonBon.getUpdatedAt());
    }

}
