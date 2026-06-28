package com.example.scp.test.fullcontext.repository;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonRepository;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Transactional
@Sql(statements = {
        "create table bon_bon (id integer, candy_type varchar(255));",
        "insert into bon_bon (id, candy_type) values (1, 'chocolate')",
        "insert into bon_bon (id, candy_type) values (1, 'cookie')"
})
class BonbonRepositoryE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonbonRepository bonbonRepository;

    @Test
    void jdbcTemplateTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(2, count);
    }

    @Test
    void repositoryTest() {
        List<BonBon> all = StreamSupport.stream(bonbonRepository.findAll().spliterator(), false).toList();
        Assertions.assertEquals(2, all.size());
    }

    @Test
    void resultSetTest() {
        Assertions.assertEquals(
                "chocolatecookie",
                jdbcTemplate.query("select * from bon_bon order by id",
                        rs -> {
                            StringBuilder stringBuilder = new StringBuilder();
                            while (rs.next()) {
                                stringBuilder.append(rs.getString("candy_type"));
                            }
                            return stringBuilder.toString();
                        }
                )
        );
    }

    @Test
    void rowCallbackHandlerTest() {
        StringBuilder sb = new StringBuilder();
        jdbcTemplate.query("select * from bon_bon order by id",
                rs -> {
                    while (rs.next()) {
                        sb.append(rs.getString("candy_type"));
                    }
                }
        );
        Assertions.assertEquals("chocolatecookie", sb.toString());
    }

}
