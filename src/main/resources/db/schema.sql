-- Users table with all fields from User entity
CREATE TABLE IF NOT EXISTS users (
    user_id UUID PRIMARY KEY,
    user_name VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    gender VARCHAR(20),
    goal VARCHAR(255),
    age INTEGER,
    height DOUBLE PRECISION,
    weight DOUBLE PRECISION,
    physical_activity_level INTEGER
);

-- Workouts table
CREATE TABLE IF NOT EXISTS workouts (
    id UUID PRIMARY KEY,
    date DATE NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- Workout Exercises table
CREATE TABLE IF NOT EXISTS workout_exercises (
    id UUID PRIMARY KEY,
    workout_id UUID NOT NULL,
    title VARCHAR(255),
    reps INTEGER,
    sets INTEGER,
    weight DOUBLE PRECISION,
    FOREIGN KEY (workout_id) REFERENCES workouts (id)
);

-- Exercises table
CREATE TABLE IF NOT EXISTS exercises (
    id UUID PRIMARY KEY,
    image_src VARCHAR(255),
    title VARCHAR(255),
    description VARCHAR(1000),
    type VARCHAR(100),
    difficulty VARCHAR(50),
    date_added VARCHAR(50)
);

-- Exercise Targets (many-to-many relationship)
--CREATE TABLE IF NOT EXISTS exercise_targets (
--    exercise_id UUID NOT NULL,
--    target VARCHAR(100) NOT NULL,
--    FOREIGN KEY (exercise_id) REFERENCES exercises (id)
--);

-- Exercise Equipment (many-to-many relationship)
--CREATE TABLE IF NOT EXISTS exercise_equipment (
--    exercise_id UUID NOT NULL,
--    equipment VARCHAR(100) NOT NULL,
--    FOREIGN KEY (exercise_id) REFERENCES exercises (id)
--);

-- Exercise Media Links (many-to-many relationship)
--CREATE TABLE IF NOT EXISTS exercise_media_links (
--    exercise_id UUID NOT NULL,
--    media_link VARCHAR(255) NOT NULL,
--    FOREIGN KEY (exercise_id) REFERENCES exercises (id)
--);

-- Exercise Tags (many-to-many relationship)
--CREATE TABLE IF NOT EXISTS exercise_tags (
--    exercise_id UUID NOT NULL,
--    tag VARCHAR(100) NOT NULL,
--    FOREIGN KEY (exercise_id) REFERENCES exercises (id)
--);

-- User roles table (from your original schema)
--CREATE TABLE IF NOT EXISTS user_roles (
--    user_id UUID NOT NULL,
--    role VARCHAR(20) NOT NULL,
--    FOREIGN KEY (user_id) REFERENCES users (user_id)
--);