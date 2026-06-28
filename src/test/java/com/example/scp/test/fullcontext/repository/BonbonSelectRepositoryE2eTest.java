package com.example.scp.test.fullcontext.repository;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonSelectRepository;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Sql(statements = {
        "create table bon_bon (id integer, candy_type varchar(255));",
        "insert into bon_bon (id, candy_type) values (1, 'chocolate')"
})
class BonbonSelectRepositoryE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonbonSelectRepository bonbonSelectRepository;

    @Test
    void getByNativeSqlTest() {
        BonBon chocolate = bonbonSelectRepository.getByTypeNativeSql("chocolate");
        Assertions.assertEquals("chocolate", chocolate.getCandyType());

    }

    @Test
    void getByJpqlTest() {
        BonBon chocolate = bonbonSelectRepository.getByTypeJavaPersistenceQueryLangauge("chocolate");
        Assertions.assertEquals("chocolate", chocolate.getCandyType());
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
    }

}
