package com.example.scp.test.fullcontext;

import com.example.scp.service.TransactionPropagationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

class TransactionPropagationServiceE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionPropagationService transactionPropagationService;

    @Test
    @Sql(statements = {
            "CREATE TABLE customer (id bigserial PRIMARY KEY, type VARCHAR(100))"
    })
    @Sql(statements = {
            "DROP TABLE customer"
    }, executionPhase = AFTER_TEST_METHOD)
    public void test() {
        Runnable runnable = () -> jdbcTemplate.update("insert into customer (type) values (?)", System.currentTimeMillis());
        Assertions.assertEquals(0, jdbcTemplate.queryForObject("select count(*) from customer", Integer.class));
        Assertions.assertThrows(RuntimeException.class, () -> {
            transactionPropagationService.requiredWithRequiredNewWithFullRollback(runnable, runnable);
        });
        Assertions.assertEquals(0, jdbcTemplate.queryForObject("select count(*) from customer", Integer.class));
    }

    @Test
    @Sql(statements = {
            "CREATE TABLE customer (id bigserial PRIMARY KEY, type VARCHAR(100))"
    })
    @Sql(statements = {
            "DROP TABLE customer"
    }, executionPhase = AFTER_TEST_METHOD)
    public void requiredWithRequiredNewWithChildRollback() {
        Runnable runnable = () -> jdbcTemplate.update("insert into customer (type) values (?)", System.currentTimeMillis());
        Assertions.assertEquals(0, jdbcTemplate.queryForObject("select count(*) from customer", Integer.class));
        transactionPropagationService.requiredWithRequiredNewWithChildRollback(runnable, runnable);
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from customer", Integer.class));
    }

}
