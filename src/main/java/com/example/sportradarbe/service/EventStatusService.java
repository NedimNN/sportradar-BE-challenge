package com.example.sportradarbe.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.dto.EventStatusResponseDto;
import com.example.sportradarbe.entity.EventStatus;
import com.example.sportradarbe.repository.EventStatusRepository;

@Service
public class EventStatusService {

    private final EventStatusRepository eventStatusRepository;

    public EventStatusService(EventStatusRepository eventStatusRepository) {
        this.eventStatusRepository = eventStatusRepository;
    }

    public List<EventStatusResponseDto> getAllStatuses() {
        return eventStatusRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public EventStatusResponseDto getStatusById(Long id) {
        EventStatus status = eventStatusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "EventStatus not found: " + id));
        return toDto(status);
    }

    private EventStatusResponseDto toDto(EventStatus status) {
        return new EventStatusResponseDto(
                status.getEventStatusId(),
                status.getCode(),
                status.getLabel(),
                status.getDescription());
    }
}
