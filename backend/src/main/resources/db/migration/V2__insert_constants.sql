INSERT IGNORE INTO espacogeekdb.media_categories VALUES (1, 'TVSerie');
INSERT IGNORE INTO espacogeekdb.media_categories VALUES (2, 'Game');
INSERT IGNORE INTO espacogeekdb.media_categories VALUES (3, 'Visual Novel');

INSERT IGNORE INTO espacogeekdb.api_keys VALUES (1, '', 'The Movie Database');
INSERT IGNORE INTO espacogeekdb.api_keys VALUES (2, '', 'IGDB Client ID');
INSERT IGNORE INTO espacogeekdb.api_keys VALUES (3, '', 'IGDB Token');
INSERT IGNORE INTO espacogeekdb.api_keys VALUES (4, '', 'IGDB Client Secret');

INSERT IGNORE INTO espacogeekdb.type_reference VALUES (1, 'The Movie Database');
INSERT IGNORE INTO espacogeekdb.type_reference VALUES (2, 'TheTVDB');
INSERT IGNORE INTO espacogeekdb.type_reference VALUES (3, 'IMDb');
INSERT IGNORE INTO espacogeekdb.type_reference VALUES (4, 'IGDB');  -- No duplicates

INSERT IGNORE INTO espacogeekdb.genres (id_genre, name_genre) VALUES
    (1, 'Action'),
    (2, 'Adventure'),
    (3, 'Animation'),
    (4, 'Comedy'),
    (5, 'Crime'),
    (6, 'Documentary'),
    (7, 'Drama'),
    (8, 'Family'),
    (9, 'Fantasy'),
    (10, 'History'),
    (11, 'Horror'),
    (12, 'Musical'),
    (13, 'Mystery'),
    (14, 'Romance'),
    (15, 'Sci-Fi'),
    (16, 'TV Movie'),
    (17, 'Thriller'),
    (18, 'War'),
    (19, 'Western'),
    (20, 'Kids'),
    (21, 'News'),
    (22, 'Reality'),
    (23, 'Soap'),
    (24, 'Talk'),
    (25, 'Pinball'),
    (46, 'Quiz/Trivia'),
    (27, 'Indie'),
    (28, 'Arcade'),
    (29, 'Card & Board Game'),
    (30, 'MOBA'),
    (31, 'Point-and-click'),
    (32, 'Fighting'),
    (33, 'Shooter'),
    (34, 'Music'),
    (35, 'Platform'),
    (36, 'Puzzle'),
    (37, 'Racing'),
    (38, 'Real Time Strategy (RTS)'),
    (39, 'Role-playing (RPG)'),
    (40, 'Simulator'),
    (41, 'Sport'),
    (42, 'Strategy'),
    (43, 'Turn-based strategy (TBS)'),
    (44, 'Tactical'),
    (45, "Hack and slash/Beat 'em up");
