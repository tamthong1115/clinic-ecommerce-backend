CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY,
                                     username VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    image_url VARCHAR(255),
    role VARCHAR(50) NOT NULL
    );

INSERT INTO users (id, username, email, password, email_verified, image_url, role) VALUES
                                                                                       ('1975bb3a-9e83-4fbe-ab3e-89d1f62fde17', 'john_doe', 'test@gmail.com', '$2a$10$Nev/K6F/jmTaqeY3S/VvseMYhfWyKR8lU0uZ1.5MBAUlwyjJzRdCO', FALSE, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-tJ9WruDBMHyYM1cY3h-_PCNRgf5OnXZrqw&s', 'USER'),
                                                                                       ('c96e4f1e-c06c-4211-b835-402921e4a5be', 'jane_doe', 'test1@gmail.com', '$2a$10$Gy/RuliRoN/UbPsXdLHkne1yTsxoixHvF8Btw6bDjtvck45Q0YTkW', TRUE, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-tJ9WruDBMHyYM1cY3h-_PCNRgf5OnXZrqw&s', 'ADMIN');