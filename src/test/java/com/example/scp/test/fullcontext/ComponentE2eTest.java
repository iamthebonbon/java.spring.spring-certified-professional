package com.example.scp.test.fullcontext;

import com.example.scp.component.BonbonComponent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ComponentE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonComponent bonbonComponent;
    @Autowired
    private MeterRegistry meterRegistry;

    @Test
    void test() {
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Assertions.assertEquals("Iam test Houston", bonbonComponent.comCheck());
        Counter counter = meterRegistry.get("bonbon-component").counter();
        Assertions.assertEquals(3.0, counter.count());
    }

}
