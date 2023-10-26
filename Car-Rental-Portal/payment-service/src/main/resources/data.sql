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
