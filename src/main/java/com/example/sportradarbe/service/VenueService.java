package com.example.sportradarbe.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.dto.VenueRequestDto;
import com.example.sportradarbe.dto.VenueResponseDto;
import com.example.sportradarbe.entity.Venue;
import com.example.sportradarbe.repository.VenueRepository;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<VenueResponseDto> getAllVenues() {
        return venueRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public VenueResponseDto getVenueById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Venue not found: " + id));
        return toDto(venue);
    }

    @Transactional
    public VenueResponseDto createVenue(VenueRequestDto request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name is required");
        }

        Venue venue = new Venue();
        venue.setName(request.name());
        venue.setCapacity(request.capacity());

        return toDto(venueRepository.save(venue));
    }

    private VenueResponseDto toDto(Venue venue) {
        return new VenueResponseDto(venue.getVenueId(), venue.getName(), venue.getCapacity());
    }
}
