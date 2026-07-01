package com.example.scp.test.slice;

import com.example.scp.entity.BonBon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

@JsonTest
public class BonbonJsonSliceTest {

    @Autowired
    private JacksonTester<BonBon> jacksonTester;

    @Test
    public void test() throws IOException {
        JsonContent<BonBon> write = jacksonTester.write(new BonBon());
        Assertions.assertEquals("""
                        {"createdAt":null,"updatedAt":null,"createdBy":null,"updatedBy":null,"id":null,"candyType":null}""",
                write.getJson()
        );
    }
}
