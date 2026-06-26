package com.example.scp.test.it;

import com.example.scp.component.BonbonComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ActuatorItTests extends AbstractItConfiguration {

    @Autowired
    private BonbonComponent bonbonComponent;

    @Test
    void test() {
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
    }

}
