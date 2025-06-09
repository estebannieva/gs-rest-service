package com.example.restservice.pricing.domain.model;

import java.util.Objects;
import java.util.UUID;

public record PriceId (UUID value) {

    public PriceId {
        Objects.requireNonNull(value, "Price ID value must not be null");
    }

    public static PriceId generate() {
        return new PriceId(UUID.randomUUID());
    }

    public static PriceId from(UUID id) {
        return new PriceId(id);
    }

    public UUID toUUID() {
        return value;
    }
}
