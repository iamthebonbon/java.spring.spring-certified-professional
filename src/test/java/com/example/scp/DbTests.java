package com.example.scp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DbTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbTests.class);

    @BeforeAll
    public static void beforeAll() {
        LOGGER.info("JUnit5@BeforeAll is called");
    }

    @AfterAll
    public static void afterAll() {
        LOGGER.info("JUnit5@AfterAll is called");
    }

    @BeforeEach
    public void beforeEach() {
        LOGGER.info("JUnit5@BeforeEach is called");
    }

    @AfterEach
    public void afterEach() {
        LOGGER.info("JUnit5@AfterEach is called");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testOne() {
        Assertions.assertEquals(
                21L,
                jdbcTemplate.queryForObject("SELECT count(*) FROM T_ACCOUNT", Long.class)
        );
    }

}
