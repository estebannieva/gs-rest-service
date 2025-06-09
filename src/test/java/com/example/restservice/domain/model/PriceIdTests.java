package com.example.restservice.domain.model;

import com.example.restservice.pricing.domain.model.PriceId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PriceIdTests {

    @Test
    @DisplayName("Should create PriceId with valid UUID")
    void shouldCreatePriceIdWithValidUUID() {
        UUID uuid = UUID.randomUUID();
        PriceId priceId = new PriceId(uuid);

        assertEquals(uuid, priceId.value());
    }

    @Test
    @DisplayName("Should throw NullPointerException when UUID is null")
    void shouldThrowWhenUUIDIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new PriceId(null));
        assertEquals("Price ID value must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should generate new PriceId with non-null UUID")
    void shouldGeneratePriceId() {
        PriceId generated = PriceId.generate();

        assertNotNull(generated);
        assertNotNull(generated.value());
    }

    @Test
    @DisplayName("Should respect equals and hashCode")
    void shouldRespectEquality() {
        UUID uuid = UUID.randomUUID();
        PriceId id1 = new PriceId(uuid);
        PriceId id2 = new PriceId(uuid);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }
}
