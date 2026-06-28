package com.example.scp.test.fullcontext.repository;

import com.example.scp.repository.BonbonCreateNativeSqlRepository;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Sql(statements = {
        "create table bon_bon (id integer, candy_type varchar(255));",
        "insert into bon_bon (id, candy_type) values (1, 'chocolate')"
})
class BonbonCreateNativeSqlRepositoryE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonCreateNativeSqlRepository bonbonCreateNativeSqlRepository;

    @Test
    void createByNativeSqlWithoutModifyingTest() {
        JpaSystemException exception = Assertions.assertThrows(JpaSystemException.class, () -> {
            bonbonCreateNativeSqlRepository.createByNativeSqlWithoutModifying(2L, "marshmallow");
        });
        Assertions.assertEquals(
                "JDBC exception executing SQL [insert into bon_bon(id, candy_type) values (?, ?)] [No results were returned by the query.] [n/a]",
                exception.getMessage()
        );
    }

    @Test
    void createByNativeSqlTest() {
        Assertions.assertEquals(1, bonbonCreateNativeSqlRepository.createByNativeSql(2L, "marshmallow"));
        Assertions.assertNotNull(bonbonCreateNativeSqlRepository.findById(2L).orElseThrow());
    }

}
