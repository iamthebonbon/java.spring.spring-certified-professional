package com.example.scp.test.fullcontext.repository;

import com.example.scp.repository.BonbonRepository;
import com.example.scp.repository.BonbonUpdateJpqlRepository;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Sql(statements = {
        "create table bon_bon (id integer, created_at timestamp, updated_at timestamp, created_by varchar(255), updated_by varchar(255), candy_type varchar(255));",
        "insert into bon_bon (id, candy_type) values (1, 'chocolate')"
})
class BonbonUpdateJpqlRepositoryE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonUpdateJpqlRepository bonbonUpdateJpqlRepository;

    @Test
    void updateByJpqlStatusWithoutModifyingTest() {
        InvalidDataAccessApiUsageException exception = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            bonbonUpdateJpqlRepository.updateByJpqlWithoutModifying(1L, "marshmallow");
        });
        Assertions.assertEquals(
                "Query executed via 'getResultList()' or 'getSingleResult()' must be a 'select' query [UPDATE BonBon o SET o.candyType = :type WHERE o.id = :id]",
                exception.getMessage()
        );
    }

    /**
     * Attention!!! Cache still returns same value
     */
    @Test
    void updateByJpqlWithoutClearCacheTest() {
        Assertions.assertEquals(
                "chocolate",
                bonbonUpdateJpqlRepository.findById(1L).orElseThrow().getCandyType()
        );
        bonbonUpdateJpqlRepository.updateByJpqlWithoutClearCache(1L, "marshmallow");
        Assertions.assertEquals(
                "chocolate",
                bonbonUpdateJpqlRepository.findById(1L).orElseThrow().getCandyType()
        );
    }

    @Test
    void updateByJpqlTest() {
        Assertions.assertEquals(
                "chocolate",
                bonbonUpdateJpqlRepository.findById(1L).orElseThrow().getCandyType()
        );
        bonbonUpdateJpqlRepository.updateByJpql(1L, "marshmallow");
        Assertions.assertEquals(
                "marshmallow",
                bonbonUpdateJpqlRepository.findById(1L).orElseThrow().getCandyType()
        );
    }

}
