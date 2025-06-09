package com.example.restservice.pricing.application.query;

import com.example.restservice.pricing.domain.exception.PriceNotFoundException;
import com.example.restservice.pricing.domain.model.Price;
import com.example.restservice.pricing.domain.port.in.FindPriceUseCase;
import com.example.restservice.pricing.domain.port.out.PriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindPriceHandler implements FindPriceUseCase {

    private final PriceRepository repository;

    @Override
    public Price findPrice(
            FindPriceQuery query
    ) {
        return repository.findPrice(
                query.brandId(),
                query.productId(),
                query.date()
        ).orElseThrow(() -> new PriceNotFoundException(query));
    }
}
