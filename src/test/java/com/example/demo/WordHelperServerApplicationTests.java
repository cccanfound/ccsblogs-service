package com.example.demo;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
@Ignore
@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class WordHelperServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
