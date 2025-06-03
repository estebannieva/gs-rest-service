package com.example.restservice.controller;

import com.example.restservice.dto.ErrorResponse;
import com.example.restservice.dto.PriceResponse;
import com.example.restservice.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        description = "REST API for querying product pricing information based on brand, product, and date."
)
public class PriceController {

    private final PriceService service;

    public PriceController(PriceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Get applicable price",
            description = "Returns the applicable price based on product ID, brand ID and application date."
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
            description = "No applicable price found for the given parameters.",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                {
                                  "timestamp": "2025-05-29T12:30:00",
                                  "status": 404,
                                  "error": "Price not found",
                                  "message": "No price found for productId=35455, brandId=1, date=2020-06-14T16:00"
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
                    description = "Application date-time in ISO format (e.g., 2020-06-14T10:00:00)",
                    example = "2020-06-14T10:00:00",
                    required = true
            )
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime date
    ) {
        return service.getPrice(productId, brandId, date);
    }
}
