package com.example.sportradarbe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.LeagueResponseDto;
import com.example.sportradarbe.service.LeagueService;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public List<LeagueResponseDto> getAll() {
        return leagueService.getAllLeagues();
    }

    @GetMapping("/{id}")
    public LeagueResponseDto getOne(@PathVariable Long id) {
        return leagueService.getLeagueById(id);
    }
}
