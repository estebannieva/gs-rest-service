package com.example.restservice.infrastructure.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_URL = "/api/prices";

    @Test
    @DisplayName("Should return the expected price when parameters are valid")
    void shouldReturnExpectedPriceForValidParams() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("date", "2020-06-14T16:00:00")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.amount", is(25.45)))
                .andExpect(jsonPath("$.currency", is("EUR")))
                .andExpect(jsonPath("$.priceList", is(2)));
    }

    @Test
    @DisplayName("Should return 404 when no matching price is available")
    void shouldReturnNotFoundIfNoPrice() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("date", "1999-01-01T00:00:00")
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Price not found")));
    }

    @Test
    @DisplayName("Should return 400 if parameters are invalid")
    void shouldReturnBadRequestForInvalidParams() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("brandId", "INVALID")
                        .param("productId", "35455")
                        .param("date", "2020-06-14T16:00:00")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Invalid parameter type")));
    }
}
