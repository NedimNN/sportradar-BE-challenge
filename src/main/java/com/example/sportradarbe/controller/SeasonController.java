package com.example.sportradarbe.controller;

import java.util.List;

import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.SeasonResponseDto;
import com.example.sportradarbe.service.SeasonService;

@RestController
@Validated
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
    public SeasonResponseDto getOne(@PathVariable @Positive(message = "id must be greater than 0") Long id) {
        return seasonService.getSeasonById(id);
    }
}
