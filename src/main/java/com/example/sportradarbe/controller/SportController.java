package com.example.sportradarbe.controller;

import java.util.List;

import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.SportResponseDto;
import com.example.sportradarbe.service.SportService;

@RestController
@Validated
@RequestMapping("/api/sports")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @GetMapping
    public List<SportResponseDto> getAll() {
        return sportService.getAllSports();
    }

    @GetMapping("/{id}")
    public SportResponseDto getOne(@PathVariable @Positive(message = "id must be greater than 0") Long id) {
        return sportService.getSportById(id);
    }
}
