package com.example.sportradarbe.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.dto.TeamResponseDto;
import com.example.sportradarbe.entity.Team;
import com.example.sportradarbe.repository.TeamRepository;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamResponseDto> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public TeamResponseDto getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Team not found: " + id));
        return toDto(team);
    }

    private TeamResponseDto toDto(Team team) {
        return new TeamResponseDto(
                team.getTeamId(),
                team.getName(),
                team.getOfficialName(),
                team.getAbbreviation(),
                team.getTeamCountryCode());
    }
}
