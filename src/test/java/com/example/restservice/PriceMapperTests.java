package com.example.restservice;

import com.example.restservice.dto.PriceResponse;
import com.example.restservice.mapper.PriceMapper;
import com.example.restservice.model.Price;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class PriceMapperTests {

    private final PriceMapper mapper = Mappers.getMapper(PriceMapper.class);

    @Test
    void shouldMapFullPriceToDto() {
        Price price = new Price();
        price.setId(UUID.randomUUID());
        price.setBrandId(1);
        price.setProductId(35455);
        price.setPriceList(2);
        price.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        price.setPriority(1);
        price.setAmount(new BigDecimal("25.45"));
        price.setCurrency("EUR");

        PriceResponse dto = mapper.toDto(price);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(price.getId());
        assertThat(dto.brandId()).isEqualTo(price.getBrandId());
        assertThat(dto.productId()).isEqualTo(price.getProductId());
        assertThat(dto.priceList()).isEqualTo(price.getPriceList());
        assertThat(dto.startDate()).isEqualTo(price.getStartDate());
        assertThat(dto.endDate()).isEqualTo(price.getEndDate());
        assertThat(dto.priority()).isEqualTo(price.getPriority());
        assertThat(dto.amount()).isEqualByComparingTo(price.getAmount());
        assertThat(dto.currency()).isEqualTo(price.getCurrency());
    }

    @Test
    void shouldMapToDto_withMinimalPrice() {
        Price price = new Price();
        price.setId(UUID.randomUUID());
        price.setAmount(BigDecimal.ZERO);
        price.setCurrency("USD");

        PriceResponse dto = mapper.toDto(price);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(price.getId());
        assertThat(dto.amount()).isEqualByComparingTo(price.getAmount());
        assertThat(dto.currency()).isEqualTo(price.getCurrency());
    }

    @Test
    void shouldReturnNull_whenPriceIsNull() {
        PriceResponse response = mapper.toDto(null);
        assertNull(response);
    }
}
