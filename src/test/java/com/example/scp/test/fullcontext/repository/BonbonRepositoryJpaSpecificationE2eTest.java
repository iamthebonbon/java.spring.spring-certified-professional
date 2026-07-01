package com.example.scp.test.fullcontext.repository;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonRepository;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Sql(statements = {
        "create table bon_bon (id integer, candy_type varchar(255));",
        "insert into bon_bon (id, candy_type) values (1, 'chocolate')",
        "insert into bon_bon (id, candy_type) values (2, 'cookie')"
})
class BonbonRepositoryJpaSpecificationE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonRepository bonbonRepository;

    @Test
    void jpaSpecificationTest() {
        List<BonBon> all = bonbonRepository.findAll(BonbonRepository.idBetween(1L, 1L));
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals("chocolate", all.get(0).getCandyType());
    }

}
