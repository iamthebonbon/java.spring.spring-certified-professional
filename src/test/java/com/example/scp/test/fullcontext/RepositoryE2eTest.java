package com.example.scp.test.fullcontext;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaSystemException;
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
    void getByJpqlTest() {
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
    void getByNativeSqlTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
        BonBon chocolate = bonbonRepository.getByTypeNativeSql("marmalade");
        Assertions.assertEquals("marmalade", chocolate.getCandyType());
    }

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void createByNativeSqlWithoutModifyingTest() {
        JpaSystemException exception = Assertions.assertThrows(JpaSystemException.class, () -> {
            bonbonRepository.createByNativeSqlWithoutModifying(2L, "marshmallow");
        });
        Assertions.assertEquals(
                "JDBC exception executing SQL [insert into bon_bon(id, candy_type) values (?, ?)] [No results were returned by the query.] [n/a]",
                exception.getMessage()
        );
    }

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void createByNativeSqlTest() {
        Assertions.assertEquals(1, bonbonRepository.createByNativeSql(2L, "marshmallow"));
        Assertions.assertNotNull(
                bonbonRepository.getByTypeNativeSql("marshmallow")
        );
    }

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void updateByJpqlStatusWithoutModifyingTest() {
        InvalidDataAccessApiUsageException exception = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            bonbonRepository.updateByJpqlWithoutModifying(1L, "marshmallow");
        });
        Assertions.assertEquals(
                "Query executed via 'getResultList()' or 'getSingleResult()' must be a 'select' query [UPDATE BonBon o SET o.candyType = :type WHERE o.id = :id]",
                exception.getMessage()
        );
    }

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void updateByJpqlWithoutReturnTypeTest() {
        // Attention!!! Cache still returns same value
        Assertions.assertEquals(
                "cookie",
                bonbonRepository.getReferenceById(1L).getCandyType()
        );
        bonbonRepository.updateByJpqlWithoutClearCache(1L, "marshmallow");
        Assertions.assertEquals(
                "cookie",
                bonbonRepository.getByTypeJavaPersistenceQueryLangauge("marshmallow").getCandyType()
        );
    }

    @Test
    @Transactional
    // method-level @Sql overrides class-level one
    @Sql(statements = {
            "create table bon_bon (id integer, candy_type varchar(255));",
            "insert into bon_bon (id, candy_type) values (1, 'cookie')"
    })
    void updateByJpqlTest() {
        Assertions.assertEquals(
                "cookie",
                bonbonRepository.getReferenceById(1L).getCandyType()
        );
        bonbonRepository.updateByJpql(1L, "marshmallow");
        Assertions.assertEquals(
                "marshmallow",
                bonbonRepository.getByTypeNativeSql("marshmallow").getCandyType()
        );
    }

}
