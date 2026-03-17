package com.example.sportradarbe.dto;

public record TeamResponseDto(
        Long teamId,
        String name,
        String officialName,
        String abbreviation,
        String teamCountryCode
) {}
