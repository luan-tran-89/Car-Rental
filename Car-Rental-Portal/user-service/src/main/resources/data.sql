
INSERT INTO user(user_name, email, first_name, last_name, phone, password, user_role, status, frequent_renter_type)
VALUES ('admin', 'admin@miu.edu', 'admin', 'admin', '20612346482',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'ADMIN', 'ACTIVE', 'NONE'),
       ('Manager 1', 'manager@miu.edu', 'manager', 'John', '20612346481',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'MANAGER', 'ACTIVE', 'NONE'),
       ('customer 1', 'customer@miu.edu', 'customer', 'Adam', '20612346486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE');


INSERT INTO `car-rental`.card (card_type, card_id, card_holder_name, card_number, cvv, expiration_date, user_id)
VALUES ('VISA', 1, 'Adam', '1234567890', '123', '10/2025', 3),
       ('MASTER_CARD', 2, 'Adam', '4567123890', '234', '08/2026', 3);

