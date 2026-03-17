package com.example.sportradarbe.dto;

public record LeagueResponseDto(
        Long leagueId,
        String name,
        String country,
        SportDto sport
) {
    public record SportDto(Long sportId, String name, String code) {}
}
