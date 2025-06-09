package com.example.restservice.domain.model;

import com.example.restservice.pricing.domain.model.Price;
import com.example.restservice.pricing.domain.model.PriceId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PriceTests {

    private static final Integer BRAND_ID = 1;
    private static final Integer PRODUCT_ID = 35455;
    private static final LocalDateTime START_DATE = LocalDateTime.of(2020, 6, 14, 15, 0);
    private static final LocalDateTime END_DATE = LocalDateTime.of(2020, 6, 14, 18, 30, 0);
    private static final Integer PRICE_LIST = 2;
    private static final Integer PRIORITY = 1;
    private static final BigDecimal AMOUNT = new BigDecimal("25.45");
    private static final String CURRENCY = "EUR";

    @Test
    @DisplayName("Should return correct priority value")
    void shouldReturnPriority() {
        Price price = new Price(
                new PriceId(UUID.randomUUID()),
                BRAND_ID,
                PRODUCT_ID,
                START_DATE,
                END_DATE,
                PRICE_LIST,
                PRIORITY,
                AMOUNT,
                CURRENCY
        );

        Integer result = price.priority();
        assertEquals(PRIORITY, result);
    }

    @Test
    @DisplayName("Should create Price instance with all fields correctly assigned")
    void shouldCreatePriceSuccessfully() {
        Price price = new Price(
                new PriceId(UUID.randomUUID()),
                BRAND_ID,
                PRODUCT_ID,
                START_DATE,
                END_DATE,
                PRICE_LIST,
                PRIORITY,
                AMOUNT,
                CURRENCY
        );

        assertNotNull(price.id());
        assertEquals(BRAND_ID, price.brandId());
        assertEquals(PRODUCT_ID, price.productId());
        assertEquals(START_DATE, price.startDate());
        assertEquals(END_DATE, price.endDate());
        assertEquals(PRICE_LIST, price.priceList());
        assertEquals(PRIORITY, price.priority());
        assertEquals(AMOUNT, price.amount());
        assertEquals(CURRENCY, price.currency());
    }

    @Nested
    @DisplayName("Validation for null arguments")
    class NullValidation {

        @Test
        @DisplayName("Should throw when id is null")
        void shouldThrowWhenIdIsNull() {
            assertThrows(NullPointerException.class, () -> new Price(
                    null,
                    BRAND_ID,
                    PRODUCT_ID,
                    START_DATE,
                    END_DATE,
                    PRICE_LIST,
                    PRIORITY,
                    AMOUNT,
                    CURRENCY
            ));
        }

        @Test
        @DisplayName("Should throw when currency is null")
        void shouldThrowWhenCurrencyIsNull() {
            PriceId id = new PriceId(UUID.randomUUID());

            assertThrows(NullPointerException.class, () -> new Price(
                    id,
                    BRAND_ID,
                    PRODUCT_ID,
                    START_DATE,
                    END_DATE,
                    PRICE_LIST,
                    PRIORITY,
                    AMOUNT,
                    null
            ));
        }
    }
}
