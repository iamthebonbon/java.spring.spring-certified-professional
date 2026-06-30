package com.example.scp.test.fullcontext.profiles.sql;

import com.example.scp.repository.BonbonCreateNativeSqlRepository;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles({"sql"})
class RepositoryProfileSqlE2eTest extends AbstractE2eConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonbonCreateNativeSqlRepository bonbonCreateNativeSqlRepository;

    @Test
    void init() {
        Assertions.assertEquals("bonbon", applicationName);
    }

    @Test
    void firstTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
    }

    @BeforeTransaction
    public void beforeTransaction() {
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

    @AfterTransaction
    public void afterTransaction() {
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

    @Test
    @Transactional
    public void createRecord() {
        bonbonCreateNativeSqlRepository.createByNativeSql(2L, "bubblegum");
        Assertions.assertEquals(2, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

}
