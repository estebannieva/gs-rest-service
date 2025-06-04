package com.example.restservice.service;

import com.example.restservice.dto.PriceResponse;
import com.example.restservice.exception.PriceNotFoundException;
import com.example.restservice.mapper.PriceMapper;
import com.example.restservice.model.Price;
import com.example.restservice.repository.PriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository repository;
    private final PriceMapper mapper;

    @Override
    public PriceResponse getPrice(Integer productId, Integer brandId, LocalDateTime date) {
        Price price = repository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        brandId, productId, date, date
                )
                .orElseThrow(() -> new PriceNotFoundException(brandId, productId, date));

        return mapper.toDto(price);
    }
}
