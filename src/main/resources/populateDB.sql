DELETE FROM dish;
DELETE FROM restaurant;
DELETE FROM user_roles;
DELETE FROM vote;
DELETE FROM users;

INSERT INTO users (email, password)
VALUES ('user1@yandex.ru', 'password'),
       ('user2@yandex.ru', 'password'),
       ('user3@yandex.ru', 'password'),
       ('admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 0),
       ('USER', 1),
       ('USER', 2),
       ('USER', 3),
       ('ADMIN', 3);

INSERT INTO restaurant (name)
VALUES ('DODO Pizza'),
       ('KFC'),
       ('Mac'),
       ('Sicilia pizza' ),
       ('Starbucks');

INSERT INTO dish(restaurant_id, name, price)
VALUES (0, '4 seasons', 2.2),
       (0, 'ceasar salad', 1.2),
       (1, 'chicken legs', 1.2),
       (1, 'fried chicken', 3.0),
       (2, 'big tasty', 1.4),
       (2, 'ceasar roll', 1.7),
       (4, 'americano', 1.3),
       (4, 'cappuccino', 1.3),
       (4, 'latte', 1.35);

INSERT INTO  vote(user_id, restaurant_id)
VALUES (0, 1),
       (1, 1),
       (2, 1);