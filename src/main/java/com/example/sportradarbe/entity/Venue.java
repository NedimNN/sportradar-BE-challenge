package com.example.sportradarbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "venue")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Long venueId;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    public Long getVenueId() { return venueId; }
    public String getName() { return name; }
    public Integer getCapacity() { return capacity; }

    public void setVenueId(Long venueId) { this.venueId = venueId; }
    public void setName(String name) { this.name = name; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
