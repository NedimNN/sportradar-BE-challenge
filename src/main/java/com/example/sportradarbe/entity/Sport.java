package com.example.sportradarbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sport")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_id")
    private Long sportId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    public Long getSportId() { return sportId; }
    public String getName() { return name; }
    public String getCode() { return code; }

    public void setSportId(Long sportId) { this.sportId = sportId; }
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
}
