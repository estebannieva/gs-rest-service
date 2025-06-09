package com.example.restservice.pricing.infrastructure.controller;

import com.example.restservice.pricing.infrastructure.controller.dto.ErrorResponse;
import com.example.restservice.pricing.application.query.FindPriceQuery;
import com.example.restservice.pricing.domain.model.Price;
import com.example.restservice.pricing.domain.port.in.FindPriceUseCase;
import com.example.restservice.pricing.infrastructure.mapper.PriceMapper;
import com.example.restservice.pricing.query.dto.PriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
@Tag(
        name = "Price Controller",
        description = "Controller for pricing management."
)
@AllArgsConstructor
public class PriceController {

    private final FindPriceUseCase useCase;
    private final PriceMapper mapper;

    @GetMapping
    @Operation(
            summary = "Get price based on query parameters",
            description = "Returns the price in effect for the given brand ID, product ID, and date." +
                    "If multiple prices are valid within the date range, the one with the highest priority is returned."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Price found successfully.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad request due to invalid parameters.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                {
                                  "timestamp": "2025-05-29T12:00:00",
                                  "status": 400,
                                  "error": "Invalid parameter type",
                                  "message": "Invalid value for parameter: 'date'"
                                }
                            """
                    ),
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "No price was found for the given parameters.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                {
                                  "timestamp": "2025-05-29T12:30:00",
                                  "status": 404,
                                  "error": "Price not found",
                                  "message": "No price found for brandId=9, productId=99999, date=9999-01-01T10:00"
                                }
                            """
                    ),
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    public PriceResponse getPrice(
            @Parameter(description = "Brand ID", example = "1", required = true)
            @RequestParam
            Integer brandId,

            @Parameter(description = "Product ID", example = "35455", required = true)
            @RequestParam
            Integer productId,

            @Parameter(
                    description = "Application date-time in ISO format (e.g., 2020-06-14T16:00:00)",
                    example = "2020-06-14T16:00:00",
                    required = true
            )
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime date
    ) {
        FindPriceQuery query = new FindPriceQuery(brandId, productId, date);
        Price price = useCase.findPrice(query);
        return mapper.toResponse(price);
    }
}
