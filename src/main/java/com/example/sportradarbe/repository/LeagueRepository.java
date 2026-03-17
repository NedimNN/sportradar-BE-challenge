package com.example.sportradarbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sportradarbe.entity.League;

public interface LeagueRepository extends JpaRepository<League, Long> {
}
