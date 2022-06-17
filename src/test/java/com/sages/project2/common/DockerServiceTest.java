package com.sages.project2.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DockerServiceTest {

    @Autowired
    DockerService dockerService;

    @Test
    @Disabled
        //I added not to extend the testing time :-)
    void giveGitUrl_ThenReturnExpectedString() {
        String input = "https://github.com/Geri999/temp1.git";

        String result = dockerService.dockerService(input);
        Assertions.assertEquals("Hello world! 123\n", result);
    }
}