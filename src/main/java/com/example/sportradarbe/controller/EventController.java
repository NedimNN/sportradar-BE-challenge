package com.example.sportradarbe.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.EventRequestDto;
import com.example.sportradarbe.dto.EventResponseDto;
import com.example.sportradarbe.service.EventService;

@RestController
@Validated
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * GET /api/events
     * Returns all events with full details (venue, status, season → league → sport, teams).
     * Supports optional filtering by sportId, leagueId, and date. If a filter is not provided, it is ignored.
     */
    @GetMapping
    public List<EventResponseDto> getAll(
        @RequestParam(required = false) @Positive(message = "sportId must be greater than 0") Long sportId,
        @RequestParam(required = false) @Positive(message = "leagueId must be greater than 0") Long leagueId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return eventService.getAllEvents(sportId, leagueId, date);
    }

    /**
     * GET /api/events/{id}
     * Returns a single event by ID or 404 if not found.
     */
    @GetMapping("/{id}")
    public EventResponseDto getOne(@PathVariable @Positive(message = "id must be greater than 0") Long id) {
        return eventService.getEventById(id);
    }

    /**
     * POST /api/events
     * Creates a new event. venueId, statusId, and seasonId must reference existing rows.
     * Returns 201 Created with the persisted event.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDto create(@Valid @RequestBody EventRequestDto request) {
        return eventService.createEvent(request);
    }
}
