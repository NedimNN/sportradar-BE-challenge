package com.example.sportradarbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "league")
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_id")
    private Long leagueId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "country", length = 100)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sport_id", nullable = false)
    private Sport sport;

    public Long getLeagueId() { return leagueId; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public Sport getSport() { return sport; }

    public void setLeagueId(Long leagueId) { this.leagueId = leagueId; }
    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setSport(Sport sport) { this.sport = sport; }
}
