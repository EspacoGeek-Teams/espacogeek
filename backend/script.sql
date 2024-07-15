--Como ainda não tem seeder, script para rodar no SQL
INSERT INTO ESPACOGEEKDB.MEDIA_CATEGORIES VALUES (
    1,
    "TVSerie"
);

INSERT INTO ESPACOGEEKDB.API_KEYS VALUE (
    1,
    "",
    "The Movie Database"
);

INSERT INTO ESPACOGEEKDB.TYPE_REFERENCE VALUE (
    1,
    "The Movie Database"
);

INSERT INTO ESPACOGEEKDB.TYPE_REFERENCE VALUE (
    2,
    "TheTVDB"
);

INSERT INTO ESPACOGEEKDB.TYPE_REFERENCE VALUE (
    3,
    "IMDb"
);

INSERT INTO espacogeekdb.genres (id_genre, name_genre)
VALUES
    (1,'Action'),
    (2,'Adventure'),
    (3,'Animation'),
    (4,'Comedy'),
    (5,'Crime'),
    (6,'Documentary'),
    (7,'Drama'),
    (8,'Family'),
    (9,'Fantasy'),
    (10,'History'),
    (11,'Horror'),
    (12,'Musical'),
    (13,'Mystery'),
    (14,'Romance'),
    (15,'Sci-Fi (Science Fiction)'),
    (16,'TV Movie'),
    (17,'Thriller'),
    (18,'War'),
    (19,'Western'),
    (20,'Kids'),
    (21,'News'),
    (22,'Reality'),
    (23,'Soap'),
    (24,'Talk');
