package com.example.sportradarbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sportradarbe.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
