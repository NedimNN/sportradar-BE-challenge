package com.example.sportradarbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sportradarbe.entity.Sport;

public interface SportRepository extends JpaRepository<Sport, Long> {
}
