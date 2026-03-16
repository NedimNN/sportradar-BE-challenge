package com.example.sportradarbe.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventTeamId implements Serializable {

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "team_id")
    private Long teamId;

    public EventTeamId() {}

    public EventTeamId(Long eventId, Long teamId) {
        this.eventId = eventId;
        this.teamId = teamId;
    }

    public Long getEventId() { return eventId; }
    public Long getTeamId() { return teamId; }

    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventTeamId that)) return false;
        return Objects.equals(eventId, that.eventId) && Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, teamId);
    }
}
