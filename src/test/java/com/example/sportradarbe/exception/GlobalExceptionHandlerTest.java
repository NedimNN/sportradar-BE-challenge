package com.example.sportradarbe.exception;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.controller.EventController;
import com.example.sportradarbe.service.EventService;

@WebMvcTest(EventController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Test
    void createEvent_withInvalidBody_returnsValidationErrors() throws Exception {
        String body = """
                {
                  "title": "",
                  "sportId": null,
                  "seasonId": 1,
                  "venueId": 1,
                  "statusId": 1,
                  "teams": [
                    {"teamId": null, "role": "", "score": -1}
                  ]
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.path").value("/api/events"))
                .andExpect(jsonPath("$.errors[*].field", hasItem("title")))
                .andExpect(jsonPath("$.errors[*].field", hasItem("sportId")))
                .andExpect(jsonPath("$.errors[*].field", hasItem("teams[0].teamId")))
                .andExpect(jsonPath("$.errors[*].field", hasItem("teams[0].role")))
                .andExpect(jsonPath("$.errors[*].field", hasItem("teams[0].score")));
    }

    @Test
    void createEvent_withMalformedJson_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid-json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Malformed JSON request body"))
                .andExpect(jsonPath("$.path").value("/api/events"));
    }

    @Test
    void getOne_withNegativeId_returnsValidationError() throws Exception {
        mockMvc.perform(get("/api/events/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.errors[*].field", hasItem(containsString("getOne.id"))))
                .andExpect(jsonPath("$.errors[*].message", hasItem("id must be greater than 0")));
    }

    @Test
    void getAll_withInvalidSportId_returnsValidationError() throws Exception {
        mockMvc.perform(get("/api/events").param("sportId", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.errors[*].field", hasItem(containsString("getAll.sportId"))))
                .andExpect(jsonPath("$.errors[*].message", hasItem("sportId must be greater than 0")));
    }

    @Test
    void getOne_whenServiceThrowsNotFound_returns404WithMessage() throws Exception {
        when(eventService.getEventById(999L)).thenThrow(
                new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Event not found: 999")
        );

        mockMvc.perform(get("/api/events/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Event not found: 999"))
                .andExpect(jsonPath("$.path").value("/api/events/999"));
    }

    @Test
    void createEvent_whenServiceThrowsDataIntegrityViolation_returnsConflict() throws Exception {
        when(eventService.createEvent(any()))
                .thenThrow(new DataIntegrityViolationException("constraint violation"));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Arsenal vs Chelsea",
                                  "description": "Premier League fixture",
                                  "eventDate": "2026-04-12",
                                  "timeUtc": "15:30:00",
                                  "sportId": 1,
                                  "seasonId": 1,
                                  "venueId": 1,
                                  "statusId": 1,
                                  "teams": []
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Request conflicts with current data constraints"))
                .andExpect(jsonPath("$.path").value("/api/events"));
    }

    @Test
    void getOne_whenServiceThrowsUnexpectedException_returnsInternalServerError() throws Exception {
        when(eventService.getEventById(anyLong()))
                .thenThrow(new IllegalStateException("unexpected"));

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Unexpected server error"))
                .andExpect(jsonPath("$.path").value("/api/events/1"));
    }

    @Test
    void getAll_withValidFilters_passesThrough() throws Exception {
        when(eventService.getAllEvents(anyLong(), anyLong(), any(LocalDate.class))).thenReturn(List.of());

        mockMvc.perform(get("/api/events")
                        .param("sportId", "1")
                        .param("leagueId", "2")
                        .param("date", "2026-04-12"))
                .andExpect(status().isOk());
    }
}
