package com.example.restservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private final String baseUrl = "/api/prices";

    @Test
    void shouldReturn_priceAt10am_onJune14_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(35.50)))
                .andExpect(jsonPath("$.priceList", is(1)));
    }

    @Test
    void shouldReturn_priceAt16pm_onJune14_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(25.45)))
                .andExpect(jsonPath("$.priceList", is(2)));
    }

    @Test
    void shouldReturn_priceAt21pm_onJune14_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(35.50)))
                .andExpect(jsonPath("$.priceList", is(1)));
    }

    @Test
    void shouldReturn_priceAt10am_onJune15_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(30.50)))
                .andExpect(jsonPath("$.priceList", is(3)));
    }

    @Test
    void shouldReturn_priceAt21pm_onJune16_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(38.95)))
                .andExpect(jsonPath("$.priceList", is(4)));
    }

    @Test
    void shouldReturn404_whenNoPriceFound() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "99999")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Price not found")));
    }
}
