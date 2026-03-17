package com.example.sportradarbe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.SeasonResponseDto;
import com.example.sportradarbe.service.SeasonService;

@RestController
@RequestMapping("/api/seasons")
public class SeasonController {

    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping
    public List<SeasonResponseDto> getAll() {
        return seasonService.getAllSeasons();
    }

    @GetMapping("/{id}")
    public SeasonResponseDto getOne(@PathVariable Long id) {
        return seasonService.getSeasonById(id);
    }
}
