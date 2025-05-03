INSERT INTO teams (name, headquarters)
VALUES 
('Aqua Kraków', 'Kraków'),
('Wave Masters', 'Warszawa'),
('Baltic Sharks', 'Gdańsk'),
('Mountain Swimmers', 'Zakopane'),
('SpeedStream', 'Poznań');
INSERT INTO competitors (first_name, second_name, last_name, date_of_birth, gender, nationality, team_id)
VALUES 
('Anna', 'Marie', 'Smith', '2003-04-12', 'FEMALE', 'Poland', 1),
('Peter', NULL, 'Johnson', '2000-07-25', 'MALE', 'Poland', 2),
('Mary', NULL, 'Williams', '2002-01-30', 'FEMALE', 'Poland', 3),
('Thomas', 'John', 'Brown', '2001-10-10', 'MALE', 'Poland', 4),
('Catherine', NULL, 'Jones', '2004-06-18', 'FEMALE', 'Poland', 5);
INSERT INTO coaches (first_name, second_name, last_name, date_of_birth, gender, nationality, team_id)
VALUES 
('John', 'Michael', 'Doe', '1980-06-15', 'MALE', 'USA', 1),
('Sarah', 'Ann', 'Williams', '1975-11-22', 'FEMALE', 'UK', 2),
('James', NULL, 'Smith', '1985-03-10', 'MALE', 'Canada', 3),
('Rebecca', 'Lee', 'Johnson', '1990-05-05', 'FEMALE', 'Australia', 4),
('David', NULL, 'Miller', '1982-12-30', 'MALE', 'Germany', 5);
INSERT INTO locations (name, address, pool_length, capacity)
VALUES 
('Aqua Sports Center', '123 Swim Lane, Kraków, Poland', 50, 200),
('Oceanic Arena', '456 Water Ave, Gdańsk, Poland', 25, 150),
('Mountain Splash Complex', '789 Hilltop Rd, Zakopane, Poland', 50, 100),
('City Swim Hall', '101 Central Blvd, Warszawa, Poland', 25, 300),
('Baltic Wave Pool', '202 Seaside St, Sopot, Poland', 50, 250);
INSERT INTO competitions (name, start_date, end_date, description, location_id)
VALUES 
('Winter Swim Cup 2025', '2025-01-15 10:00:00', '2025-01-15 17:00:00', 'Cold water challenge in the heart of winter.', 2),
('Polish National Championships', '2025-06-10 09:00:00', '2025-06-12 18:00:00', 'Top swimmers from across the country compete.', 1),
('Youth Swimming Gala', '2025-05-05 08:00:00', '2025-05-05 16:00:00', 'Event dedicated to young and emerging talent.', 3),
('Open Water Trials', '2025-07-01 09:30:00', '2025-07-01 15:30:00', 'Simulation for open water conditions in a large pool.', 5),
('City Championship Warsaw', '2025-09-20 10:00:00', '2025-09-21 18:00:00', 'Annual city-level swimming competition.', 4);
INSERT INTO races (style, distance, race_date, competition_id)
VALUES 
('FREESTYLE', 100, '2025-06-10 10:00:00', 1),
('BACKSTROKE', 200, '2025-06-10 11:00:00', 1),
('BREASTSTROKE', 100, '2025-06-10 12:00:00', 2),
('BUTTERFLY', 50, '2025-07-01 09:00:00', 4),
('FREESTYLE', 400, '2025-09-20 14:00:00', 5);
INSERT INTO results (place, result_time, competitor_id, race_id)
VALUES 
(1, '00:53:21', 1, 1),
(2, '00:54:45', 2, 1),
(3, '00:55:12', 3, 1),

(1, '01:45:00', 2, 2),
(2, '01:47:10', 1, 2),

(1, '00:59:33', 3, 3),

(1, '00:25:48', 1, 4),
(2, '00:26:10', 2, 4),

(1, '04:23:59', 2, 5),
(2, '04:30:12', 3, 5);
