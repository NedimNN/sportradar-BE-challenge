CREATE TABLE sport (
    sport_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE league (
    league_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    country VARCHAR(100),
    sport_id BIGINT NOT NULL,
    CONSTRAINT fk_league_sport
        FOREIGN KEY (sport_id)
        REFERENCES sport (sport_id)
);

CREATE TABLE season (
    season_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    league_id BIGINT NOT NULL,
    CONSTRAINT fk_season_league
        FOREIGN KEY (league_id)
        REFERENCES league (league_id),
    CONSTRAINT chk_season_date_range
        CHECK (end_date >= start_date)
);

CREATE TABLE venue (
    venue_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    capacity INTEGER,
    CONSTRAINT chk_venue_capacity_non_negative
        CHECK (capacity IS NULL OR capacity >= 0)
);

CREATE TABLE event_status (
    event_status_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    label VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE event (
    event_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    time_utc TIME,
    event_date DATE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    venue_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    season_id BIGINT NOT NULL,
    CONSTRAINT fk_event_venue
        FOREIGN KEY (venue_id)
        REFERENCES venue (venue_id),
    CONSTRAINT fk_event_status
        FOREIGN KEY (status_id)
        REFERENCES event_status (event_status_id),
    CONSTRAINT fk_event_season
        FOREIGN KEY (season_id)
        REFERENCES season (season_id)
);

CREATE TABLE team (
    team_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    official_name VARCHAR(200),
    abbreviation VARCHAR(20),
    team_country_code VARCHAR(10)
);

CREATE TABLE event_team (
    event_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    role VARCHAR(30),
    score INTEGER,
    CONSTRAINT pk_event_team PRIMARY KEY (event_id, team_id),
    CONSTRAINT fk_event_team_event
        FOREIGN KEY (event_id)
        REFERENCES event (event_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_event_team_team
        FOREIGN KEY (team_id)
        REFERENCES team (team_id)
        ON DELETE CASCADE,
    CONSTRAINT chk_event_team_score_non_negative
        CHECK (score IS NULL OR score >= 0)
);

CREATE INDEX idx_league_sport_id ON league (sport_id);
CREATE INDEX idx_season_league_id ON season (league_id);
CREATE INDEX idx_event_venue_id ON event (venue_id);
CREATE INDEX idx_event_status_id ON event (status_id);
CREATE INDEX idx_event_season_id ON event (season_id);
CREATE INDEX idx_event_team_team_id ON event_team (team_id);
