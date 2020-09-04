DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM DISHES;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO  restaurants (name)
VALUES ('THE CASTLE'),
       ('RED SQUARE');

INSERT INTO dishes (restaurant_id, name, menuDate, price)
VALUES (100002, 'Hamburger', '2020-01-30', 700),
       (100002, 'French fries', '2020-01-30', 150),
       (100003, 'Chicken soup', '2020-01-31', 250);

INSERT INTO votes (voteDate, user_id, restaurant_id)
VALUES ('2020-01-30 10:00:00', 100000, 100002),
       ('2020-01-30 12:00:00', 100001, 100003),
       ('2020-01-31 10:00:00', 100000, 100003)