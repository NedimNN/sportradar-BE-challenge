package com.example.sportradarbe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.TeamResponseDto;
import com.example.sportradarbe.service.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamResponseDto> getAll() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public TeamResponseDto getOne(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }
}
