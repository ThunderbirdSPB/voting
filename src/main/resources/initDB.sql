DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id                  INTEGER IDENTITY            PRIMARY KEY,
    email               VARCHAR(320)                NOT NULL UNIQUE,
    password            VARCHAR(320)                NOT NULL,
    voted               BOOLEAN DEFAULT FALSE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE restaurant
(
    id                  INTEGER IDENTITY            PRIMARY KEY,
    name                VARCHAR(320)                NOT NULL UNIQUE,
    votes               INTEGER DEFAULT 0           NOT NULL
);

CREATE TABLE dish
(
    id                  INTEGER IDENTITY            PRIMARY KEY,
    restaurant_id       INTEGER                     NOT NULL,
    name                VARCHAR(320)                NOT NULL,
    price               DOUBLE                      NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    user_id             INTEGER                     NOT NULL,
    restaurant_id       INTEGER                     NOT NULL,
    CONSTRAINT user_restaurant_idx UNIQUE (user_id, restaurant_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE user_roles
(
    user_id             INTEGER                     NOT NULL,
    role                VARCHAR(320)                NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
