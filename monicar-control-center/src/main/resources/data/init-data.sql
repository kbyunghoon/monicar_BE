INSERT INTO vehicle_types (vehicle_types_name, created_at, updated_at, deleted_at)
VALUES ('SUV', NOW(), NOW(), NULL),
       ('Truck', NOW(), NOW(), NULL),
       ('Convertible', NOW(), NOW(), NULL);


INSERT INTO company
(company_id,
 company_name,
 business_registration_number,
 created_at,
 updated_at,
 deleted_at)
VALUES (1, 'ABC Corporation', '123-45-67890', '2025-01-01 09:00:00', '2025-01-01 09:00:00', NULL),
       (2, 'XYZ Corporation', '987-65-43210', '2025-01-02 10:00:00', '2025-01-02 10:00:00', NULL),
       (3, 'Acme Inc', '111-22-3333', '2025-01-03 11:00:00', '2025-01-03 11:00:00', NULL),
       (4, 'Mega Co', '444-55-6666', '2025-01-04 12:00:00', '2025-01-04 12:00:00', NULL);

INSERT INTO vehicle_information
(vehicle_id, company_id, vehicle_type_id, vehicle_number, mdn, tid, mid, pv, did, sum, status, created_at, updated_at,
 deleted_at)
VALUES (1, 1, 1, '123가1234', 1010101010, 'TID001', 1, 1, 1, 10000, 'IN_OPERATION', '2025-01-01 10:00:00',
        '2025-01-01 10:00:00', NULL),
       (2, 1, 2, '234나5678', 2020202020, 'TID002', 2, 1, 2, 15000, 'NOT_REGISTERED', '2025-01-02 11:00:00',
        '2025-01-02 11:30:00', NULL),
       (3, 1, 3, '345다9012', 3030303030, 'TID003', 3, 1, 3, 20000, 'NOT_DRIVEN', '2025-01-03 12:00:00',
        '2025-01-03 12:00:00', NULL),
       (4, 1, 1, '456라3456', 4040404040, 'TID004', 4, 1, 4, 25000, 'IN_OPERATION', '2025-01-04 13:00:00',
        '2025-01-04 13:00:00', NULL),
       (5, 1, 2, '567마7890', 1234567890, 'TID005', 5, 2, 5, 0, 'NOT_DRIVEN', '2025-01-05 14:00:00',
        '2025-01-05 14:30:00',
        NULL),
       (6, 1, 3, '678바1234', 6060606060, 'TID006', 6, 2, 6, 35000, 'NOT_REGISTERED', '2025-01-06 15:00:00',
        '2025-01-06 15:00:00', NULL),
       (7, 1, 1, '789사5678', 7070707070, 'TID007', 7, 2, 7, 40000, 'IN_OPERATION', '2025-01-07 16:00:00',
        '2025-01-07 16:00:00', NULL),
       (8, 1, 2, '890아9012', 8080808080, 'TID008', 8, 3, 8, 45000, 'NOT_DRIVEN', '2025-01-08 17:00:00',
        '2025-01-08 17:30:00', NULL),
       (9, 1, 3, '901자3456', 9090909090, 'TID009', 9, 3, 9, 50000, 'IN_OPERATION', '2025-01-09 18:00:00',
        '2025-01-09 18:00:00', NULL),
       (10, 1, 1, '123차7890', 1111111111, 'TID010', 10, 3, 10, 55000, 'NOT_REGISTERED', '2025-01-10 19:00:00',
        '2025-01-10 19:30:00', NULL),
       (11, 1, 2, '234카1234', 1212121212, 'TID011', 11, 4, 11, 60000, 'NOT_DRIVEN', '2025-01-11 20:00:00',
        '2025-01-11 20:00:00', NULL),
       (12, 1, 3, '345타5678', 1313131313, 'TID012', 12, 4, 12, 65000, 'IN_OPERATION', '2025-01-12 21:00:00',
        '2025-01-12 21:00:00', NULL),
       (13, 1, 1, '456파9012', 1414141414, 'TID013', 13, 4, 13, 70000, 'NOT_DRIVEN', '2025-01-13 22:00:00',
        '2025-01-13 22:30:00', NULL),
       (14, 1, 2, '567하3456', 1515151515, 'TID014', 14, 5, 14, 75000, 'NOT_REGISTERED', '2025-01-14 23:00:00',
        '2025-01-14 23:00:00', NULL),
       (15, 1, 3, '678가7890', 1616161616, 'TID015', 15, 5, 15, 80000, 'IN_OPERATION', '2025-01-15 08:00:00',
        '2025-01-15 08:30:00', NULL),
       (16, 1, 1, '789나1234', 1717171717, 'TID016', 16, 5, 16, 85000, 'NOT_DRIVEN', '2025-01-16 09:00:00',
        '2025-01-16 09:00:00', NULL),
       (17, 1, 2, '890다5678', 1818181818, 'TID017', 17, 6, 17, 90000, 'NOT_REGISTERED', '2025-01-17 10:00:00',
        '2025-01-17 10:00:00', NULL),
       (18, 1, 3, '901라9012', 1919191919, 'TID018', 18, 6, 18, 95000, 'NOT_DRIVEN', '2025-01-18 11:00:00',
        '2025-01-18 11:30:00', NULL),
       (19, 1, 1, '123마3456', 2020202020, 'TID019', 19, 6, 19, 100000, 'IN_OPERATION', '2025-01-19 12:00:00',
        '2025-01-19 12:00:00', NULL),
       (20, 1, 2, '234바7890', 2121212121, 'TID020', 20, 7, 20, 105000, 'NOT_REGISTERED', '2025-01-20 13:00:00',
        '2025-01-20 13:00:00', NULL);

INSERT INTO department
(department_id,
 company_id,
 department_name,
 created_at,
 updated_at,
 deleted_at)
VALUES (1, 1, 'Sales', '2025-01-01 09:00:00', '2025-01-01 09:00:00', NULL),
       (2, 1, 'Marketing', '2025-01-01 09:00:00', '2025-01-01 09:00:00', NULL),

       (3, 2, 'HR', '2025-01-02 10:00:00', '2025-01-02 10:00:00', NULL),
       (4, 2, 'Finance', '2025-01-02 10:00:00', '2025-01-02 10:00:00', NULL),

       (5, 3, 'R&D', '2025-01-03 11:00:00', '2025-01-03 11:00:00', NULL),
       (6, 3, 'Manufacturing', '2025-01-03 11:00:00', '2025-01-03 11:00:00', NULL),

       (7, 4, 'Logistics', '2025-01-04 12:00:00', '2025-01-04 12:00:00', NULL),
       (8, 4, 'Operations', '2025-01-04 12:00:00', '2025-01-04 12:00:00', NULL);

INSERT INTO manager
(manager_id,
 department_id,
 email,
 login_id,
 password,
 nickname,
 role,
 last_logined_at,
 created_at,
 updated_at,
 deleted_at)
VALUES (1, 1, 'john@example.com', 'john', 'password', 'John Manager', 'ADMIN', '2025-01-01 09:30:00',
        '2025-01-01 09:00:00', '2025-01-01 09:00:00', NULL),
       (2, 2, 'jane@example.com', 'jane', 'password', 'Jane Manager', 'USER', '2025-01-02 10:30:00',
        '2025-01-02 10:00:00', '2025-01-02 10:00:00', NULL),
       (3, 3, 'smith@example.com', 'smith', 'password', 'Smith Manager', 'USER', '2025-01-03 11:30:00',
        '2025-01-03 11:00:00', '2025-01-03 11:00:00', NULL),
       (4, 4, 'mike@example.com', 'mike', 'password', 'Mike Manager', 'ADMIN', '2025-01-04 12:30:00',
        '2025-01-04 12:00:00', '2025-01-04 12:00:00', NULL),
       (5, 5, 'ellen@example.com', 'ellen', 'password', 'Ellen Manager', 'USER', '2025-01-05 13:30:00',
        '2025-01-05 13:00:00', '2025-01-05 13:00:00', NULL),
       (6, 6, 'bill@example.com', 'bill', 'password', 'Bill Manager', 'USER', '2025-01-06 14:30:00',
        '2025-01-06 14:00:00', '2025-01-06 14:00:00', NULL),
       (7, 7, 'kevin@example.com', 'kevin', 'password', 'Kevin Manager', 'USER', '2025-01-07 15:30:00',
        '2025-01-07 15:00:00', '2025-01-07 15:00:00', NULL),
       (8, 8, 'lucy@example.com', 'lucy', 'password', 'Lucy Manager', 'ADMIN', '2025-01-08 16:30:00',
        '2025-01-08 16:00:00', '2025-01-08 16:00:00', NULL);


INSERT INTO driving_history
(driving_history_id,
 vehicle_id,
 department_id,
 driver_email,
 initial_odometer,
 final_odometer,
 driving_distance,
 business_commute_distance,
 business_usage_distance,
 is_business_use,
 used_at,
 on_time,
 off_time,
 created_at,
 updated_at,
 deleted_at)
VALUES (1, 1, 1, 'john@example.com',
        10000,
        10030,
        30,
        5,
        25,
        TRUE,
        '2025-01-01 10:00:00',
        '2025-01-01 10:00:00',
        '2025-01-01 10:20:00',
        '2025-01-01 10:00:00',
        '2025-01-01 10:00:00',
        NULL),


       (2, 3, 2, 'jane@example.com',
        20000,
        20045,
        45,
        0,
        45,
        TRUE,
        '2025-01-02 11:00:00',
        '2025-01-02 11:00:00',
        '2025-01-02 11:40:00',
        '2025-01-02 11:00:00',
        '2025-01-02 11:00:00',
        NULL),


       (3, 5, 3, 'smith@example.com',
        30000,
        30025,
        25,
        25,
        0,
        FALSE,
        '2025-01-03 12:30:00',
        '2025-01-03 12:30:00',
        '2025-01-03 13:00:00',
        '2025-01-03 12:30:00',
        '2025-01-03 12:30:00',
        NULL),


       (4, 2, 4, 'mike@example.com',
        15000,
        15070,
        70,
        0,
        70,
        TRUE,
        '2025-01-04 14:00:00',
        '2025-01-04 14:00:00',
        '2025-01-04 14:50:00',
        '2025-01-04 14:00:00',
        '2025-01-04 14:00:00',
        NULL),


       (5, 7, 5, 'ellen@example.com',
        40000,
        40020,
        20,
        0,
        20,
        TRUE,
        '2025-01-05 16:30:00',
        '2025-01-05 16:30:00',
        '2025-01-05 17:00:00',
        '2025-01-05 16:30:00',
        '2025-01-05 16:30:00',
        NULL),


       (6, 10, 6, 'bill@example.com',
        55000,
        55040,
        40,
        0,
        40,
        TRUE,
        '2025-01-06 09:00:00',
        '2025-01-06 09:00:00',
        '2025-01-06 09:30:00',
        '2025-01-06 09:00:00',
        '2025-01-06 09:00:00',
        NULL),


       (7, 15, 7, 'kevin@example.com',
        80000,
        80050,
        50,
        20,
        30,
        TRUE,
        '2025-01-07 08:30:00',
        '2025-01-07 08:30:00',
        '2025-01-07 09:10:00',
        '2025-01-07 08:30:00',
        '2025-01-07 08:30:00',
        NULL),


       (8, 18, 8, 'lucy@example.com',
        95000,
        95030,
        30,
        0,
        30,
        TRUE,
        '2025-01-08 12:00:00',
        '2025-01-08 12:00:00',
        '2025-01-08 12:40:00',
        '2025-01-08 12:00:00',
        '2025-01-08 12:00:00',
        NULL),


       (9, 16, 1, 'john@example.com',
        85000,
        85060,
        60,
        10,
        50,
        TRUE,
        '2025-01-09 09:30:00',
        '2025-01-09 09:30:00',
        '2025-01-09 10:10:00',
        '2025-01-09 09:30:00',
        '2025-01-09 09:30:00',
        NULL),


       (10, 1, 2, 'jane@example.com',
        10050,
        10070,
        20,
        5,
        15,
        TRUE,
        '2025-01-10 10:00:00',
        '2025-01-10 10:00:00',
        '2025-01-10 10:30:00',
        '2025-01-10 10:00:00',
        '2025-01-10 10:00:00',
        NULL);