package com.example.scp.test.fullcontext.profiles.sql;

import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"sql"})
class RepositoryProfileSqlE2eTest extends AbstractE2eConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void init() {
        Assertions.assertEquals("bonbon", applicationName);
    }

    @Test
    void firstTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from sql", Integer.class);
        Assertions.assertEquals(1, count);
    }

}
