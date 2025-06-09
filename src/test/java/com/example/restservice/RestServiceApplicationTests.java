package com.example.restservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
class RestServiceApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void applicationContextStartsSuccessfully() {
        assertThat(context).isNotNull();
    }

    @Test
    void mainMethodRunsWithoutExceptions() {
        assertThatCode(() -> RestServiceApplication.main(new String[]{}))
                .doesNotThrowAnyException();
    }
}
