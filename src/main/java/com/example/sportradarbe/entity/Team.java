package com.example.sportradarbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "official_name", length = 200)
    private String officialName;

    @Column(name = "abbreviation", length = 20)
    private String abbreviation;

    @Column(name = "team_country_code", length = 10)
    private String teamCountryCode;

    public Long getTeamId() { return teamId; }
    public String getName() { return name; }
    public String getOfficialName() { return officialName; }
    public String getAbbreviation() { return abbreviation; }
    public String getTeamCountryCode() { return teamCountryCode; }

    public void setTeamId(Long teamId) { this.teamId = teamId; }
    public void setName(String name) { this.name = name; }
    public void setOfficialName(String officialName) { this.officialName = officialName; }
    public void setAbbreviation(String abbreviation) { this.abbreviation = abbreviation; }
    public void setTeamCountryCode(String teamCountryCode) { this.teamCountryCode = teamCountryCode; }
}
