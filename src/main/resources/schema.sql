CREATE TABLE IF NOT EXISTS USERS
(
	USER_ID INT PRIMARY KEY AUTO_INCREMENT,
	EMAIL VARCHAR(255) NOT NULL,
	LOGIN VARCHAR(255) NOT NULL,
	USER_NAME VARCHAR(255) NOT NULL,
	BIRTHDAY DATE
);
CREATE TABLE IF NOT EXISTS RATING
(
	RATING_ID INT PRIMARY KEY AUTO_INCREMENT,
	RATING_NAME VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS FILMS
(
	FILM_ID INT PRIMARY KEY AUTO_INCREMENT,
	FILM_NAME VARCHAR(255) NOT NULL,
    RATING_ID INT REFERENCES RATING (RATING_ID),
    DESCRIPTION VARCHAR(255),
    RELEASE_DATE DATE,
    DURATION INT
);

CREATE TABLE IF NOT EXISTS GENRES
(
	GENRE_ID INT PRIMARY KEY AUTO_INCREMENT,
	GENRE_NAME VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS FILMSGENRES (
	FILM_ID INT REFERENCES FILMS (FILM_ID),
	GENRE_ID INT REFERENCES GENRES (GENRE_ID)
);

CREATE TABLE IF NOT EXISTS FILMLIKES
(
	FILM_ID INT REFERENCES FILMS (FILM_ID),
	USER_ID INT REFERENCES USERS (USER_ID)
);
CREATE TABLE IF NOT EXISTS USERFRIENDS
(
	USER_ID INT REFERENCES USERS (USER_ID),
	FRIEND_ID INT REFERENCES USERS (USER_ID)
);
