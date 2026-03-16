package com.example.sportradarbe.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "time_utc")
    private LocalTime timeUtc;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private EventStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventTeam> eventTeams = new HashSet<>();

    public Long getEventId() { return eventId; }
    public LocalTime getTimeUtc() { return timeUtc; }
    public LocalDate getEventDate() { return eventDate; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Venue getVenue() { return venue; }
    public EventStatus getStatus() { return status; }
    public Season getSeason() { return season; }
    public Set<EventTeam> getEventTeams() { return eventTeams; }

    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setTimeUtc(LocalTime timeUtc) { this.timeUtc = timeUtc; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setVenue(Venue venue) { this.venue = venue; }
    public void setStatus(EventStatus status) { this.status = status; }
    public void setSeason(Season season) { this.season = season; }
    public void setEventTeams(Set<EventTeam> eventTeams) { this.eventTeams = eventTeams; }
}
