USE farmers_market;

/*alter table `users` modify column uuid varbinary(36);*/

INSERT INTO `users` (`id`, `username`, `email`, `first_name`, `last_name`, `password`)
VALUES
    (1, 'Rado', 'rado@rado.com','Radostin','Stoyanov','9af4ed36dbea8dad496d0f74d398024941e40cf832f0ae6200a6b29b246795050af4c2fd12ec9ba6d7e93d15560441bc');

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
