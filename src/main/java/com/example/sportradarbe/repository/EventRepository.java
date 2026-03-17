package com.example.sportradarbe.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sportradarbe.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Loads all events with their full object graph in a single SQL query using
     * JOIN FETCH, avoiding N+1 selects. Using DISTINCT to deduplicate parent rows
     * that multiply due to the LEFT JOIN on the eventTeams collection.
     */
    @Query("""
            SELECT DISTINCT e FROM Event e
            JOIN FETCH e.venue
            JOIN FETCH e.status
            JOIN FETCH e.season s
            JOIN FETCH s.league l
            JOIN FETCH l.sport
            LEFT JOIN FETCH e.eventTeams et
            LEFT JOIN FETCH et.team
            WHERE (:sportId IS NULL OR l.sport.sportId = :sportId)
              AND (:leagueId IS NULL OR s.league.leagueId = :leagueId)
              AND (:date IS NULL OR e.eventDate = :date)
            ORDER BY e.eventDate ASC, e.timeUtc ASC
            """)
    List<Event> findAllWithDetails(
        @Param("sportId") Long sportId,
        @Param("leagueId") Long leagueId,
        @Param("date") LocalDate date
    );

    /**
     * Same strategy as findAllWithDetails but for a single event by its ID.
     */
    @Query("""
            SELECT e FROM Event e
            JOIN FETCH e.venue
            JOIN FETCH e.status
            JOIN FETCH e.season s
            JOIN FETCH s.league l
            JOIN FETCH l.sport
            LEFT JOIN FETCH e.eventTeams et
            LEFT JOIN FETCH et.team
            WHERE e.eventId = :eventId
            """)
    Optional<Event> findByIdWithDetails(Long eventId);
}
