package com.example.sportradarbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_status")
public class EventStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_status_id")
    private Long eventStatusId;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "label", nullable = false, length = 100)
    private String label;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Long getEventStatusId() { return eventStatusId; }
    public String getCode() { return code; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }

    public void setEventStatusId(Long eventStatusId) { this.eventStatusId = eventStatusId; }
    public void setCode(String code) { this.code = code; }
    public void setLabel(String label) { this.label = label; }
    public void setDescription(String description) { this.description = description; }
}
