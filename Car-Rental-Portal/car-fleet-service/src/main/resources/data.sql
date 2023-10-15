INSERT INTO `car-rental`.car (car_id, base_code, per_day_cost, make, model, status)
VALUES (1, 60, 20, 'BMW', 'X5', 'AVAILABLE'),
       (2, 50, 20, 'HONDA', 'CR-V', 'AVAILABLE'),
       (3, 70, 25, 'BMW', 'X7', 'AVAILABLE');
INSERT INTO `car-rental`.maintenance (id, description, end_date, start_date, status, car_id)
VALUES (1, 'Daily Maintenence', '2023-10-10 12:00:00.000000', '2023-10-12 12:00:00.000000', 'FINISHED', 1),
       (2, 'Daily Maintenence', '2023-10-01 12:00:00.000000', '2023-10-11 12:00:00.000000', 'FINISHED', 1);

