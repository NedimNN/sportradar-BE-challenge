package com.example.sportradarbe.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Request body for creating a new event.
 * venueId, statusId, and seasonId must reference existing rows in their respective tables.
 */
public record EventRequestDto(
        @NotBlank(message = "title is required")
        @Size(max = 200, message = "title must be at most 200 characters")
        String title,
        @Size(max = 5000, message = "description must be at most 5000 characters")
        String description,
        LocalDate eventDate,
        LocalTime timeUtc,
        @NotNull(message = "sportId is required")
        @Positive(message = "sportId must be greater than 0")
        Long sportId,
        @NotNull(message = "seasonId is required")
        @Positive(message = "seasonId must be greater than 0")
        Long seasonId,
        @NotNull(message = "venueId is required")
        @Positive(message = "venueId must be greater than 0")
        Long venueId,
        @NotNull(message = "statusId is required")
        @Positive(message = "statusId must be greater than 0")
        Long statusId,
        @Valid
        List<EventTeamRequestDto> teams
) {}
