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
        "create table bon_bon (id integer, created_at timestamp, updated_at timestamp, created_by varchar(255), updated_by varchar(255), candy_type varchar(255));",
        "insert into bon_bon (id, candy_type) values (1, 'chocolate')",
        "insert into bon_bon (id, candy_type) values (2, 'cookie')"
})
class BonbonRepositoryExampleE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonRepository bonbonRepository;

    @Test
    void exampleTest() {
        BonBon bonBon = new BonBon();
        bonBon.setCandyType("cookie");
        List<BonBon> all = bonbonRepository.findAll(Example.of(bonBon));
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(2, all.get(0).getId());
    }

    @Test
    void exampleConditionalTest() {
        BonBon bonBon = new BonBon();
        bonBon.setId(99L);
        bonBon.setCandyType("ooki");
        List<BonBon> all = bonbonRepository.findAll(
                Example.of(
                        bonBon,
                        ExampleMatcher.matching()
                                .withMatcher("candyType",
                                        ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                                .withIgnorePaths("id")
                )
        );
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(2, all.get(0).getId());
    }

    @Test
    void exampleConditionalStartWithTest() {
        BonBon bonBon = new BonBon();
        bonBon.setId(99L);
        bonBon.setCandyType("cook");
        List<BonBon> all = bonbonRepository.findAll(
                Example.of(
                        bonBon,
                        ExampleMatcher.matching()
                                .withMatcher("candyType",
                                        ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase())
                                .withIgnorePaths("id")
                )
        );
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(2, all.get(0).getId());
    }

}
