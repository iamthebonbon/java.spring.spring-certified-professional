package com.example.scp.test.fullcontext;

import com.example.scp.service.BonbonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
class BonbonServiceE2eTest extends AbstractE2eConfiguration {

    @Autowired
    private BonbonServiceImpl service;

    @Test
    public void mandatory(CapturedOutput output) {
        service.test();
        Assertions.assertTrue(output.getAll().contains("halo"));
        Assertions.assertTrue(output.getAll().contains("bindAnnotation"));
        Assertions.assertTrue(output.getAll().contains("referenceTypeAnnotation"));
        Assertions.assertTrue(output.getAll().contains("within"));
        Assertions.assertTrue(output.getAll().contains("target"));
        Assertions.assertTrue(output.getAll().contains("withinAnnotation"));
        Assertions.assertTrue(output.getAll().contains("targetAnnotation"));
        Assertions.assertTrue(output.getAll().contains("generalPointcutAndreferenceTypeAnnotation"));
    }

}
