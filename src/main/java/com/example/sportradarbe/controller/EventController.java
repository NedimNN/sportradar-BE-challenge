package com.example.sportradarbe.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.EventRequestDto;
import com.example.sportradarbe.dto.EventResponseDto;
import com.example.sportradarbe.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * GET /api/events
     * Returns all events with full details (venue, status, season → league → sport, teams).
     */
    @GetMapping
    public List<EventResponseDto> getAll() {
        return eventService.getAllEvents();
    }

    /**
     * GET /api/events/{id}
     * Returns a single event by ID or 404 if not found.
     */
    @GetMapping("/{id}")
    public EventResponseDto getOne(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    /**
     * POST /api/events
     * Creates a new event. venueId, statusId, and seasonId must reference existing rows.
     * Returns 201 Created with the persisted event.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponseDto create(@RequestBody EventRequestDto request) {
        return eventService.createEvent(request);
    }
}
