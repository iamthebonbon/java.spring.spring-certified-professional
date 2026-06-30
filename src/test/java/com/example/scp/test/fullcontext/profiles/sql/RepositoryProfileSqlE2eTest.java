package com.example.scp.test.fullcontext.profiles.sql;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonbonCreateNativeSqlRepository;
import com.example.scp.test.fullcontext.AbstractE2eConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@ActiveProfiles({"sql"})
class RepositoryProfileSqlE2eTest extends AbstractE2eConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonbonCreateNativeSqlRepository bonbonCreateNativeSqlRepository;

    @Test
    void init() {
        Assertions.assertEquals("bonbon", applicationName);
    }

    @Test
    void firstTest() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(1, count);
    }

    @BeforeTransaction
    public void beforeTransaction() {
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

    @AfterTransaction
    public void afterTransaction() {
        Assertions.assertEquals(1, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

    @Test
    @Transactional
    public void createRecord() {
        bonbonCreateNativeSqlRepository.createByNativeSql(2L, "bubblegum");
        Assertions.assertEquals(2, jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class));
    }

    @Test
    @Transactional
    public void preparedStatement() {
        List<BonBon> query = jdbcTemplate.query("select * from bon_bon where id = ? and candy_type = ?",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setLong(1, 1L);
                        ps.setString(2, "chocolate");
                    }
                },
                new RowMapper<BonBon>() {
                    @Override
                    public BonBon mapRow(ResultSet rs, int rowNum) throws SQLException {
                        BonBon bonBon = new BonBon();
                        bonBon.setId(rs.getLong("id"));
                        bonBon.setCandyType(rs.getString("candy_type"));
                        return bonBon;
                    }
                });
        Assertions.assertEquals("chocolate", query.get(0).getCandyType());
    }


    @Test
    @Transactional
    public void keyHolder() {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO bon_bon (id, candy_type) VALUES (?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    ps.setLong(1, 2L);
                    ps.setString(2, "cookie");
                    return ps;
                },
                generatedKeyHolder);
        Assertions.assertFalse(generatedKeyHolder.getKeys().isEmpty());
    }

}
