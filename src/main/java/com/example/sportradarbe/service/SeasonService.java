package com.example.sportradarbe.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.dto.SeasonResponseDto;
import com.example.sportradarbe.dto.SeasonResponseDto.LeagueDto;
import com.example.sportradarbe.entity.Season;
import com.example.sportradarbe.repository.SeasonRepository;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Transactional(readOnly = true)
    public List<SeasonResponseDto> getAllSeasons() {
        return seasonRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SeasonResponseDto getSeasonById(Long id) {
        Season season = seasonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Season not found: " + id));
        return toDto(season);
    }

    private SeasonResponseDto toDto(Season season) {
        LeagueDto leagueDto = new LeagueDto(
                season.getLeague().getLeagueId(),
                season.getLeague().getName(),
                season.getLeague().getCountry());
        return new SeasonResponseDto(
                season.getSeasonId(),
                season.getName(),
                season.getStartDate(),
                season.getEndDate(),
                leagueDto);
    }
}
