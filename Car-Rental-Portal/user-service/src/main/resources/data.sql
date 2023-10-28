
INSERT INTO user(user_name, email, first_name, last_name, phone, password, user_role, status, frequent_renter_type)
VALUES ('admin', 'admin@miu.edu', 'admin', 'admin', '20612346482',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'ADMIN', 'ACTIVE', 'NONE'),
       ('Manager 1', 'manager@miu.edu', 'manager', 'John', '20612346481',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'MANAGER', 'ACTIVE', 'NONE'),
       ('Manager 2', 'manager2@miu.edu', 'manager 2', 'Sam', '20612346481',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'MANAGER', 'ACTIVE', 'NONE'),
       ('Manager 3', 'manager3@miu.edu', 'manager 3', 'Jackson', '20612346481',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'MANAGER', 'ACTIVE', 'NONE'),
       ('customer 1', 'customer@miu.edu', 'customer', 'Adam', '20612346486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE'),
       ('Bronze customer', 'customer2@miu.edu', 'Bronze', 'Josh', '2068764648',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'FREQUENT_RENTER', 'ACTIVE', 'BRONZE'),
       ('Sliver customer', 'customer3@miu.edu', 'Sliver', 'Sammy', '2061234565',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'FREQUENT_RENTER', 'ACTIVE', 'SILVER'),
       ('Gold customer', 'customer4@miu.edu', 'Gold', 'Thomas', '2053446486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'FREQUENT_RENTER', 'ACTIVE', 'GOLD'),
       ('Jenny', 'jenny@miu.edu', 'customer', 'Jenny', '2061986486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE'),
       ('Lucy', 'lucy@miu.edu', 'customer', 'Lucy', '2098746486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE'),
       ('Eddie', 'eddie@miu.edu', 'customer', 'Eddie', '2063467486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE'),
       ('Maria', 'maria@miu.edu', 'customer', 'Maria', '2066546486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE'),
       ('Happy', 'happy@miu.edu', 'customer', 'Happy', '2049846486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE'),
       ('Steve', 'steve@miu.edu', 'customer', 'Steve', '2023646486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE'),
       ('Loki', 'loki@miu.edu', 'customer', 'Loki', '208616486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE')
;


INSERT INTO `car-rental`.card (card_type, card_holder_name, card_number, cvv, expiration_date, user_id)
VALUES ('VISA', 'Adam', '4234567890', '123', '10/2025', 5),
       ('MASTER_CARD', 'Adam', '7567123890', '234', '08/2026', 5),
       ('VISA', 'Josh', '4123437890', '222', '12/2025', 6),
       ('MASTER_CARD', 'Josh', '3567123890', '333', '01/2026', 6),
       ('VISA', 'Sammy', '4290567890', '444', '07/2025', 7),
       ('MASTER_CARD', 'Sammy', '8567123890', '555', '05/2026', 7),
       ('VISA', 'Thomas', '40897662434', '666', '12/2025', 8),
       ('MASTER_CARD', 'Thomas', '71126784421', '777', '10/2026', 8)
;

