package com.example.sportradarbe.dto;

/**
 * Represents one team's participation in an event.
 * role is typically "HOME" or "AWAY". score may be null if the event has not finished.
 */
public record EventTeamRequestDto(
        Long teamId,
        String role,
        Integer score
) {}
