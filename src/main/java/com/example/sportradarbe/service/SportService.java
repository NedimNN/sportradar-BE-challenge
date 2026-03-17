package com.example.sportradarbe.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.dto.SportResponseDto;
import com.example.sportradarbe.entity.Sport;
import com.example.sportradarbe.repository.SportRepository;

@Service
public class SportService {

    private final SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public List<SportResponseDto> getAllSports() {
        return sportRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public SportResponseDto getSportById(Long id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Sport not found: " + id));
        return toDto(sport);
    }

    private SportResponseDto toDto(Sport sport) {
        return new SportResponseDto(sport.getSportId(), sport.getName(), sport.getCode());
    }
}
