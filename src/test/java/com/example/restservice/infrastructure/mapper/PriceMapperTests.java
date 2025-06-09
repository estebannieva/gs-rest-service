package com.example.restservice.infrastructure.mapper;

import com.example.restservice.pricing.domain.model.Price;
import com.example.restservice.pricing.domain.model.PriceId;
import com.example.restservice.pricing.infrastructure.mapper.PriceMapper;
import com.example.restservice.pricing.infrastructure.persistence.PriceEntity;
import com.example.restservice.pricing.query.dto.PriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PriceMapperTests {

    private final PriceMapper mapper = Mappers.getMapper(PriceMapper.class);

    @Test
    @DisplayName("Map entity to domain")
    void shouldMapToDomain() {
        PriceEntity entity = new PriceEntity();
        entity.setId(UUID.randomUUID());
        entity.setBrandId(1);
        entity.setProductId(35455);
        entity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        entity.setPriceList(1);
        entity.setPriority(0);
        entity.setAmount(new BigDecimal("35.50"));
        entity.setCurrency("EUR");

        Price price = mapper.toDomain(entity);

        assertEquals(entity.getId(), price.id().toUUID());
        assertEquals(entity.getBrandId(), price.brandId());
        assertEquals(entity.getProductId(), price.productId());
        assertEquals(entity.getStartDate(), price.startDate());
        assertEquals(entity.getEndDate(), price.endDate());
        assertEquals(entity.getPriceList(), price.priceList());
        assertEquals(entity.getPriority(), price.priority());
        assertEquals(entity.getAmount(), price.amount());
        assertEquals(entity.getCurrency(), price.currency());
    }

    @Test
    @DisplayName("Map domain to response")
    void shouldMapToResponse() {
        Price price = new Price(
                new PriceId(UUID.randomUUID()),
                1,
                35455,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2,
                1,
                new BigDecimal("25.45"),
                "EUR"
        );

        PriceResponse response = mapper.toResponse(price);

        assertNotNull(response);
        assertEquals(price.id().toUUID(), response.id());
        assertEquals(price.brandId(), response.brandId());
        assertEquals(price.productId(), response.productId());
        assertEquals(price.startDate(), response.startDate());
        assertEquals(price.endDate(), response.endDate());
        assertEquals(price.priceList(), response.priceList());
        assertEquals(price.priority(), response.priority());
        assertEquals(price.amount(), response.amount());
        assertEquals(price.currency(), response.currency());
    }

    @Test
    @DisplayName("Return null when entity is null")
    void shouldReturnNullWhenPriceIsNullForEntity() {
        Price response = mapper.toDomain(null);
        assertNull(response);
    }

    @Test
    @DisplayName("Return null when price is null")
    void shouldReturnNullWhenPriceIsNullForToResponse() {
        PriceResponse response = mapper.toResponse(null);
        assertNull(response);
    }

    @Test
    @DisplayName("Map UUID to PriceId")
    void shouldMapUuidToPriceId() {
        UUID uuid = UUID.randomUUID();
        PriceId priceId = mapper.map(uuid);

        assertNotNull(priceId);
        assertEquals(uuid, priceId.toUUID());
    }

    @Test
    @DisplayName("Map PriceId to UUID")
    void shouldMapPriceIdToUuid() {
        UUID uuid = UUID.randomUUID();
        PriceId priceId = PriceId.from(uuid);
        UUID result = mapper.map(priceId);

        assertEquals(uuid, result);
    }
}
