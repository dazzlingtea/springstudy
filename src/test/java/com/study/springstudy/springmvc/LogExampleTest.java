package com.study.springstudy.springmvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogExampleTest {
    @Autowired
    LogExample logExample;

    @Test
    @DisplayName("")
    void logTest() {

        logExample.showLog();
    }

}