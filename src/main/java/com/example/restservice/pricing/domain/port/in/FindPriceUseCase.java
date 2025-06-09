package com.example.restservice.pricing.domain.port.in;

import com.example.restservice.pricing.application.query.FindPriceQuery;
import com.example.restservice.pricing.domain.model.Price;

public interface FindPriceUseCase {

    Price findPrice(
            FindPriceQuery query
    );
}
