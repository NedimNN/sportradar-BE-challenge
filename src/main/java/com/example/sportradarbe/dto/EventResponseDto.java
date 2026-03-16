package com.example.sportradarbe.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record EventResponseDto(
        Long eventId,
        LocalDate eventDate,
        LocalTime timeUtc,
        String title,
        String description,
        VenueDto venue,
        StatusDto status,
        SeasonDto season,
        List<EventTeamDto> teams
) {
    public record VenueDto(Long venueId, String name, Integer capacity) {}

    public record StatusDto(Long eventStatusId, String code, String label) {}

    public record SportDto(Long sportId, String name, String code) {}

    public record LeagueDto(Long leagueId, String name, String country, SportDto sport) {}

    public record SeasonDto(Long seasonId, String name, LocalDate startDate, LocalDate endDate, LeagueDto league) {}

    public record TeamDto(Long teamId, String name, String abbreviation, String teamCountryCode) {}

    public record EventTeamDto(TeamDto team, String role, Integer score) {}
}
