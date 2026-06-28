package com.example.scp.test.fullcontext;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Sql(statements = {
        "create table bon_bon (id integer, candy_type varchar(255));"
})
class RepositoryE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonbonRepository bonBonRepository;

    @Test
    @Order(100)
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void testOne() {
        List<BonBon> all = bonBonRepository.findAll();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    @Order(200)
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void testTwo() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
    }

}
