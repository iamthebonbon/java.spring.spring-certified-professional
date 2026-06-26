package com.example.scp.test.extension;

import com.example.scp.extension.BeforeEachCallbackExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BeforeEachCallbackExtension.class)
public class BeforeEachCallbackTests {

    @Test
    public void first() {
        Assertions.assertTrue(true);
    }

    @Test
    public void second() {
        Assertions.assertTrue(true);
    }
}
