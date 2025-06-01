package com.example.restservice.exception;

import com.example.restservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidParam(MethodArgumentTypeMismatchException ex) {
        return new ErrorResponse(
                400,
                "Invalid parameter type",
                "Invalid value for parameter: '" + ex.getName() + "'"
        );
    }

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePriceNotFound(PriceNotFoundException ex) {
        return new ErrorResponse(
                404,
                "Price not found", ex.getMessage()
        );
    }
}
