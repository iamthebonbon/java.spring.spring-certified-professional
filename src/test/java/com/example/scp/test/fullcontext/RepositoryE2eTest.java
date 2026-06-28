package com.example.scp.test.fullcontext;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonRepository;
import org.junit.jupiter.api.Assertions;
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
    private BonbonRepository bonbonRepository;

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void jpaTest() {
        List<BonBon> all = bonbonRepository.findAll();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'chocolate')"
    })
    void jdbcTemplateTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
    }

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'chocolate')"
    })
    void jpqlTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
        BonBon chocolate = bonbonRepository.getByTypeJavaPersistenceQueryLangauge("chocolate");
        Assertions.assertEquals("chocolate", chocolate.getCandyType());
    }

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'marmalade')"
    })
    void nativeSqlTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
        BonBon chocolate = bonbonRepository.getByTypeNativeSql("marmalade");
        Assertions.assertEquals("marmalade", chocolate.getCandyType());
    }

}
