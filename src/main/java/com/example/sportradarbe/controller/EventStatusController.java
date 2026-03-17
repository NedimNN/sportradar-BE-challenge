package com.example.sportradarbe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportradarbe.dto.EventStatusResponseDto;
import com.example.sportradarbe.service.EventStatusService;

@RestController
@RequestMapping("/api/statuses")
public class EventStatusController {

    private final EventStatusService eventStatusService;

    public EventStatusController(EventStatusService eventStatusService) {
        this.eventStatusService = eventStatusService;
    }

    @GetMapping
    public List<EventStatusResponseDto> getAll() {
        return eventStatusService.getAllStatuses();
    }

    @GetMapping("/{id}")
    public EventStatusResponseDto getOne(@PathVariable Long id) {
        return eventStatusService.getStatusById(id);
    }
}
