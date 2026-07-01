package com.example.scp.test.fullcontext;

import com.example.scp.config.properties.BonbonProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BonbonPropertiesTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonProperties bonbonProperties;

    @Test
    public void test() {
        Assertions.assertEquals(
                "BonbonProperties{one='Marmalade', two='Marshmallow', three='null', custom='houston, halo from `bonbon.properties`'}",
                bonbonProperties.toString()
        );
    }

}
