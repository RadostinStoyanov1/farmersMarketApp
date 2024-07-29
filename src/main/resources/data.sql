USE farmers_market;

alter table `users` modify column uuid varbinary(36);

INSERT INTO `users` (`id`, `username`, `email`, `first_name`, `last_name`, `password`, `uuid`)
VALUES
    (1, 'Rado', 'rado@rado.com','Radostin','Stoyanov','$2a$10$7SVR.r7RMpo0Q/ySmi.EneuCg5FYiHAR1ovcgN0DRso9NxGJhG/TS','3fd4b7bf-f5fc-4c53-a7c6-1520560d3c71');

INSERT INTO roles (id, role)
VALUES (1, 'ADMIN');
INSERT INTO roles (id, role)
VALUES (2, 'USER');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
    (1, 1),
    (1, 2);

INSERT INTO products (id, product_type)
VALUES (1, 'TOMATO');
INSERT INTO products (id, product_type)
VALUES (2, 'CUCUMBER');
INSERT INTO products (id, product_type)
VALUES (3, 'PEPPER');
INSERT INTO products (id, product_type)
VALUES (4, 'EGGPLANT');
INSERT INTO products (id, product_type)
VALUES (5, 'ONION');
INSERT INTO products (id, product_type)
VALUES (6, 'POTATO');
INSERT INTO products (id, product_type)
VALUES (7, 'CORN');
INSERT INTO products (id, product_type)
VALUES (8, 'OTHERS');
