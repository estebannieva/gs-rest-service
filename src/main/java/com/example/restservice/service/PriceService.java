package com.example.restservice.service;

import com.example.restservice.dto.PriceResponse;

import java.time.LocalDateTime;

public interface PriceService {

    PriceResponse getPrice(Integer productId, Integer brandId, LocalDateTime date);
}
