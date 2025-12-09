-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL,
    email    VARCHAR(100) NOT NULL
);

-- Создание таблицы фильмов
CREATE TABLE IF NOT EXISTS movies (
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(200) NOT NULL,
    release_date DATE,
    genre        VARCHAR(50),
    description  VARCHAR(2000),
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    likes        INTEGER NOT NULL DEFAULT 0,
    user_id      BIGINT REFERENCES users(id) ON DELETE SET NULL
);

-- Пример начальных данных
INSERT INTO users (username, email) VALUES
('alice', 'alice@example.com'),
('bob',   'bob@example.com')
ON CONFLICT DO NOTHING;
