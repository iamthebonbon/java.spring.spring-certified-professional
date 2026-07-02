package com.example.scp.test.fullcontext.profiles.sql;

import com.example.scp.service.TransactionPropagationService;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles({"sql"})
class TransactionPropagationServiceE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionPropagationService transactionPropagationService;

    @BeforeTransaction
    public void beforeTransaction() {
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

    @AfterTransaction
    public void afterTransaction() {
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

    @Test
    public void test() {
        Runnable parentRunnable = () -> jdbcTemplate.update("insert into bon_bon (id, candy_type) values (?,?)", 100, System.currentTimeMillis());
        Runnable runnable = () -> jdbcTemplate.update("insert into bon_bon (id, candy_type) values (?,?)", 200, System.currentTimeMillis());
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
        Assertions.assertThrows(RuntimeException.class, () -> {
            transactionPropagationService.requiredWithRequiredNewWithFullRollback(parentRunnable, runnable);
        });
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

    @Test
    @Transactional
    public void requiredWithRequiredNewWithChildRollback() {
        Runnable parentRunnable = () -> jdbcTemplate.update("insert into bon_bon (id, candy_type) values (?,?)", 100, System.currentTimeMillis());
        Runnable runnable = () -> jdbcTemplate.update("insert into bon_bon (id, candy_type) values (?,?)", 200, System.currentTimeMillis());
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
        transactionPropagationService.requiredWithRequiredNewWithChildRollback(parentRunnable, runnable);
        Assertions.assertEquals(2, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

}
