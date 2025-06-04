package com.example.restservice;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@AllArgsConstructor
class RestServiceApplicationTests {

    private final ApplicationContext context;

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
