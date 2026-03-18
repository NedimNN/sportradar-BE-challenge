package com.example.sportradarbe.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.VenueRequestDto;
import com.example.sportradarbe.dto.VenueResponseDto;
import com.example.sportradarbe.service.VenueService;

@RestController
@Validated
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public List<VenueResponseDto> getAll() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public VenueResponseDto getOne(@PathVariable @Positive(message = "id must be greater than 0") Long id) {
        return venueService.getVenueById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueResponseDto create(@Valid @RequestBody VenueRequestDto request) {
        return venueService.createVenue(request);
    }
}
