package com.example.sportradarbe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record VenueRequestDto(
        @NotBlank(message = "name is required")
        @Size(max = 200, message = "name must be at most 200 characters")
        String name,
        @PositiveOrZero(message = "capacity must be zero or positive")
        Integer capacity
) {}
