package com.example.sportradarbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_team")
public class EventTeam {

    @EmbeddedId
    private EventTeamId id = new EventTeamId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "role", length = 30)
    private String role;

    @Column(name = "score")
    private Integer score;

    public EventTeamId getId() { return id; }
    public Event getEvent() { return event; }
    public Team getTeam() { return team; }
    public String getRole() { return role; }
    public Integer getScore() { return score; }

    public void setId(EventTeamId id) { this.id = id; }
    public void setEvent(Event event) { this.event = event; }
    public void setTeam(Team team) { this.team = team; }
    public void setRole(String role) { this.role = role; }
    public void setScore(Integer score) { this.score = score; }
}
