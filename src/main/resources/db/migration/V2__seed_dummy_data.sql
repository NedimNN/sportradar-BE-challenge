INSERT INTO sport (name, code) VALUES
    ('Football', 'FOOTBALL'),
    ('Basketball', 'BASKETBALL'),
    ('Rugby Union', 'RUGBY_UNION'),
    ('Cricket', 'CRICKET'),
    ('Ice Hockey', 'ICE_HOCKEY'),
    ('Baseball', 'BASEBALL'),
    ('American Football', 'AMERICAN_FOOTBALL');

INSERT INTO league (name, country, sport_id) VALUES
    ('Premier League', 'England', (SELECT sport_id FROM sport WHERE code = 'FOOTBALL')),
    ('NBA', 'United States', (SELECT sport_id FROM sport WHERE code = 'BASKETBALL')),
    ('United Rugby Championship', 'Ireland', (SELECT sport_id FROM sport WHERE code = 'RUGBY_UNION')),
    ('Indian Premier League', 'India', (SELECT sport_id FROM sport WHERE code = 'CRICKET')),
    ('National Hockey League', 'United States/Canada', (SELECT sport_id FROM sport WHERE code = 'ICE_HOCKEY')),
    ('Major League Baseball', 'United States/Canada', (SELECT sport_id FROM sport WHERE code = 'BASEBALL')),
    ('National Football League', 'United States', (SELECT sport_id FROM sport WHERE code = 'AMERICAN_FOOTBALL'));

INSERT INTO season (name, start_date, end_date, league_id) VALUES
    ('Premier League 2024/25', DATE '2024-08-16', DATE '2025-05-25', (SELECT league_id FROM league WHERE name = 'Premier League')),
    ('NBA 2025/26', DATE '2025-10-21', DATE '2026-06-20', (SELECT league_id FROM league WHERE name = 'NBA')),
    ('United Rugby Championship 2024/25', DATE '2024-09-20', DATE '2025-06-14', (SELECT league_id FROM league WHERE name = 'United Rugby Championship')),
    ('IPL 2026', DATE '2026-03-20', DATE '2026-05-24', (SELECT league_id FROM league WHERE name = 'Indian Premier League')),
    ('NHL 2024/25', DATE '2024-10-04', DATE '2025-06-24', (SELECT league_id FROM league WHERE name = 'National Hockey League')),
    ('MLB 2026', DATE '2026-03-26', DATE '2026-11-01', (SELECT league_id FROM league WHERE name = 'Major League Baseball')),
    ('NFL 2026', DATE '2026-09-10', DATE '2027-02-14', (SELECT league_id FROM league WHERE name = 'National Football League'));

INSERT INTO venue (name, capacity) VALUES
    ('Old Trafford', 74310),
    ('Madison Square Garden', 19812),
    ('Aviva Stadium', 51700),
    ('Wankhede Stadium', 33108),
    ('Bell Centre', 21302),
    ('Dodger Stadium', 56000),
    ('Lambeau Field', 81441);

INSERT INTO event_status (code, label, description) VALUES
    ('SCHEDULED', 'Scheduled', 'Event is scheduled and has not started yet.'),
    ('LIVE', 'Live', 'Event is currently in progress.'),
    ('FINISHED', 'Finished', 'Event has concluded with a final result.'),
    ('POSTPONED', 'Postponed', 'Event has been postponed to a later date.'),
    ('CANCELLED', 'Cancelled', 'Event has been cancelled and will not be played.'),
    ('SUSPENDED', 'Suspended', 'Event started but was suspended before completion.'),
    ('ABANDONED', 'Abandoned', 'Event was abandoned due to external circumstances.');

INSERT INTO team (name, official_name, abbreviation, team_country_code) VALUES
    ('Manchester United', 'Manchester United Football Club', 'MUN', 'GB'),
    ('Liverpool', 'Liverpool Football Club', 'LIV', 'GB'),
    ('New York Knicks', 'New York Knickerbockers', 'NYK', 'US'),
    ('Boston Celtics', 'Boston Celtics', 'BOS', 'US'),
    ('Leinster Rugby', 'Leinster Rugby', 'LEI', 'IE'),
    ('Munster Rugby', 'Munster Rugby', 'MUNR', 'IE'),
    ('Mumbai Indians', 'Mumbai Indians', 'MI', 'IN'),
    ('Chennai Super Kings', 'Chennai Super Kings', 'CSK', 'IN'),
    ('Montreal Canadiens', 'Club de hockey Canadien', 'MTL', 'CA'),
    ('Toronto Maple Leafs', 'Toronto Maple Leafs Hockey Club', 'TOR', 'CA'),
    ('Los Angeles Dodgers', 'Los Angeles Dodgers', 'LAD', 'US'),
    ('San Francisco Giants', 'San Francisco Giants', 'SFG', 'US'),
    ('Green Bay Packers', 'Green Bay Packers', 'GB', 'US'),
    ('Chicago Bears', 'Chicago Bears', 'CHI', 'US');

INSERT INTO event (time_utc, event_date, title, description, venue_id, status_id, season_id) VALUES
    (
        TIME '15:30:00',
        DATE '2025-03-16',
        'Manchester United vs Liverpool',
        'Premier League fixture at Old Trafford.',
        (SELECT venue_id FROM venue WHERE name = 'Old Trafford'),
        (SELECT event_status_id FROM event_status WHERE code = 'FINISHED'),
        (SELECT season_id FROM season WHERE name = 'Premier League 2024/25')
    ),
    (
        TIME '00:30:00',
        DATE '2026-04-08',
        'New York Knicks vs Boston Celtics',
        'Eastern Conference regular season matchup at Madison Square Garden.',
        (SELECT venue_id FROM venue WHERE name = 'Madison Square Garden'),
        (SELECT event_status_id FROM event_status WHERE code = 'LIVE'),
        (SELECT season_id FROM season WHERE name = 'NBA 2025/26')
    ),
    (
        TIME '17:35:00',
        DATE '2025-05-10',
        'Leinster Rugby vs Munster Rugby',
        'United Rugby Championship derby in Dublin.',
        (SELECT venue_id FROM venue WHERE name = 'Aviva Stadium'),
        (SELECT event_status_id FROM event_status WHERE code = 'SCHEDULED'),
        (SELECT season_id FROM season WHERE name = 'United Rugby Championship 2024/25')
    ),
    (
        TIME '14:00:00',
        DATE '2026-04-20',
        'Mumbai Indians vs Chennai Super Kings',
        'IPL evening match at Wankhede Stadium.',
        (SELECT venue_id FROM venue WHERE name = 'Wankhede Stadium'),
        (SELECT event_status_id FROM event_status WHERE code = 'POSTPONED'),
        (SELECT season_id FROM season WHERE name = 'IPL 2026')
    ),
    (
        TIME '23:00:00',
        DATE '2025-03-29',
        'Montreal Canadiens vs Toronto Maple Leafs',
        'Original Six rivalry game in Montreal.',
        (SELECT venue_id FROM venue WHERE name = 'Bell Centre'),
        (SELECT event_status_id FROM event_status WHERE code = 'SUSPENDED'),
        (SELECT season_id FROM season WHERE name = 'NHL 2024/25')
    ),
    (
        TIME '02:10:00',
        DATE '2026-06-15',
        'Los Angeles Dodgers vs San Francisco Giants',
        'National League West game at Dodger Stadium.',
        (SELECT venue_id FROM venue WHERE name = 'Dodger Stadium'),
        (SELECT event_status_id FROM event_status WHERE code = 'CANCELLED'),
        (SELECT season_id FROM season WHERE name = 'MLB 2026')
    ),
    (
        TIME '21:25:00',
        DATE '2026-11-23',
        'Green Bay Packers vs Chicago Bears',
        'Historic NFC North rivalry at Lambeau Field.',
        (SELECT venue_id FROM venue WHERE name = 'Lambeau Field'),
        (SELECT event_status_id FROM event_status WHERE code = 'ABANDONED'),
        (SELECT season_id FROM season WHERE name = 'NFL 2026')
    );

INSERT INTO event_team (event_id, team_id, role, score) VALUES
    (
        (SELECT event_id FROM event WHERE title = 'Manchester United vs Liverpool'),
        (SELECT team_id FROM team WHERE name = 'Manchester United'),
        'HOME',
        2
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Manchester United vs Liverpool'),
        (SELECT team_id FROM team WHERE name = 'Liverpool'),
        'AWAY',
        1
    ),
    (
        (SELECT event_id FROM event WHERE title = 'New York Knicks vs Boston Celtics'),
        (SELECT team_id FROM team WHERE name = 'New York Knicks'),
        'HOME',
        88
    ),
    (
        (SELECT event_id FROM event WHERE title = 'New York Knicks vs Boston Celtics'),
        (SELECT team_id FROM team WHERE name = 'Boston Celtics'),
        'AWAY',
        91
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Leinster Rugby vs Munster Rugby'),
        (SELECT team_id FROM team WHERE name = 'Leinster Rugby'),
        'HOME',
        NULL
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Leinster Rugby vs Munster Rugby'),
        (SELECT team_id FROM team WHERE name = 'Munster Rugby'),
        'AWAY',
        NULL
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Mumbai Indians vs Chennai Super Kings'),
        (SELECT team_id FROM team WHERE name = 'Mumbai Indians'),
        'HOME',
        NULL
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Mumbai Indians vs Chennai Super Kings'),
        (SELECT team_id FROM team WHERE name = 'Chennai Super Kings'),
        'AWAY',
        NULL
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Montreal Canadiens vs Toronto Maple Leafs'),
        (SELECT team_id FROM team WHERE name = 'Montreal Canadiens'),
        'HOME',
        3
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Montreal Canadiens vs Toronto Maple Leafs'),
        (SELECT team_id FROM team WHERE name = 'Toronto Maple Leafs'),
        'AWAY',
        3
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Los Angeles Dodgers vs San Francisco Giants'),
        (SELECT team_id FROM team WHERE name = 'Los Angeles Dodgers'),
        'HOME',
        NULL
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Los Angeles Dodgers vs San Francisco Giants'),
        (SELECT team_id FROM team WHERE name = 'San Francisco Giants'),
        'AWAY',
        NULL
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Green Bay Packers vs Chicago Bears'),
        (SELECT team_id FROM team WHERE name = 'Green Bay Packers'),
        'HOME',
        14
    ),
    (
        (SELECT event_id FROM event WHERE title = 'Green Bay Packers vs Chicago Bears'),
        (SELECT team_id FROM team WHERE name = 'Chicago Bears'),
        'AWAY',
        10
    );