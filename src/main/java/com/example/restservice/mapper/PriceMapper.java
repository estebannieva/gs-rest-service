package com.example.restservice.mapper;

import com.example.restservice.dto.PriceResponse;
import com.example.restservice.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceResponse toDto(Price price);
}
