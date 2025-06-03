package com.example.restservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
class RestServiceApplicationTests {

    private final ApplicationContext context;

    public RestServiceApplicationTests(ApplicationContext context) {
        this.context = context;
    }

    @Test
    void applicationContext_startsSuccessfully() {
        assertThat(context).isNotNull();
    }

    @Test
    void mainMethod_runsWithoutExceptions() {
        assertThatCode(() -> RestServiceApplication.main(new String[]{}))
                .doesNotThrowAnyException();
    }
}
