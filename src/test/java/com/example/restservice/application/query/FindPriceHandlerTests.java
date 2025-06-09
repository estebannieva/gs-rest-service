package com.example.restservice.application.query;

import com.example.restservice.pricing.application.query.FindPriceHandler;
import com.example.restservice.pricing.application.query.FindPriceQuery;
import com.example.restservice.pricing.domain.exception.PriceNotFoundException;
import com.example.restservice.pricing.domain.model.Price;
import com.example.restservice.pricing.domain.port.out.PriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FindPriceHandlerTests {

    @Autowired
    private PriceRepository priceRepository;

    private static final Integer BRAND_ID = 1;
    private static final Integer PRODUCT_ID = 35455;
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 6, 14, 16, 0); // matches two, but one has higher priority

    @Test
    @DisplayName("Should return highest priority price for given date")
    void shouldReturnHighestPriorityPrice() {
        FindPriceHandler handler = new FindPriceHandler(priceRepository);

        FindPriceQuery query = new FindPriceQuery(BRAND_ID, PRODUCT_ID, DATE);
        Price result = handler.findPrice(query);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(BRAND_ID, result.brandId());
        assertEquals(PRODUCT_ID, result.productId());
        assertEquals(new BigDecimal("25.45"), result.amount());
        assertEquals("EUR", result.currency());
        assertEquals(1, result.priority());
    }

    @Test
    @DisplayName("Should throw PriceNotFoundException when price is not found")
    void shouldThrowExceptionWhenNoPriceFound() {
        FindPriceHandler handler = new FindPriceHandler(priceRepository);

        FindPriceQuery query = new FindPriceQuery(9, 99999, LocalDateTime.of(9999, 1, 1, 0, 0));
        assertThrows(PriceNotFoundException.class, () -> handler.findPrice(query));
    }
}
