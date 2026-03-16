package com.example.sportradarbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sportradarbe.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
