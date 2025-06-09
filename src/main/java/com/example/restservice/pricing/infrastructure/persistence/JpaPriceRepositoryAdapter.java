package com.example.restservice.pricing.infrastructure.persistence;

import com.example.restservice.pricing.domain.model.Price;
import com.example.restservice.pricing.domain.port.out.PriceRepository;
import com.example.restservice.pricing.infrastructure.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaPriceRepositoryAdapter implements PriceRepository {

    private final JpaPriceRepository repository;
    private final PriceMapper mapper;

    @Override
    public Optional<Price> findPrice(
            Integer brandId,
            Integer productId,
            LocalDateTime date
    ) {
        return repository.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                brandId,
                productId,
                date,
                date
        ).map(mapper::toDomain);
    }
}
