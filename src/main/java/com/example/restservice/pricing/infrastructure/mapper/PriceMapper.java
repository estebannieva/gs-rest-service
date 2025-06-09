package com.example.restservice.pricing.infrastructure.mapper;

import com.example.restservice.pricing.domain.model.Price;
import com.example.restservice.pricing.domain.model.PriceId;
import com.example.restservice.pricing.infrastructure.persistence.PriceEntity;
import com.example.restservice.pricing.query.dto.PriceResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toDomain(PriceEntity entity);

    PriceResponse toResponse(Price price);

    default PriceId map(UUID id) {
        return PriceId.from(id);
    }

    default UUID map(PriceId priceId) {
        return priceId.toUUID();
    }
}
