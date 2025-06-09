package com.example.restservice.pricing.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, UUID> {

    Optional<PriceEntity> findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Integer brandId,
            Integer productId,
            LocalDateTime fromDate,
            LocalDateTime toDate
    );
}
