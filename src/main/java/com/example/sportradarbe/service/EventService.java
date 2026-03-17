package com.example.sportradarbe.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.sportradarbe.dto.EventRequestDto;
import com.example.sportradarbe.dto.EventResponseDto;
import com.example.sportradarbe.dto.EventResponseDto.EventTeamDto;
import com.example.sportradarbe.dto.EventResponseDto.LeagueDto;
import com.example.sportradarbe.dto.EventResponseDto.SeasonDto;
import com.example.sportradarbe.dto.EventResponseDto.SportDto;
import com.example.sportradarbe.dto.EventResponseDto.StatusDto;
import com.example.sportradarbe.dto.EventResponseDto.TeamDto;
import com.example.sportradarbe.dto.EventResponseDto.VenueDto;
import com.example.sportradarbe.entity.Event;
import com.example.sportradarbe.entity.EventStatus;
import com.example.sportradarbe.entity.EventTeam;
import com.example.sportradarbe.entity.League;
import com.example.sportradarbe.entity.Season;
import com.example.sportradarbe.entity.Team;
import com.example.sportradarbe.entity.Venue;
import com.example.sportradarbe.repository.EventRepository;
import com.example.sportradarbe.repository.EventStatusRepository;
import com.example.sportradarbe.repository.SeasonRepository;
import com.example.sportradarbe.repository.TeamRepository;
import com.example.sportradarbe.repository.VenueRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final EventStatusRepository eventStatusRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;

    public EventService(EventRepository eventRepository,
                        VenueRepository venueRepository,
                        EventStatusRepository eventStatusRepository,
                        SeasonRepository seasonRepository,
                        TeamRepository teamRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.eventStatusRepository = eventStatusRepository;
        this.seasonRepository = seasonRepository;
        this.teamRepository = teamRepository;
    }

    //  CREATE

    @Transactional
    public EventResponseDto createEvent(EventRequestDto request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
        }
        if (request.venueId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "venueId is required");
        }
        if (request.statusId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "statusId is required");
        }
        if (request.seasonId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "seasonId is required");
        }

        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Venue not found: " + request.venueId()));

        EventStatus status = eventStatusRepository.findById(request.statusId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "EventStatus not found: " + request.statusId()));

        Season season = seasonRepository.findById(request.seasonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Season not found: " + request.seasonId()));

        Event event = new Event();
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setEventDate(request.eventDate());
        event.setTimeUtc(request.timeUtc());
        event.setVenue(venue);
        event.setStatus(status);
        event.setSeason(season);

        if (request.teams() != null) {
            for (var teamRequest : request.teams()) {
                if (teamRequest.teamId() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "teamId is required for each team entry");
                }
                Team team = teamRepository.findById(teamRequest.teamId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Team not found: " + teamRequest.teamId()));

                EventTeam eventTeam = new EventTeam();
                eventTeam.setEvent(event);
                eventTeam.setTeam(team);
                eventTeam.setRole(teamRequest.role());
                eventTeam.setScore(teamRequest.score());
                event.getEventTeams().add(eventTeam);
            }
        }

        Event saved = eventRepository.save(event);
        // Re-fetch with full detail graph so the response is consistent with GET endpoints.
        return toDto(eventRepository.findByIdWithDetails(saved.getEventId())
                .orElseThrow());
    }

    //  READ

    @Transactional(readOnly = true)
    public List<EventResponseDto> getAllEvents() {
        return eventRepository.findAllWithDetails()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public EventResponseDto getEventById(Long eventId) {
        return eventRepository.findByIdWithDetails(eventId)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Event not found: " + eventId));
    }

    //  ENTITY TO DTO

    private EventResponseDto toDto(Event event) {
        VenueDto venueDto = new VenueDto(
                event.getVenue().getVenueId(),
                event.getVenue().getName(),
                event.getVenue().getCapacity()
        );

        StatusDto statusDto = new StatusDto(
                event.getStatus().getEventStatusId(),
                event.getStatus().getCode(),
                event.getStatus().getLabel()
        );

        League league = event.getSeason().getLeague();
        SportDto sportDto = new SportDto(
                league.getSport().getSportId(),
                league.getSport().getName(),
                league.getSport().getCode()
        );
        LeagueDto leagueDto = new LeagueDto(
                league.getLeagueId(),
                league.getName(),
                league.getCountry(),
                sportDto
        );
        SeasonDto seasonDto = new SeasonDto(
                event.getSeason().getSeasonId(),
                event.getSeason().getName(),
                event.getSeason().getStartDate(),
                event.getSeason().getEndDate(),
                leagueDto
        );

        List<EventTeamDto> teams = event.getEventTeams().stream()
                .map(et -> new EventTeamDto(
                        new TeamDto(
                                et.getTeam().getTeamId(),
                                et.getTeam().getName(),
                                et.getTeam().getAbbreviation(),
                                et.getTeam().getTeamCountryCode()
                        ),
                        et.getRole(),
                        et.getScore()
                ))
                .toList();

        return new EventResponseDto(
                event.getEventId(),
                event.getEventDate(),
                event.getTimeUtc(),
                event.getTitle(),
                event.getDescription(),
                venueDto,
                statusDto,
                seasonDto,
                teams
        );
    }
}
