CREATE TABLE IF NOT EXISTS users
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR(255),
        login VARCHAR(20) NOT NULL,
        email VARCHAR(255) NOT NULL CHECK (email LIKE '%_@__%.__%'),
        birthday DATE CHECK (birthday <= CURRENT_DATE)
        );

CREATE TABLE IF NOT EXISTS friends
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        user_id INTEGER,
        friend_id INTEGER,
        status VARCHAR(20),
    CHECK (user_id <> friend_id)
        );

CREATE TABLE IF NOT EXISTS likes
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        user_id INTEGER,
        film_id INTEGER
        );

CREATE TABLE IF NOT EXISTS films
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description VARCHAR(200),
        release_date DATE,
        duration INTEGER CHECK (duration > 0),
        rating_id INTEGER
        );

CREATE TABLE IF NOT EXISTS rating
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR NOT NULL
        );

CREATE TABLE IF NOT EXISTS film_genre
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        genre_id INTEGER,
        film_id INTEGER
        );

CREATE TABLE IF NOT EXISTS genre
        (
        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR(50) NOT NULL
        );