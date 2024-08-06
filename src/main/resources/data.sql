CREATE TABLE IF NOT EXISTS users (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(255) DEFAULT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    PRIMARY KEY (id)
);


INSERT INTO users (id, username, email, first_name, last_name, password)
VALUES
    (1, 'Rado', 'rado@rado.com','Radostin','Stoyanov','9af4ed36dbea8dad496d0f74d398024941e40cf832f0ae6200a6b29b246795050af4c2fd12ec9ba6d7e93d15560441bc');

CREATE TABLE IF NOT EXISTS roles (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    role enum('ADMIN','USER') DEFAULT NULL,
    PRIMARY KEY (id)
);

INSERT INTO roles (id, role)
VALUES (1, 'ADMIN');
INSERT INTO roles (id, role)
VALUES (2, 'USER');

CREATE TABLE IF NOT EXISTS users_roles (
    user_id bigint(20) NOT NULL,
    role_id bigint(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (1, 2);

CREATE TABLE IF NOT EXISTS products (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    product_type enum('CORN','CUCUMBER','EGGPLANT','ONION','OTHERS','PEPPER','POTATO','TOMATO') DEFAULT NULL,
    PRIMARY KEY (id)
);

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
