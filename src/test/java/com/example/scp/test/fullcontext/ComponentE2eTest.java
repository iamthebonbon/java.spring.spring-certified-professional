package com.example.scp.test.fullcontext;

import com.example.scp.component.BonbonComponent;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ComponentE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonComponent bonbonComponent;
    @Autowired
    private Counter counter;

    @Test
    void test() {
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Assertions.assertEquals(3.0, counter.count());
    }

}
