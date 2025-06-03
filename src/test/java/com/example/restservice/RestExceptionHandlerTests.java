package com.example.restservice;

import com.example.restservice.controller.PriceController;
import com.example.restservice.dto.ErrorResponse;
import com.example.restservice.exception.PriceNotFoundException;
import com.example.restservice.exception.RestExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RestExceptionHandlerTests {

    private final RestExceptionHandler handler = new RestExceptionHandler();

    @Test
    void handlePriceNotFound_returnsExpectedErrorResponse() {
        Integer productId = 35455;
        Integer brandId = 1;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        PriceNotFoundException ex = new PriceNotFoundException(brandId, productId, date);

        ErrorResponse response = handler.handlePriceNotFound(ex);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(404);
        assertThat(response.error()).isEqualTo("Price not found");

        String expectedMessage = "No price found for productId=35455, brandId=1, date=2020-06-14T10:00";
        assertThat(response.message()).isEqualTo(expectedMessage);

        assertThat(response.timestamp()).isNotNull();
    }

    @Test
    void handleInvalidParam_returnsExpectedErrorResponse() throws NoSuchMethodException {
        MethodArgumentTypeMismatchException ex = buildDateParamTypeMismatchException();

        ErrorResponse response = handler.handleInvalidParam(ex);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(400);
        assertThat(response.error()).isEqualTo("Invalid parameter type");
        assertThat(response.message()).isEqualTo("Invalid value for parameter: 'date'");
        assertThat(response.timestamp()).isNotNull();
    }

    private MethodArgumentTypeMismatchException buildDateParamTypeMismatchException() throws NoSuchMethodException {
        Method method = PriceController.class.getMethod(
                "getPrice", Integer.class, Integer.class, LocalDateTime.class
        );
        MethodParameter methodParameter = new MethodParameter(method, 2); // parámetro "date"
        Throwable cause = new IllegalArgumentException("Invalid input");

        return new MethodArgumentTypeMismatchException(
                "", String.class, "date", methodParameter, cause
        );
    }
}
