package com.example.sportradarbe.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.dto.EventRequestDto;
import com.example.sportradarbe.dto.EventResponseDto;
import com.example.sportradarbe.dto.EventTeamRequestDto;
import com.example.sportradarbe.entity.Event;
import com.example.sportradarbe.entity.EventStatus;
import com.example.sportradarbe.entity.EventTeam;
import com.example.sportradarbe.entity.League;
import com.example.sportradarbe.entity.Season;
import com.example.sportradarbe.entity.Sport;
import com.example.sportradarbe.entity.Team;
import com.example.sportradarbe.entity.Venue;
import com.example.sportradarbe.repository.EventRepository;
import com.example.sportradarbe.repository.EventStatusRepository;
import com.example.sportradarbe.repository.SeasonRepository;
import com.example.sportradarbe.repository.TeamRepository;
import com.example.sportradarbe.repository.VenueRepository;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private EventStatusRepository eventStatusRepository;

    @Mock
    private SeasonRepository seasonRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_withValidRequest_persistsAndReturnsDetailedDto() {
        EventRequestDto request = new EventRequestDto(
                "Arsenal vs Chelsea",
                "Premier League fixture",
                LocalDate.of(2026, 4, 12),
                LocalTime.of(15, 30),
                1L,
                1L,
                1L,
                List.of(
                        new EventTeamRequestDto(10L, "HOME", null),
                        new EventTeamRequestDto(11L, "AWAY", null)
                )
        );

        Venue venue = new Venue();
        venue.setVenueId(1L);
        venue.setName("Old Trafford");
        venue.setCapacity(74310);

        EventStatus status = new EventStatus();
        status.setEventStatusId(1L);
        status.setCode("SCHEDULED");
        status.setLabel("Scheduled");

        Sport sport = new Sport();
        sport.setSportId(1L);
        sport.setName("Football");
        sport.setCode("FOOTBALL");

        League league = new League();
        league.setLeagueId(1L);
        league.setName("Premier League");
        league.setCountry("England");
        league.setSport(sport);

        Season season = new Season();
        season.setSeasonId(1L);
        season.setName("Premier League 2024/25");
        season.setStartDate(LocalDate.of(2024, 8, 16));
        season.setEndDate(LocalDate.of(2025, 5, 25));
        season.setLeague(league);

        Team homeTeam = new Team();
        homeTeam.setTeamId(10L);
        homeTeam.setName("Arsenal");
        homeTeam.setAbbreviation("ARS");
        homeTeam.setTeamCountryCode("GB");

        Team awayTeam = new Team();
        awayTeam.setTeamId(11L);
        awayTeam.setName("Chelsea");
        awayTeam.setAbbreviation("CHE");
        awayTeam.setTeamCountryCode("GB");

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(eventStatusRepository.findById(1L)).thenReturn(Optional.of(status));
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(teamRepository.findById(10L)).thenReturn(Optional.of(homeTeam));
        when(teamRepository.findById(11L)).thenReturn(Optional.of(awayTeam));

        Event savedEvent = buildDetailedSavedEvent(100L, request, venue, status, season, homeTeam, awayTeam);
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> {
            Event event = invocation.getArgument(0);
            event.setEventId(100L);
            return event;
        });
        when(eventRepository.findByIdWithDetails(100L)).thenReturn(Optional.of(savedEvent));

        EventResponseDto response = eventService.createEvent(request);

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(eventCaptor.capture());

        Event persisted = eventCaptor.getValue();
        assertEquals("Arsenal vs Chelsea", persisted.getTitle());
        assertEquals(LocalDate.of(2026, 4, 12), persisted.getEventDate());
        assertEquals(LocalTime.of(15, 30), persisted.getTimeUtc());
        assertEquals(2, persisted.getEventTeams().size());
        assertTrue(persisted.getEventTeams().stream().allMatch(et -> et.getEvent() == persisted));

        assertNotNull(response);
        assertEquals(100L, response.eventId());
        assertEquals("Arsenal vs Chelsea", response.title());
        assertEquals("SCHEDULED", response.status().code());
        assertEquals("Old Trafford", response.venue().name());
        assertEquals(2, response.teams().size());
        assertTrue(response.teams().stream().anyMatch(t -> "HOME".equals(t.role()) && "Arsenal".equals(t.team().name())));
        assertTrue(response.teams().stream().anyMatch(t -> "AWAY".equals(t.role()) && "Chelsea".equals(t.team().name())));
    }

    @Test
    void createEvent_withMissingTitle_throwsBadRequest() {
        EventRequestDto request = new EventRequestDto(
                "  ",
                "No title should fail",
                LocalDate.of(2026, 4, 12),
                LocalTime.of(15, 30),
                1L,
                1L,
                1L,
                List.of()
        );

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> eventService.createEvent(request));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("title is required"));
        verifyNoInteractions(eventRepository, venueRepository, eventStatusRepository, seasonRepository, teamRepository);
        }

        @Test
        void createEvent_withUnknownTeam_rollsBackFlowWithoutSave() {
        EventRequestDto request = new EventRequestDto(
            "Arsenal vs Chelsea",
            "Premier League fixture",
            LocalDate.of(2026, 4, 12),
            LocalTime.of(15, 30),
            1L,
            1L,
            1L,
            List.of(
                new EventTeamRequestDto(10L, "HOME", null),
                new EventTeamRequestDto(999L, "AWAY", null)
            )
        );

        Venue venue = new Venue();
        venue.setVenueId(1L);

        EventStatus status = new EventStatus();
        status.setEventStatusId(1L);

        Season season = new Season();
        season.setSeasonId(1L);
        League league = new League();
        Sport sport = new Sport();
        league.setSport(sport);
        season.setLeague(league);

        Team homeTeam = new Team();
        homeTeam.setTeamId(10L);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(eventStatusRepository.findById(1L)).thenReturn(Optional.of(status));
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(teamRepository.findById(10L)).thenReturn(Optional.of(homeTeam));
        when(teamRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> eventService.createEvent(request));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Team not found: 999"));
        verify(eventRepository, never()).save(any(Event.class));
        verify(eventRepository, never()).findByIdWithDetails(any(Long.class));
    }

    @Test
    void getEventById_whenMissing_throwsNotFound() {
        when(eventRepository.findByIdWithDetails(999L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> eventService.getEventById(999L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Event not found: 999"));
    }

    private static Event buildDetailedSavedEvent(
            Long eventId,
            EventRequestDto request,
            Venue venue,
            EventStatus status,
            Season season,
            Team homeTeam,
            Team awayTeam
    ) {
        Event event = new Event();
        event.setEventId(eventId);
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setEventDate(request.eventDate());
        event.setTimeUtc(request.timeUtc());
        event.setVenue(venue);
        event.setStatus(status);
        event.setSeason(season);

        EventTeam home = new EventTeam();
        home.setEvent(event);
        home.setTeam(homeTeam);
        home.setRole("HOME");
        home.setScore(null);

        EventTeam away = new EventTeam();
        away.setEvent(event);
        away.setTeam(awayTeam);
        away.setRole("AWAY");
        away.setScore(null);

        event.setEventTeams(Set.of(home, away));
        return event;
    }
}
