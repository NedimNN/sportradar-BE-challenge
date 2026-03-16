package com.example.sportradarbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sportradarbe.entity.EventStatus;

public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
}
