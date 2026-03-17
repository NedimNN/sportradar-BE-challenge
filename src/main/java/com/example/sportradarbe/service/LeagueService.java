package com.example.sportradarbe.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.dto.LeagueResponseDto;
import com.example.sportradarbe.dto.LeagueResponseDto.SportDto;
import com.example.sportradarbe.entity.League;
import com.example.sportradarbe.repository.LeagueRepository;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Transactional(readOnly = true)
    public List<LeagueResponseDto> getAllLeagues() {
        return leagueRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public LeagueResponseDto getLeagueById(Long id) {
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "League not found: " + id));
        return toDto(league);
    }

    private LeagueResponseDto toDto(League league) {
        SportDto sportDto = new SportDto(
                league.getSport().getSportId(),
                league.getSport().getName(),
                league.getSport().getCode());
        return new LeagueResponseDto(
                league.getLeagueId(),
                league.getName(),
                league.getCountry(),
                sportDto);
    }
}
