-- 아래는 예시입니다. NOW() 말고 직접적인 TIMESTAMP 값을 사용해 주세요.
INSERT INTO vehicle_types (vehicle_types_name, created_at, updated_at, deleted_at)
VALUES
    ('SUV', NOW(), NOW(), NULL),
    ('Truck', NOW(), NOW(), NULL),
    ('Convertible', NOW(), NOW(), NULL);