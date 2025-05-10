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
    ('84a9c31b-7e5d-42cf-9b3a-eb57268c1d35', 'maria.garcia@example.com', true, '$2a$10$jyLIBHNdBTMZ45R60Fo1v.su5kUP/T5hUCZDSbGMzt9oG2PlQge.q', 'USER'),
    ('9325a8c0-60f1-495a-873f-6d251d54aeb5', 'james.wilson@example.com', true, '$2a$10$jyLIBHNdBTMZ45R60Fo1v.su5kUP/T5hUCZDSbGMzt9oG2PlQge.q', 'USER'),
    ('101b4bb0-3088-4000-a39a-106b0eab301a', 'sophia.lee@example.com', true, '$2a$10$jyLIBHNdBTMZ45R60Fo1v.su5kUP/T5hUCZDSbGMzt9oG2PlQge.q', 'USER'),
    ('063ca99e-2fc9-47f0-a114-6eaec2d08603', 'michael.brown@example.com', true, '$2a$10$jyLIBHNdBTMZ45R60Fo1v.su5kUP/T5hUCZDSbGMzt9oG2PlQge.q', 'USER'),
    ('50e253f7-0d6c-4aa0-8cea-89147d5e7a05', 'olivia.davis@example.com', true, '$2a$10$jyLIBHNdBTMZ45R60Fo1v.su5kUP/T5hUCZDSbGMzt9oG2PlQge.q', 'USER'),
    ('eb7c3204-dfbe-4eba-a06c-5a8bade70671', 'doctor@gmail.com',true, '$2a$10$1oP8BRT0m/L4SavCXkVQKOKn..lWHhJ7.Wl.ZZNn.lexG7YQzNFMS', 'DOCTOR'),
    ('3f852db7-8d9c-4ba0-af91-b96bf9ddfcb7', 'clinic-owner@gmail.com',true,'$2a$10$W7fJGx5Ny9QGLpfNNuIjKuBd/7w15iZ4NoCwjFmGMr8KRNjqOmeTK', 'CLINIC_OWNER'),
    ('12d9d732-f0a6-40fa-8b81-72c181e372c8', 'clinic-owner1@gmail.com',true,'$2a$10$W7fJGx5Ny9QGLpfNNuIjKuBd/7w15iZ4NoCwjFmGMr8KRNjqOmeTK', 'CLINIC_OWNER'),
    ('f31cd6b3-ffa0-4a09-9a79-87147ca5d90d', 'clinic-owner2@gmail.com',true,'$2a$10$W7fJGx5Ny9QGLpfNNuIjKuBd/7w15iZ4NoCwjFmGMr8KRNjqOmeTK', 'CLINIC_OWNER'),
    ('be2aabf5-3f13-4b81-8ca0-396f83e3e15c', 'admin@gmail.com',true, '$2a$10$DYTm9YZ7HvknFxFtVf.cT.AGs7gLjqqeXJYl1XDF93w1Es/a4vvDO', 'ADMIN')
ON CONFLICT (id) DO NOTHING;
