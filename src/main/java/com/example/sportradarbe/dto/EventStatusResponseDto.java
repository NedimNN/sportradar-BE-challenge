package com.example.sportradarbe.dto;

public record EventStatusResponseDto(
        Long eventStatusId,
        String code,
        String label,
        String description
) {}
