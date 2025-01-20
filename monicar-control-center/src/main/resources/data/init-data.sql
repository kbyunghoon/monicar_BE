INSERT INTO vehicle_types (vehicle_types_name, created_at, updated_at, deleted_at)
VALUES ('SUV', '2025-01-10 10:30:00', '2025-01-10 10:30:00', NULL),
       ('Truck', '2025-01-10 10:30:00', '2025-01-10 10:30:00', NULL),
       ('Convertible', '2025-01-10 10:30:00', '2025-01-10 10:30:00', NULL);


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
(vehicle_id, company_id, vehicle_type_id, vehicle_number, mdn, tid, mid, pv, did, driving_days, sum, status,
 delivery_date, created_at, updated_at, deleted_at)
VALUES (1, 1, 1, '123가1234', 1010101010, 'TID001', 1, 1, 1, 0, 10000, 'IN_OPERATION', '2025-01-01 10:00:00',
        '2025-01-01 10:00:00', '2025-01-01 10:00:00', NULL),
       (2, 1, 2, '234나5678', 2020202020, 'TID002', 2, 1, 2, 0, 15000, 'NOT_REGISTERED', '2025-01-02 11:00:00',
        '2025-01-02 11:00:00', '2025-01-02 11:30:00', NULL),
       (3, 1, 3, '345다9012', 3030303030, 'TID003', 3, 1, 3, 0, 20000, 'NOT_DRIVEN', '2025-01-03 12:00:00',
        '2025-01-03 12:00:00', '2025-01-03 12:00:00', NULL),
       (4, 1, 1, '456라3456', 4040404040, 'TID004', 4, 1, 4, 0, 25000, 'IN_OPERATION', '2025-01-04 13:00:00',
        '2025-01-04 13:00:00', '2025-01-04 13:00:00', NULL),
       (5, 1, 2, '567마7890', 1234567890, 'TID005', 5, 2, 5, 0, 0, 'NOT_DRIVEN', '2025-01-05 14:00:00',
        '2025-01-05 14:00:00', '2025-01-05 14:30:00', NULL),
       (6, 1, 3, '678바1234', 6060606060, 'TID006', 6, 2, 6, 0, 35000, 'NOT_REGISTERED', '2025-01-06 15:00:00',
        '2025-01-06 15:00:00', '2025-01-06 15:00:00', NULL),
       (7, 1, 1, '789사5678', 7070707070, 'TID007', 7, 2, 7, 0, 40000, 'IN_OPERATION', '2025-01-07 16:00:00',
        '2025-01-07 16:00:00', '2025-01-07 16:00:00', NULL),
       (8, 1, 2, '890아9012', 8080808080, 'TID008', 8, 3, 8, 0, 45000, 'NOT_DRIVEN', '2025-01-08 17:00:00',
        '2025-01-08 17:00:00', '2025-01-08 17:30:00', NULL),
       (9, 1, 3, '901자3456', 9090909090, 'TID009', 9, 3, 9, 0, 50000, 'IN_OPERATION', '2025-01-09 18:00:00',
        '2025-01-09 18:00:00', '2025-01-09 18:00:00', NULL),
       (10, 1, 1, '123차7890', 1111111111, 'TID010', 10, 3, 10, 0, 55000, 'NOT_REGISTERED', '2025-01-10 19:00:00',
        '2025-01-10 19:00:00', '2025-01-10 19:30:00', NULL),
       (11, 1, 2, '234카1234', 1212121212, 'TID011', 11, 4, 11, 0, 60000, 'NOT_DRIVEN', '2025-01-11 20:00:00',
        '2025-01-11 20:00:00', '2025-01-11 20:00:00', NULL),
       (12, 1, 3, '345타5678', 1313131313, 'TID012', 12, 4, 12, 0, 65000, 'IN_OPERATION', '2025-01-12 21:00:00',
        '2025-01-12 21:00:00', '2025-01-12 21:00:00', NULL),
       (13, 1, 1, '456파9012', 1414141414, 'TID013', 13, 4, 13, 0, 70000, 'NOT_DRIVEN', '2025-01-13 22:00:00',
        '2025-01-13 22:00:00', '2025-01-13 22:30:00', NULL),
       (14, 1, 2, '567하3456', 1515151515, 'TID014', 14, 5, 14, 0, 75000, 'NOT_REGISTERED', '2025-01-14 23:00:00',
        '2025-01-14 23:00:00', '2025-01-14 23:00:00', NULL),
       (15, 1, 3, '678가7890', 1616161616, 'TID015', 15, 5, 15, 0, 80000, 'IN_OPERATION', '2025-01-15 08:00:00',
        '2025-01-15 08:00:00', '2025-01-15 08:30:00', NULL),
       (16, 1, 1, '789나1234', 1717171717, 'TID016', 16, 5, 16, 0, 85000, 'NOT_DRIVEN', '2025-01-16 09:00:00',
        '2025-01-16 09:00:00', '2025-01-16 09:00:00', NULL),
       (17, 1, 2, '890다5678', 1818181818, 'TID017', 17, 6, 17, 0, 90000, 'NOT_REGISTERED', '2025-01-17 10:00:00',
        '2025-01-17 10:00:00', '2025-01-17 10:00:00', NULL),
       (18, 1, 3, '901라9012', 1919191919, 'TID018', 18, 6, 18, 0, 95000, 'NOT_DRIVEN', '2025-01-18 11:00:00',
        '2025-01-18 11:00:00', '2025-01-18 11:30:00', NULL),
       (19, 1, 1, '123마3456', 2020202020, 'TID019', 19, 6, 19, 0, 100000, 'IN_OPERATION', '2025-01-19 12:00:00',
        '2025-01-19 12:00:00', '2025-01-19 12:00:00', NULL),
       (20, 1, 2, '234바7890', 2121212121, 'TID020', 20, 7, 20, 0, 105000, 'NOT_REGISTERED', '2025-01-20 13:00:00',
        '2025-01-20 13:00:00', '2025-01-20 13:00:00', NULL);


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
 use_purpose,
 start_time,
 end_time,
 created_at,
 updated_at,
 deleted_at)
VALUES (1, 1, 1, 'john@example.com', 10000, 10030, 30, 'NORMAL', '2025-01-01 10:00:00', '2025-01-01 10:20:00',
        '2025-01-01 10:00:00', '2025-01-01 10:00:00', NULL),
       (2, 3, 2, 'jane@example.com', 20000, 20045, 45, 'NORMAL', '2025-01-02 11:00:00', '2025-01-02 11:40:00',
        '2025-01-02 11:00:00', '2025-01-02 11:00:00', NULL),
       (3, 5, 3, 'smith@example.com', 30000, 30025, 25, 'COMMUTE', '2025-01-03 12:30:00', '2025-01-03 13:00:00',
        '2025-01-03 12:30:00', '2025-01-03 12:30:00', NULL),
       (4, 2, 4, 'mike@example.com', 15000, 15070, 70, 'NORMAL', '2025-01-04 14:00:00', '2025-01-04 14:50:00',
        '2025-01-04 14:00:00', '2025-01-04 14:00:00', NULL),
       (5, 7, 5, 'ellen@example.com', 40000, 40020, 20, 'NORMAL', '2025-01-05 16:30:00', '2025-01-05 17:00:00',
        '2025-01-05 16:30:00', '2025-01-05 16:30:00', NULL),
       (6, 10, 6, 'bill@example.com', 55000, 55040, 40, 'NORMAL', '2025-01-06 09:00:00', '2025-01-06 09:30:00',
        '2025-01-06 09:00:00', '2025-01-06 09:00:00', NULL),
       (7, 15, 7, 'kevin@example.com', 80000, 80050, 50, 'NORMAL', '2025-01-07 08:30:00', '2025-01-07 09:10:00',
        '2025-01-07 08:30:00', '2025-01-07 08:30:00', NULL),
       (8, 18, 8, 'lucy@example.com', 95000, 95030, 30, 'NORMAL', '2025-01-08 12:00:00', '2025-01-08 12:40:00',
        '2025-01-08 12:00:00', '2025-01-08 12:00:00', NULL),
       (9, 16, 1, 'john@example.com', 85000, 85060, 60, 'NORMAL', '2025-01-09 09:30:00', '2025-01-09 10:10:00',
        '2025-01-09 09:30:00', '2025-01-09 09:30:00', NULL),
       (10, 1, 2, 'jane@example.com', 10050, 10070, 20, 'NORMAL', '2025-01-10 10:00:00', '2025-01-10 10:30:00',
        '2025-01-10 10:00:00', '2025-01-10 10:00:00', NULL);


INSERT INTO cycle_info (vehicle_id, interval_at, status, lat, lng, ang, spd, created_at)
VALUES (19, '2025-01-25 00:00:02', 'active', 37068615, 126358301, 360, 100, '2025-02-01 10:10:00'),
       (8, '2025-01-23 00:00:02', 'active', 35835085, 126246805, 360, 100, '2025-02-01 10:10:00'),
       (14, '2025-01-27 00:00:02', 'active', 33276700, 126307205, 360, 100, '2025-02-01 10:10:00'),
       (20, '2025-01-23 00:00:02', 'active', 36348572, 126177669, 360, 100, '2025-02-01 10:10:00'),
       (9, '2025-01-26 00:00:05', 'active', 36772477, 126445634, 360, 100, '2025-02-01 10:10:00'),
       (7, '2025-01-23 00:00:03', 'active', 33152979, 129818351, 360, 100, '2025-02-01 10:10:00'),
       (6, '2025-01-29 00:00:04', 'active', 38225425, 128790783, 360, 100, '2025-02-01 10:10:00'),
       (16, '2025-01-28 00:00:02', 'active', 37034210, 126291686, 360, 100, '2025-02-01 10:10:00'),
       (7, '2025-01-23 00:00:01', 'active', 33152975, 129818343, 360, 100, '2025-02-01 10:10:00'),
       (7, '2025-01-23 00:00:05', 'active', 33152983, 129818359, 360, 100, '2025-02-01 10:10:00'),
       (17, '2025-01-25 00:00:01', 'active', 37729597, 124752114, 360, 100, '2025-02-01 10:10:00'),
       (6, '2025-01-29 00:00:05', 'active', 38225431, 128790786, 360, 100, '2025-02-01 10:10:00'),
       (2, '2025-01-23 00:00:01', 'active', 36005885, 124733634, 360, 100, '2025-02-01 10:10:00'),
       (7, '2025-01-23 00:00:02', 'active', 33152977, 129818347, 360, 100, '2025-02-01 10:10:00'),
       (15, '2025-01-28 00:00:02', 'active', 34667588, 128709520, 360, 100, '2025-02-01 10:10:00'),
       (20, '2025-01-23 00:00:04', 'active', 36348582, 126177673, 360, 100, '2025-02-01 10:10:00'),
       (18, '2025-01-21 00:00:05', 'active', 38530983, 125262557, 360, 100, '2025-02-01 10:10:00'),
       (18, '2025-01-21 00:00:02', 'active', 38530959, 125262542, 360, 100, '2025-02-01 10:10:00'),
       (15, '2025-01-28 00:00:01', 'active', 34667582, 128709514, 360, 100, '2025-02-01 10:10:00'),
       (9, '2025-01-26 00:00:04', 'active', 36772470, 126445633, 360, 100, '2025-02-01 10:10:00'),
       (9, '2025-01-26 00:00:02', 'active', 36772456, 126445631, 360, 100, '2025-02-01 10:10:00'),
       (11, '2025-01-29 00:00:03', 'active', 33772904, 129508013, 360, 100, '2025-02-01 10:10:00'),
       (15, '2025-01-28 00:00:03', 'active', 34667594, 128709526, 360, 100, '2025-02-01 10:10:00'),
       (2, '2025-01-23 00:00:04', 'active', 36005888, 124733649, 360, 100, '2025-02-01 10:10:00'),
       (16, '2025-01-28 00:00:03', 'active', 37034213, 126291688, 360, 100, '2025-02-01 10:10:00'),
       (16, '2025-01-28 00:00:05', 'active', 37034219, 126291692, 360, 100, '2025-02-01 10:10:00'),
       (20, '2025-01-23 00:00:01', 'active', 36348567, 126177667, 360, 100, '2025-02-01 10:10:00'),
       (1, '2025-01-27 00:00:01', 'active', 36406831, 128427710, 360, 100, '2025-02-01 10:10:00'),
       (5, '2025-01-23 00:00:05', 'active', 37848007, 127204632, 360, 100, '2025-02-01 10:10:00'),
       (6, '2025-01-29 00:00:02', 'active', 38225413, 128790777, 360, 100, '2025-02-01 10:10:00'),
       (11, '2025-01-29 00:00:02', 'active', 33772896, 129508012, 360, 100, '2025-02-01 10:10:00'),
       (3, '2025-01-25 00:00:03', 'active', 35135632, 127333929, 360, 100, '2025-02-01 10:10:00'),
       (1, '2025-01-27 00:00:03', 'active', 36406837, 128427716, 360, 100, '2025-02-01 10:10:00'),
       (16, '2025-01-28 00:00:04', 'active', 37034216, 126291690, 360, 100, '2025-02-01 10:10:00'),
       (10, '2025-01-28 00:00:01', 'active', 36161655, 129408316, 360, 100, '2025-02-01 10:10:00'),
       (2, '2025-01-23 00:00:03', 'active', 36005887, 124733644, 360, 100, '2025-02-01 10:10:00'),
       (11, '2025-01-29 00:00:05', 'active', 33772920, 129508015, 360, 100, '2025-02-01 10:10:00'),
       (15, '2025-01-28 00:00:05', 'active', 34667606, 128709538, 360, 100, '2025-02-01 10:10:00'),
       (12, '2025-01-27 00:00:03', 'active', 33306577, 127148500, 360, 100, '2025-02-01 10:10:00'),
       (10, '2025-01-28 00:00:05', 'active', 36161687, 129408344, 360, 100, '2025-02-01 10:10:00'),
       (12, '2025-01-27 00:00:02', 'active', 33306571, 127148494, 360, 100, '2025-02-01 10:10:00'),
       (14, '2025-01-27 00:00:05', 'active', 33276703, 126307229, 360, 100, '2025-02-01 10:10:00'),
       (10, '2025-01-28 00:00:02', 'active', 36161663, 129408323, 360, 100, '2025-02-01 10:10:00'),
       (8, '2025-01-23 00:00:05', 'active', 35835100, 126246817, 360, 100, '2025-02-01 10:10:00'),
       (7, '2025-01-23 00:00:04', 'active', 33152981, 129818355, 360, 100, '2025-02-01 10:10:00'),
       (17, '2025-01-25 00:00:03', 'active', 37729605, 124752130, 360, 100, '2025-02-01 10:10:00'),
       (10, '2025-01-28 00:00:04', 'active', 36161679, 129408337, 360, 100, '2025-02-01 10:10:00'),
       (9, '2025-01-26 00:00:01', 'active', 36772449, 126445630, 360, 100, '2025-02-01 10:10:00'),
       (16, '2025-01-28 00:00:01', 'active', 37034207, 126291684, 360, 100, '2025-02-01 10:10:00'),
       (5, '2025-01-23 00:00:03', 'active', 37848001, 127204620, 360, 100, '2025-02-01 10:10:00'),
       (1, '2025-01-27 00:00:04', 'active', 36406840, 128427719, 360, 100, '2025-02-01 10:10:00'),
       (18, '2025-01-21 00:00:04', 'active', 38530975, 125262552, 360, 100, '2025-02-01 10:10:00'),
       (9, '2025-01-26 00:00:03', 'active', 36772463, 126445632, 360, 100, '2025-02-01 10:10:00'),
       (13, '2025-01-26 00:00:04', 'active', 35229567, 129903588, 360, 100, '2025-02-01 10:10:00'),
       (13, '2025-01-26 00:00:02', 'active', 35229563, 129903572, 360, 100, '2025-02-01 10:10:00'),
       (8, '2025-01-23 00:00:01', 'active', 35835080, 126246801, 360, 100, '2025-02-01 10:10:00'),
       (5, '2025-01-23 00:00:04', 'active', 37848004, 127204626, 360, 100, '2025-02-01 10:10:00'),
       (13, '2025-01-26 00:00:05', 'active', 35229569, 129903596, 360, 100, '2025-02-01 10:10:00'),
       (5, '2025-01-23 00:00:01', 'active', 37847995, 127204608, 360, 100, '2025-02-01 10:10:00'),
       (4, '2025-01-23 00:00:02', 'active', 35077241, 129462135, 360, 100, '2025-02-01 10:10:00'),
       (20, '2025-01-23 00:00:03', 'active', 36348577, 126177671, 360, 100, '2025-02-01 10:10:00'),
       (3, '2025-01-25 00:00:04', 'active', 35135638, 127333934, 360, 100, '2025-02-01 10:10:00'),
       (13, '2025-01-26 00:00:01', 'active', 35229561, 129903564, 360, 100, '2025-02-01 10:10:00'),
       (13, '2025-01-26 00:00:03', 'active', 35229565, 129903580, 360, 100, '2025-02-01 10:10:00'),
       (15, '2025-01-28 00:00:04', 'active', 34667600, 128709532, 360, 100, '2025-02-01 10:10:00'),
       (11, '2025-01-29 00:00:01', 'active', 33772888, 129508011, 360, 100, '2025-02-01 10:10:00'),
       (3, '2025-01-25 00:00:01', 'active', 35135620, 127333919, 360, 100, '2025-02-01 10:10:00'),
       (14, '2025-01-27 00:00:03', 'active', 33276701, 126307213, 360, 100, '2025-02-01 10:10:00'),
       (17, '2025-01-25 00:00:04', 'active', 37729609, 124752138, 360, 100, '2025-02-01 10:10:00'),
       (4, '2025-01-23 00:00:05', 'active', 35077259, 129462147, 360, 100, '2025-02-01 10:10:00'),
       (14, '2025-01-27 00:00:04', 'active', 33276702, 126307221, 360, 100, '2025-02-01 10:10:00'),
       (12, '2025-01-27 00:00:04', 'active', 33306583, 127148506, 360, 100, '2025-02-01 10:10:00'),
       (6, '2025-01-29 00:00:03', 'active', 38225419, 128790780, 360, 100, '2025-02-01 10:10:00'),
       (4, '2025-01-23 00:00:01', 'active', 35077235, 129462131, 360, 100, '2025-02-01 10:10:00'),
       (8, '2025-01-23 00:00:03', 'active', 35835090, 126246809, 360, 100, '2025-02-01 10:10:00'),
       (8, '2025-01-23 00:00:04', 'active', 35835095, 126246813, 360, 100, '2025-02-01 10:10:00'),
       (12, '2025-01-27 00:00:05', 'active', 33306589, 127148512, 360, 100, '2025-02-01 10:10:00'),
       (10, '2025-01-28 00:00:03', 'active', 36161671, 129408330, 360, 100, '2025-02-01 10:10:00'),
       (12, '2025-01-27 00:00:01', 'active', 33306565, 127148488, 360, 100, '2025-02-01 10:10:00'),
       (20, '2025-01-23 00:00:05', 'active', 36348587, 126177675, 360, 100, '2025-02-01 10:10:00'),
       (5, '2025-01-23 00:00:02', 'active', 37847998, 127204614, 360, 100, '2025-02-01 10:10:00'),
       (4, '2025-01-23 00:00:03', 'active', 35077247, 129462139, 360, 100, '2025-02-01 10:10:00'),
       (2, '2025-01-23 00:00:02', 'active', 36005886, 124733639, 360, 100, '2025-02-01 10:10:00'),
       (17, '2025-01-25 00:00:02', 'active', 37729601, 124752122, 360, 100, '2025-02-01 10:10:00'),
       (4, '2025-01-23 00:00:04', 'active', 35077253, 129462143, 360, 100, '2025-02-01 10:10:00'),
       (2, '2025-01-23 00:00:05', 'active', 36005889, 124733654, 360, 100, '2025-02-01 10:10:00'),
       (14, '2025-01-27 00:00:01', 'active', 33276699, 126307197, 360, 100, '2025-02-01 10:10:00'),
       (17, '2025-01-25 00:00:05', 'active', 37729613, 124752146, 360, 100, '2025-02-01 10:10:00'),
       (19, '2025-01-25 00:00:01', 'active', 37068609, 126358294, 360, 100, '2025-02-01 10:10:00'),
       (1, '2025-01-27 00:00:05', 'active', 36406843, 128427722, 360, 100, '2025-02-01 10:10:00'),
       (18, '2025-01-21 00:00:03', 'active', 38530967, 125262547, 360, 100, '2025-02-01 10:10:00'),
       (3, '2025-01-25 00:00:05', 'active', 35135644, 127333939, 360, 100, '2025-02-01 10:10:00'),
       (19, '2025-01-25 00:00:05', 'active', 37068633, 126358322, 360, 100, '2025-02-01 10:10:00'),
       (19, '2025-01-25 00:00:04', 'active', 37068627, 126358315, 360, 100, '2025-02-01 10:10:00'),
       (3, '2025-01-25 00:00:02', 'active', 35135626, 127333924, 360, 100, '2025-02-01 10:10:00'),
       (11, '2025-01-29 00:00:04', 'active', 33772912, 129508014, 360, 100, '2025-02-01 10:10:00'),
       (18, '2025-01-21 00:00:01', 'active', 38530951, 125262537, 360, 100, '2025-02-01 10:10:00'),
       (1, '2025-01-27 00:00:02', 'active', 36406834, 128427713, 360, 100, '2025-02-01 10:10:00'),
       (19, '2025-01-25 00:00:03', 'active', 37068621, 126358308, 360, 100, '2025-02-01 10:10:00'),
       (6, '2025-01-29 00:00:01', 'active', 38225407, 128790774, 360, 100, '2025-02-01 10:10:00');


INSERT INTO vehicle_event (vehicle_id, type, event_at, created_at)
VALUES (1, 'on', '2025-01-01 08:00:00', '2025-01-10 10:30:00'),
       (1, 'off', '2025-01-01 08:30:00', '2025-01-10 10:30:00'),
       (1, 'on', '2025-01-01 09:00:00', '2025-01-10 10:30:00'),
       (1, 'off', '2025-01-01 09:30:00', '2025-01-10 10:30:00'),
       (1, 'on', '2025-01-01 10:00:00', '2025-01-10 10:30:00'),
       (1, 'off', '2025-01-01 10:30:00', '2025-01-10 10:30:00'),

       (2, 'on', '2025-01-02 08:00:00', '2025-01-10 10:30:00'),
       (2, 'off', '2025-01-02 08:30:00', '2025-01-10 10:30:00'),
       (2, 'on', '2025-01-02 09:00:00', '2025-01-10 10:30:00'),
       (2, 'off', '2025-01-02 09:30:00', '2025-01-10 10:30:00'),
       (2, 'on', '2025-01-02 10:00:00', '2025-01-10 10:30:00'),
       (2, 'off', '2025-01-02 10:30:00', '2025-01-10 10:30:00'),

       (3, 'on', '2025-01-03 08:00:00', '2025-01-10 10:30:00'),
       (3, 'off', '2025-01-03 08:30:00', '2025-01-10 10:30:00'),
       (3, 'on', '2025-01-03 09:00:00', '2025-01-10 10:30:00'),
       (3, 'off', '2025-01-03 09:30:00', '2025-01-10 10:30:00'),
       (3, 'on', '2025-01-03 10:00:00', '2025-01-10 10:30:00'),
       (3, 'off', '2025-01-03 10:30:00', '2025-01-10 10:30:00'),

       (4, 'on', '2025-01-04 08:00:00', '2025-01-10 10:30:00'),
       (4, 'off', '2025-01-04 08:30:00', '2025-01-10 10:30:00'),
       (4, 'on', '2025-01-04 09:00:00', '2025-01-10 10:30:00'),
       (4, 'off', '2025-01-04 09:30:00', '2025-01-10 10:30:00'),
       (4, 'on', '2025-01-04 10:00:00', '2025-01-10 10:30:00'),
       (4, 'off', '2025-01-04 10:30:00', '2025-01-10 10:30:00'),

       (5, 'on', '2025-01-05 08:00:00', '2025-01-10 10:30:00'),
       (5, 'off', '2025-01-05 08:30:00', '2025-01-10 10:30:00'),
       (5, 'on', '2025-01-05 09:00:00', '2025-01-10 10:30:00'),
       (5, 'off', '2025-01-05 09:30:00', '2025-01-10 10:30:00'),
       (5, 'on', '2025-01-05 10:00:00', '2025-01-10 10:30:00'),
       (5, 'off', '2025-01-05 10:30:00', '2025-01-10 10:30:00'),

       (6, 'on', '2025-01-06 08:00:00', '2025-01-10 10:30:00'),
       (6, 'off', '2025-01-06 08:30:00', '2025-01-10 10:30:00'),
       (6, 'on', '2025-01-06 09:00:00', '2025-01-10 10:30:00'),
       (6, 'off', '2025-01-06 09:30:00', '2025-01-10 10:30:00'),
       (6, 'on', '2025-01-06 10:00:00', '2025-01-10 10:30:00'),
       (6, 'off', '2025-01-06 10:30:00', '2025-01-10 10:30:00'),

       (7, 'on', '2025-01-07 08:00:00', '2025-01-10 10:30:00'),
       (7, 'off', '2025-01-07 08:30:00', '2025-01-10 10:30:00'),
       (7, 'on', '2025-01-07 09:00:00', '2025-01-10 10:30:00'),
       (7, 'off', '2025-01-07 09:30:00', '2025-01-10 10:30:00'),
       (7, 'on', '2025-01-07 10:00:00', '2025-01-10 10:30:00'),
       (7, 'off', '2025-01-07 10:30:00', '2025-01-10 10:30:00'),

       (8, 'off', '2025-01-08 08:00:00', '2025-01-10 10:30:00'),
       (8, 'on', '2025-01-08 08:30:00', '2025-01-10 10:30:00'),
       (8, 'off', '2025-01-08 09:00:00', '2025-01-10 10:30:00'),
       (8, 'on', '2025-01-08 09:30:00', '2025-01-10 10:30:00'),
       (8, 'off', '2025-01-08 10:00:00', '2025-01-10 10:30:00'),
       (8, 'on', '2025-01-08 10:30:00', '2025-01-10 10:30:00'),

       (9, 'on', '2025-01-09 08:00:00', '2025-01-10 10:30:00'),
       (9, 'off', '2025-01-09 08:30:00', '2025-01-10 10:30:00'),
       (9, 'on', '2025-01-09 09:00:00', '2025-01-10 10:30:00'),
       (9, 'off', '2025-01-09 09:30:00', '2025-01-10 10:30:00'),
       (9, 'on', '2025-01-09 10:00:00', '2025-01-10 10:30:00'),
       (9, 'off', '2025-01-09 10:30:00', '2025-01-10 10:30:00'),

       (10, 'on', '2025-01-10 08:00:00', '2025-01-10 10:30:00'),
       (10, 'off', '2025-01-10 08:30:00', '2025-01-10 10:30:00'),
       (10, 'on', '2025-01-10 09:00:00', '2025-01-10 10:30:00'),
       (10, 'off', '2025-01-10 09:30:00', '2025-01-10 10:30:00'),
       (10, 'on', '2025-01-10 10:00:00', '2025-01-10 10:30:00'),
       (10, 'off', '2025-01-10 10:30:00', '2025-01-10 10:30:00'),

       (11, 'on', '2025-01-11 08:00:00', '2025-01-10 10:30:00'),
       (11, 'off', '2025-01-11 08:30:00', '2025-01-10 10:30:00'),
       (11, 'on', '2025-01-11 09:00:00', '2025-01-10 10:30:00'),
       (11, 'off', '2025-01-11 09:30:00', '2025-01-10 10:30:00'),
       (11, 'on', '2025-01-11 10:00:00', '2025-01-10 10:30:00'),
       (11, 'off', '2025-01-11 10:30:00', '2025-01-10 10:30:00'),

       (12, 'on', '2025-01-12 08:00:00', '2025-01-10 10:30:00'),
       (12, 'off', '2025-01-12 08:30:00', '2025-01-10 10:30:00'),
       (12, 'on', '2025-01-12 09:00:00', '2025-01-10 10:30:00'),
       (12, 'off', '2025-01-12 09:30:00', '2025-01-10 10:30:00'),
       (12, 'on', '2025-01-12 10:00:00', '2025-01-10 10:30:00'),
       (12, 'off', '2025-01-12 10:30:00', '2025-01-10 10:30:00'),

       (13, 'on', '2025-01-13 08:00:00', '2025-01-10 10:30:00'),
       (13, 'off', '2025-01-13 08:30:00', '2025-01-10 10:30:00'),
       (13, 'on', '2025-01-13 09:00:00', '2025-01-10 10:30:00'),
       (13, 'off', '2025-01-13 09:30:00', '2025-01-10 10:30:00'),
       (13, 'on', '2025-01-13 10:00:00', '2025-01-10 10:30:00'),
       (13, 'off', '2025-01-13 10:30:00', '2025-01-10 10:30:00'),

       (14, 'on', '2025-01-14 08:00:00', '2025-01-10 10:30:00'),
       (14, 'off', '2025-01-14 08:30:00', '2025-01-10 10:30:00'),
       (14, 'on', '2025-01-14 09:00:00', '2025-01-10 10:30:00'),
       (14, 'off', '2025-01-14 09:30:00', '2025-01-10 10:30:00'),
       (14, 'on', '2025-01-14 10:00:00', '2025-01-10 10:30:00'),
       (14, 'off', '2025-01-14 10:30:00', '2025-01-10 10:30:00'),

       (15, 'on', '2025-01-15 08:00:00', '2025-01-10 10:30:00'),
       (15, 'off', '2025-01-15 08:30:00', '2025-01-10 10:30:00'),
       (15, 'on', '2025-01-15 09:00:00', '2025-01-10 10:30:00'),
       (15, 'off', '2025-01-15 09:30:00', '2025-01-10 10:30:00'),
       (15, 'on', '2025-01-15 10:00:00', '2025-01-10 10:30:00'),
       (15, 'off', '2025-01-15 10:30:00', '2025-01-10 10:30:00'),

       (16, 'on', '2025-01-16 08:00:00', '2025-01-10 10:30:00'),
       (16, 'off', '2025-01-16 08:30:00', '2025-01-10 10:30:00'),
       (16, 'on', '2025-01-16 09:00:00', '2025-01-10 10:30:00'),
       (16, 'off', '2025-01-16 09:30:00', '2025-01-10 10:30:00'),
       (16, 'on', '2025-01-16 10:00:00', '2025-01-10 10:30:00'),
       (16, 'off', '2025-01-16 10:30:00', '2025-01-10 10:30:00'),

       (17, 'on', '2025-01-17 08:00:00', '2025-01-10 10:30:00'),
       (17, 'off', '2025-01-17 08:30:00', '2025-01-10 10:30:00'),
       (17, 'on', '2025-01-17 09:00:00', '2025-01-10 10:30:00'),
       (17, 'off', '2025-01-17 09:30:00', '2025-01-10 10:30:00'),
       (17, 'on', '2025-01-17 10:00:00', '2025-01-10 10:30:00'),
       (17, 'off', '2025-01-17 10:30:00', '2025-01-10 10:30:00'),

       (18, 'on', '2025-01-18 08:00:00', '2025-01-10 10:30:00'),
       (18, 'off', '2025-01-18 08:30:00', '2025-01-10 10:30:00'),
       (18, 'on', '2025-01-18 09:00:00', '2025-01-10 10:30:00'),
       (18, 'off', '2025-01-18 09:30:00', '2025-01-10 10:30:00'),
       (18, 'on', '2025-01-18 10:00:00', '2025-01-10 10:30:00'),
       (18, 'off', '2025-01-18 10:30:00', '2025-01-10 10:30:00'),

       (19, 'on', '2025-01-19 08:00:00', '2025-01-10 10:30:00'),
       (19, 'off', '2025-01-19 08:30:00', '2025-01-10 10:30:00'),
       (19, 'on', '2025-01-19 09:00:00', '2025-01-10 10:30:00'),
       (19, 'off', '2025-01-19 09:30:00', '2025-01-10 10:30:00'),
       (19, 'on', '2025-01-19 10:00:00', '2025-01-10 10:30:00'),
       (19, 'off', '2025-01-19 10:30:00', '2025-01-10 10:30:00'),

       (20, 'on', '2025-01-20 08:00:00', '2025-01-10 10:30:00'),
       (20, 'off', '2025-01-20 08:30:00', '2025-01-10 10:30:00'),
       (20, 'on', '2025-01-20 09:00:00', '2025-01-10 10:30:00'),
       (20, 'off', '2025-01-20 09:30:00', '2025-01-10 10:30:00'),
       (20, 'on', '2025-01-20 10:00:00', '2025-01-10 10:30:00'),
       (20, 'off', '2025-01-20 10:30:00', '2025-01-10 10:30:00');