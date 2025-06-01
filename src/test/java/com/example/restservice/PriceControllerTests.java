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
    void test1_requestAt10am_onJune14_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(35.50)))
                .andExpect(jsonPath("$.priceList", is(1)));
    }

    @Test
    void test2_requestAt16pm_onJune14_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(25.45)))
                .andExpect(jsonPath("$.priceList", is(2)));
    }

    @Test
    void test3_requestAt21pm_onJune14_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(35.50)))
                .andExpect(jsonPath("$.priceList", is(1)));
    }

    @Test
    void test4_requestAt10am_onJune15_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(30.50)))
                .andExpect(jsonPath("$.priceList", is(3)));
    }

    @Test
    void test5_requestAt21pm_onJune16_product35455_brand1() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("date", "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(38.95)))
                .andExpect(jsonPath("$.priceList", is(4)));
    }
}
