CREATE TABLE IF NOT EXISTS users
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        login VARCHAR(20) NOT NULL,
        email VARCHAR(255) NOT NULL CHECK (email LIKE '%_@__%.__%'),
        birthday DATE CHECK (birthday <= CURRENT_DATE)
        );

CREATE TABLE IF NOT EXISTS rating
(
    id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(200),
    release_date DATE,
    duration INTEGER CHECK (duration > 0),
    rating_id INTEGER,
    FOREIGN KEY (rating_id) REFERENCES rating(id)
);

CREATE TABLE IF NOT EXISTS friends
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        user_id INTEGER NOT NULL,
        friend_id INTEGER NOT NULL,
        status VARCHAR(20),
    CHECK (user_id <> friend_id),
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (friend_id) REFERENCES users(id),
        UNIQUE (user_id, friend_id)
        );

CREATE TABLE IF NOT EXISTS likes
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        user_id INTEGER NOT NULL,
        film_id INTEGER NOT NULL,
        FOREIGN KEY (film_id) REFERENCES films(id),
        FOREIGN KEY (user_id) REFERENCES users(id),
        UNIQUE (film_id, user_id)
        );

CREATE TABLE IF NOT EXISTS genres
(
    id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS film_genre
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        genre_id INTEGER,
        film_id INTEGER,
        FOREIGN KEY (film_id) REFERENCES films(id),
        FOREIGN KEY (genre_id) REFERENCES genres(id),
        UNIQUE (film_id, genre_id)
        );

