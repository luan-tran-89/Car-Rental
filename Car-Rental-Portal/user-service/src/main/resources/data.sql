INSERT INTO user(user_name, email, first_name, last_name, phone, password, user_role, status, frequent_rental_type)
VALUES ('admin', 'admin@miu.edu', 'admin', 'admin', '20612346482',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'ADMIN', 'ACTIVE', 'NONE'),
       ('Manager 1', 'manager@miu.edu', 'manager', 'John', '20612346481',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'MANAGER', 'ACTIVE', 'NONE'),
       ('customer 1', 'customer@miu.edu', 'customer', 'Adam', '20612346486',
        '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', -- 123
        'CUSTOMER', 'ACTIVE', 'NONE');

-- INSERT INTO role (id, role)
-- VALUES (1, 'ADMIN');
-- INSERT INTO role (id, role)
-- VALUES (2, 'MANAGER');
-- INSERT INTO role (id, role)
-- VALUES (3, 'CUSTOMER');

-- INSERT INTO users_roles (user_id, roles_id)
-- VALUES (1, 1);
