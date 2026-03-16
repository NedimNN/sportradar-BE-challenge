package com.example.sportradarbe.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Request body for creating a new event.
 * venueId, statusId, and seasonId must reference existing rows in their respective tables.
 */
public record EventRequestDto(
        String title,
        String description,
        LocalDate eventDate,
        LocalTime timeUtc,
        Long venueId,
        Long statusId,
        Long seasonId,
        List<EventTeamRequestDto> teams
) {}
