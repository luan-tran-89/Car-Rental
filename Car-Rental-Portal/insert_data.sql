
INSERT INTO `car-rental`.user(user_name, email, first_name, last_name, phone, password, user_role, status, frequent_renter_type)
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

INSERT INTO `car-rental`.car (fixed_cost, cost_per_day, make, model, status)
VALUES (50, 20, 'BMW', 'X1', 'AVAILABLE'),
       (55, 20, 'BMW', 'X2', 'AVAILABLE'),
       (60, 20, 'BMW', 'X3', 'AVAILABLE'),
       (65, 20, 'BMW', 'X4', 'AVAILABLE'),
       (70, 25, 'BMW', 'X5', 'AVAILABLE'),
       (75, 30, 'BMW', 'X7', 'AVAILABLE'),
       (30, 20, 'HONDA', 'Civic', 'AVAILABLE'),
       (35, 20, 'HONDA', 'Accord', 'AVAILABLE'),
       (40, 25, 'HONDA', 'HR-V', 'AVAILABLE'),
       (50, 25, 'HONDA', 'CR-V', 'AVAILABLE'),
       (50, 30, 'HONDA', 'Pilot', 'AVAILABLE'),
       (50, 30, 'HONDA', 'Passport', 'AVAILABLE'),
       (50, 50, 'HONDA', 'Odyssey', 'AVAILABLE'),
       (30, 20, 'TOYOTA', 'Crown', 'AVAILABLE'),
       (30, 25, 'TOYOTA', 'Prius', 'AVAILABLE'),
       (30, 30, 'TOYOTA', 'Corolla', 'AVAILABLE'),
       (50, 30, 'TOYOTA', 'Rav 4', 'AVAILABLE'),
       (50, 35, 'TOYOTA', 'Highlander', 'AVAILABLE'),
       (50, 40, 'TOYOTA', 'Grand Highlander', 'AVAILABLE')
;

INSERT INTO `car-rental`.maintenance (description, end_date, start_date, status, car_id)
VALUES ('Daily Maintenance', '2023-07-10 12:00:00.000000', '2023-07-12 12:00:00.000000', 'FINISHED', 1),
       ('Daily Maintenance', '2023-05-01 12:00:00.000000', '2023-05-11 12:00:00.000000', 'FINISHED', 1),
       ('Daily Maintenance', '2023-03-10 12:00:00.000000', '2023-03-12 12:00:00.000000', 'FINISHED', 1),
       ('Daily Maintenance', '2023-02-07 12:00:00.000000', '2023-02-11 12:00:00.000000', 'FINISHED', 1),
       ('Daily Maintenance', '2023-01-02 12:00:00.000000', '2023-01-12 12:00:00.000000', 'FINISHED', 1),
       ('Daily Maintenance', '2023-07-10 12:00:00.000000', '2023-07-12 12:00:00.000000', 'FINISHED', 2),
       ('Daily Maintenance', '2023-05-01 12:00:00.000000', '2023-05-11 12:00:00.000000', 'FINISHED', 2),
       ('Daily Maintenance', '2023-03-10 12:00:00.000000', '2023-03-12 12:00:00.000000', 'FINISHED', 2),
       ('Daily Maintenance', '2023-02-07 12:00:00.000000', '2023-02-11 12:00:00.000000', 'FINISHED', 2),
       ('Daily Maintenance', '2023-01-02 12:00:00.000000', '2023-01-12 12:00:00.000000', 'FINISHED', 2),
       ('Daily Maintenance', '2023-07-10 12:00:00.000000', '2023-07-12 12:00:00.000000', 'FINISHED', 3),
       ('Daily Maintenance', '2023-05-01 12:00:00.000000', '2023-05-11 12:00:00.000000', 'FINISHED', 3),
       ('Daily Maintenance', '2023-03-10 12:00:00.000000', '2023-03-12 12:00:00.000000', 'FINISHED', 3),
       ('Daily Maintenance', '2023-02-07 12:00:00.000000', '2023-02-11 12:00:00.000000', 'FINISHED', 3),
       ('Daily Maintenance', '2023-01-02 12:00:00.000000', '2023-01-12 12:00:00.000000', 'FINISHED', 3),
       ('Daily Maintenance', '2023-07-10 12:00:00.000000', '2023-07-12 12:00:00.000000', 'FINISHED', 4),
       ('Daily Maintenance', '2023-05-01 12:00:00.000000', '2023-05-11 12:00:00.000000', 'FINISHED', 4),
       ('Daily Maintenance', '2023-03-10 12:00:00.000000', '2023-03-12 12:00:00.000000', 'FINISHED', 4),
       ('Daily Maintenance', '2023-02-07 12:00:00.000000', '2023-02-11 12:00:00.000000', 'FINISHED', 4),
       ('Daily Maintenance', '2023-01-02 12:00:00.000000', '2023-01-12 12:00:00.000000', 'FINISHED', 4)
;

INSERT INTO `car-rental`.payment_method
(card_type, approval_amount, balance, card_number, cvv, expiry_date, total_used, used_amount, user_id)
VALUES ('VISA', 10000, 900, '4234567890', '123', '10/2025', 100, 100, 5),
       ('MASTER_CARD', 10000, 9000, '9567123890', '234', '08/2026', 1000, 1000, 5),
       ('VISA', 5000, 4400, '4123437890', '222', '12/2025', 600, 600, 6),
       ('MASTER_CARD', 10000, 9700, '9567123890', '333', '08/2026', 300, 300, 6),
       ('VISA', 5000, 1000, '4290567890', '444', '07/2025', 4000, 4000, 7),
       ('MASTER_CARD', 10000, 8000, '8567123890', '555', '05/2026', 2000, 2000, 7),
       ('VISA', 30000, 20000, '40897662434', '666', '07/2025', 10000, 10000, 8),
       ('MASTER_CARD', 30000, 20000, '71126784421', '777', '05/2026', 10000, 10000, 8)
;

INSERT INTO `car-rental`.payments (description, payment_amount, status, transaction_date, method_id)
VALUES ('Honda CR-V, Insurance, 48 hours ', 100, 'COMPLETED', '2023-10-20', 1),
       ('Honda HR-V, Insurance, 48 hours ', 100, 'COMPLETED', '2023-08-20', 1),
       ('Honda CR-V, Insurance, 48 hours ', 100, 'COMPLETED', '2023-08-23', 3),
       ('Toyota Rav 4, Insurance, 48 hours ', 100, 'COMPLETED', '2023-07-11', 3),
       ('Honda CR-V, Insurance, 48 hours ', 100, 'COMPLETED', '2023-05-28', 3),
       ('Toyota Camry, Insurance, 48 hours ', 100, 'COMPLETED', '2023-03-20', 3),
       ('Toyota Highlander, Insurance, 48 hours ', 100, 'COMPLETED', '2023-02-12', 5),
       ('Toyota Rav 4, Insurance, 48 hours ', 100, 'COMPLETED', '2023-01-10', 5),
       ('BMW X7, Insurance, 48 hours ', 100, 'COMPLETED', '2023-09-20', 8),
       ('BMW X7, Insurance, 48 hours ', 100, 'COMPLETED', '2023-05-20', 8),
       ('BMW X5, Insurance, 48 hours ', 100, 'COMPLETED', '2023-04-06', 8),
       ('BMW X3, Insurance, 48 hours ', 100, 'COMPLETED', '2023-03-08', 8),
       ('Toyota Rav 4, Insurance, 48 hours ', 100, 'COMPLETED', '2023-02-10', 8)
;

INSERT INTO `car-rental`.rental (car_id, end_date, payment_id, start_date, total_cost, user_id)
VALUES (1, '2023-10-30 19:00:00.000000', 1, '2023-10-26 19:00:00.000000', 100, 5),
       (9, '2023-10-30 19:00:00.000000', 2, '2023-10-26 19:00:00.000000', 100, 5),
       (10, '2023-10-30 19:00:00.000000', 3, '2023-10-26 19:00:00.000000', 100, 6),
       (17, '2023-10-30 19:00:00.000000', 4, '2023-10-26 19:00:00.000000', 100, 6),
       (10, '2023-10-30 19:00:00.000000', 5, '2023-10-26 19:00:00.000000', 100, 6),
       (14, '2023-10-30 19:00:00.000000', 6, '2023-10-26 19:00:00.000000', 100, 6),
       (18, '2023-10-30 19:00:00.000000', 7, '2023-10-26 19:00:00.000000', 100, 7),
       (17, '2023-10-30 19:00:00.000000', 8, '2023-10-26 19:00:00.000000', 100, 7),
       (6, '2023-10-10 19:00:00.000000', 9, '2023-10-26 19:00:00.000000', 500, 8),
       (6, '2023-05-20 19:00:00.000000', 10, '2023-05-26 19:00:00.000000', 200, 8),
       (5, '2023-04-06 19:00:00.000000', 11, '2023-04-26 19:00:00.000000', 300, 8),
       (3, '2023-03-08 19:00:00.000000', 12, '2023-03-18 19:00:00.000000', 300, 8)
;

