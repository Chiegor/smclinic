DELETE FROM coworking_spaces;
DELETE FROM rooms;
DELETE FROM bookings;

INSERT INTO coworking_spaces (id, name, address, created_at, updated_at, version)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'WeWork', 'Москва, ул. Примерная, д. 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('3e1afeb4-44d3-11ee-be56-0242ac120002', 'TheyWork', 'Санкт-Петербург, ул. Тестовая, д. 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO rooms (id, room_name, space_id, seats, created_at, updated_at, version)
VALUES
    ('a1b2c3d4-e29b-41d4-a716-446655440001', 'Комната 1', '550e8400-e29b-41d4-a716-446655440000', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('a1b2c3d4-e29b-41d4-a716-446655440002', 'Комната 2', '550e8400-e29b-41d4-a716-446655440000', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('a1b2c3d4-e29b-41d4-a716-446655440003', 'Комната 3', '3e1afeb4-44d3-11ee-be56-0242ac120002', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO bookings (id, room_id, start_time, end_time, created_at)
VALUES
    ('b1c2d3e4-e29b-41d4-a716-446655440004', 'a1b2c3d4-e29b-41d4-a716-446655440001', 2025-02-10T17:00:00, 2025-02-10T18:00:00, CURRENT_TIMESTAMP),
    ('b1c2d3e4-e29b-41d4-a716-446655440005', 'a1b2c3d4-e29b-41d4-a716-446655440002', 2025-02-11T17:00:00, 2025-02-11T18:00:00, CURRENT_TIMESTAMP),
    ('b1c2d3e4-e29b-41d4-a716-446655440006', 'a1b2c3d4-e29b-41d4-a716-446655440003', 2025-02-12T17:00:00, 2025-02-12T18:00:00, CURRENT_TIMESTAMP);