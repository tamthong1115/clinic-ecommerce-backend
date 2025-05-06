CREATE TABLE IF NOT EXISTS "users" (
                                      id UUID PRIMARY KEY,
                                      email VARCHAR(255) NOT NULL UNIQUE,
                                      email_verified BOOLEAN NOT NULL DEFAULT false,
                                      password VARCHAR(255),
                                      role VARCHAR(50) NOT NULL
);


INSERT INTO "users" (id, email, email_verified, password, role)
VALUES
    ('c9ab5852-50f6-4989-b71a-2b7986fc70fa', 'user@gmail.com',true, '$2a$10$jyLIBHNdBTMZ45R60Fo1v.su5kUP/T5hUCZDSbGMzt9oG2PlQge.q', 'USER'),
    ('eb7c3204-dfbe-4eba-a06c-5a8bade70671', 'doctor@gmail.com',true, '$2a$10$1oP8BRT0m/L4SavCXkVQKOKn..lWHhJ7.Wl.ZZNn.lexG7YQzNFMS', 'DOCTOR'),
    ('3f852db7-8d9c-4ba0-af91-b96bf9ddfcb7', 'clinic-owner@gmail.com',true,'$2a$10$W7fJGx5Ny9QGLpfNNuIjKuBd/7w15iZ4NoCwjFmGMr8KRNjqOmeTK', 'CLINIC_OWNER'),
    ('be2aabf5-3f13-4b81-8ca0-396f83e3e15c', 'admin@gmail.com',true, '$2a$10$DYTm9YZ7HvknFxFtVf.cT.AGs7gLjqqeXJYl1XDF93w1Es/a4vvDO', 'ADMIN')
