package com.example.sportradarbe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Represents one team's participation in an event.
 * role is typically "HOME" or "AWAY". score may be null if the event has not finished.
 */
public record EventTeamRequestDto(
        @NotNull(message = "teamId is required")
        @Positive(message = "teamId must be greater than 0")
        Long teamId,
        @NotBlank(message = "role is required")
        @Pattern(regexp = "HOME|AWAY", message = "role must be either HOME or AWAY")
        String role,
        @PositiveOrZero(message = "score must be zero or positive")
        Integer score
) {}
