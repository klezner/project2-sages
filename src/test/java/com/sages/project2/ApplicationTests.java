package com.sages.project2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    public void shouldReturnText() throws Exception {
        String expected = "witajSwiecie";
        String received = "witajSwiecie!!!";
        Assertions.assertEquals(received, expected);
    }

}
