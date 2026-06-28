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
import java.util.stream.StreamSupport;

@Transactional
@Sql(statements = {
        "create table bon_bon (id integer, candy_type varchar(255));",
        "insert into bon_bon (id, candy_type) values (1, 'chocolate')"
})
class RepositoryE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonbonRepository bonbonRepository;

    @Test
    void jdbcTemplateTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
    }

    @Test
    void jpaTest() {
        List<BonBon> all = StreamSupport.stream(bonbonRepository.findAll().spliterator(), false).toList();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void getByJpqlTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
        BonBon chocolate = bonbonRepository.getByTypeJavaPersistenceQueryLangauge("chocolate");
        Assertions.assertEquals("chocolate", chocolate.getCandyType());
    }

    @Test
    void getByNativeSqlTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
        BonBon chocolate = bonbonRepository.getByTypeNativeSql("chocolate");
        Assertions.assertEquals("chocolate", chocolate.getCandyType());
    }

    @Test
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
    void createByNativeSqlTest() {
        Assertions.assertEquals(1, bonbonRepository.createByNativeSql(2L, "marshmallow"));
        Assertions.assertNotNull(
                bonbonRepository.getByTypeNativeSql("marshmallow")
        );
    }

    @Test
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
    void updateByJpqlWithoutClearCacheTest() {
        // Attention!!! Cache still returns same value
        Assertions.assertEquals(
                "chocolate",
                bonbonRepository.findById(1L).get().getCandyType()
        );
        bonbonRepository.updateByJpqlWithoutClearCache(1L, "marshmallow");
        Assertions.assertEquals(
                "chocolate",
                bonbonRepository.findById(1L).get().getCandyType()
        );
    }

    @Test
    void updateByJpqlTest() {
        Assertions.assertEquals(
                "chocolate",
                bonbonRepository.findById(1L).get().getCandyType()
        );
        bonbonRepository.updateByJpql(1L, "marshmallow");
        Assertions.assertEquals(
                "marshmallow",
                bonbonRepository.findById(1L).get().getCandyType()
        );
    }

}
