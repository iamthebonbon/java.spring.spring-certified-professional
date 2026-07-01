package com.example.scp.test.fullcontext.repository;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonRepository;
import com.example.scp.repository.projection.BonbonProjection;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.StreamSupport;

@Transactional
@Sql(statements = {
        "create table bon_bon (id integer, created_at timestamp, updated_at timestamp, created_by varchar(255), updated_by varchar(255), candy_type varchar(255));",
        "insert into bon_bon (id, candy_type) values (1, 'chocolate')",
        "insert into bon_bon (id, candy_type) values (2, 'cookie')"
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
    void projectionsTest() {
        BonbonProjection projection = bonbonRepository.findByCandyType("chocolate");
        Assertions.assertEquals("1 chocolate", projection.getType());
        BonbonProjection dynamicProjection = bonbonRepository.findByCandyType("cookie", BonbonProjection.class);
        Assertions.assertEquals("2 cookie", dynamicProjection.getType());
    }

    @Test
    void rowMapperTest() {
        Assertions.assertEquals(
                "[BonBon{id=1, candyType='chocolate'}, BonBon{id=2, candyType='cookie'}]",
                jdbcTemplate.query("select * from bon_bon order by id",
                        new RowMapper<BonBon>() {
                            @Override
                            public @Nullable BonBon mapRow(ResultSet rs, int rowNum) throws SQLException {
                                BonBon bonBon = new BonBon();
                                bonBon.setId(rs.getLong("id"));
                                bonBon.setCandyType(rs.getString("candy_type"));
                                return bonBon;
                            }
                        }
                ).toString()
        );
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
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(2, count);
        StringBuilder sb = new StringBuilder();
        jdbcTemplate.query("select * from bon_bon order by id",
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        sb.append(rs.getString("candy_type"));
                    }
                }
        );
        Assertions.assertEquals("chocolatecookie", sb.toString());
    }

}
