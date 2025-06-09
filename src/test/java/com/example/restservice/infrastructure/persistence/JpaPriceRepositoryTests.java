package com.example.restservice.infrastructure.persistence;

import com.example.restservice.pricing.infrastructure.persistence.JpaPriceRepository;
import com.example.restservice.pricing.infrastructure.persistence.PriceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaPriceRepositoryTests {

    @Autowired
    private JpaPriceRepository jpaPriceRepository;

    private static final Integer PRODUCT_ID = 35455;
    private static final Integer BRAND_ID = 1;

    @Test
    @DisplayName("Should return highest priority price when multiple prices match date")
    void shouldReturnHighestPriorityPrice() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0); // matches records 1 and 2 → should return record 2 (priority 1)

        Optional<PriceEntity> result = jpaPriceRepository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        BRAND_ID, PRODUCT_ID, date, date
                );

        assertTrue(result.isPresent());
        assertNotNull(result.get().getId());
        assertEquals(1, result.get().getPriority());
    }

    @Test
    @DisplayName("Should return empty when no price matches criteria")
    void shouldReturnEmptyWhenNoMatch() {
        Optional<PriceEntity> result = jpaPriceRepository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        9, 99999,
                        LocalDateTime.of(9999, 1, 1, 0, 0),
                        LocalDateTime.of(9999, 1, 1, 0, 0)
                );

        assertTrue(result.isEmpty());
    }
}
