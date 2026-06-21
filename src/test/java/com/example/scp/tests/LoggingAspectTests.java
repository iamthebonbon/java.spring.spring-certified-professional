package com.example.scp.tests;

import com.example.scp.aop.LoggingAspect;
import com.example.scp.controller.MainController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.stream.Collectors;

@EnableAspectJAutoProxy
@Import({MainController.class, LoggingAspect.class})
@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
public class LoggingAspectTests {

    @Test
    void contextLoads(
            CapturedOutput output,
            @Autowired MainController controller
    ) {
        controller.get("");
        Assertions.assertTrue(
                Arrays.stream(output.getOut().split("\n"))
                        .filter(v -> v.contains("com.example.scp.aop"))
                        .collect(Collectors.joining("\n"))
                        .contains("Before")
        );
    }


}
