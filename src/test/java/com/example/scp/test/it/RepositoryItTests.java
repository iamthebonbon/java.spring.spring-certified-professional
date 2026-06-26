package com.example.scp.test.it;

import com.example.scp.entity.BonBon;
import com.example.scp.repository.BonBonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

class RepositoryItTests extends AbstractItConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BonBonRepository bonBonRepository;

    @Test
    void testOne() {
        List<BonBon> all = bonBonRepository.findAll();
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    void testTwo() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bon_bon", Integer.class);
        Assertions.assertEquals(0, count);
    }

}
