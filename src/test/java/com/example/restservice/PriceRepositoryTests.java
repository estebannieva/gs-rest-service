package com.example.restservice;

import com.example.restservice.model.Price;
import com.example.restservice.repository.PriceRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AllArgsConstructor
class PriceRepositoryTests {

    private final PriceRepository priceRepository;

    @Test
    void shouldReturn_whenPriceSuccessful() {
        Integer brandId = 1;
        Integer productId = 35455;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        Optional<Price> results = priceRepository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        brandId, productId, date, date
                );

        assertThat(results)
                .isPresent()
                .get()
                .satisfies(price -> {
                    assertThat(price.getProductId()).isEqualTo(productId);
                    assertThat(price.getBrandId()).isEqualTo(brandId);
                    assertThat(price.getStartDate()).isBeforeOrEqualTo(date);
                    assertThat(price.getEndDate()).isAfterOrEqualTo(date);
                });
    }
}
