package com.example.scp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DbTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbTests.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testOne() {
        LOGGER.info("DbTests is called");
        Assertions.assertEquals(
                21L,
                jdbcTemplate.queryForObject("SELECT count(*) FROM T_ACCOUNT", Long.class)
        );
    }

}
