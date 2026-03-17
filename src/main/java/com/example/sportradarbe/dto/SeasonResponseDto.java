package com.example.sportradarbe.dto;

import java.time.LocalDate;

public record SeasonResponseDto(
        Long seasonId,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        LeagueDto league
) {
    public record LeagueDto(Long leagueId, String name, String country) {}
}
