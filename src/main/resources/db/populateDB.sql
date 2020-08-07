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

INSERT INTO dishes (restaurant_id, name, price)
VALUES (100002, 'Hamburger', 700),
       (100002,'French fries', 150),
       (100003,'Chicken soup', 250);