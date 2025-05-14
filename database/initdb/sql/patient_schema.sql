-- Create patient table
CREATE TABLE IF NOT EXISTS patient (
                                       id UUID PRIMARY KEY,
                                       user_id UUID,
                                       first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    date_of_birth DATE,
    gender VARCHAR(20),
    address TEXT
    );

INSERT INTO patient (id, user_id, first_name, last_name, email, phone, date_of_birth, gender, address)
VALUES
    ('46ec95a3-d044-4d8c-bfbf-46dde351200c', '84a9c31b-7e5d-42cf-9b3a-eb57268c1d35', 'Maria', 'Garcia', 'maria.garcia@example.com', '+1122334455', '1993-04-18', 'Female', '789 Elm St, Westville, USA'),
    ('af47f44e-8590-4254-9c15-b082f93f3135', '9325a8c0-60f1-495a-873f-6d251d54aeb5', 'James', 'Wilson', 'james.wilson@example.com', '+1233445566', '1988-11-25', 'Male', '101 Pine Rd, Easttown, USA'),
    ('bfe6acd9-4a55-43b9-aec6-52766cf14554', '101b4bb0-3088-4000-a39a-106b0eab301a', 'Sophia', 'Lee', 'sophia.lee@example.com', '+1344556677', '1995-07-30', 'Female', '202 Maple Ave, Northburg, USA'),
    ('5f83048e-9366-42a6-909c-8471d3745d10', '063ca99e-2fc9-47f0-a114-6eaec2d08603', 'Michael', 'Brown', 'michael.brown@example.com', '+1455667788', '1982-09-12', 'Male', '303 Cedar Ln, Southfield, USA'),
    ('3b95bb9a-25f5-466d-ad22-43c05a0e58b0', '50e253f7-0d6c-4aa0-8cea-89147d5e7a05', 'Olivia', 'Davis', 'olivia.davis@example.com', '+1566778899', '1991-03-05', 'Female', '404 Birch Blvd, Centertown, USA'),
    ('4b2d9606-4388-46b8-8768-650fa6c34186', 'c9ab5852-50f6-4989-b71a-2b7986fc70fa', 'John', 'Smith', 'user@gmail.com', '+1234567890', '1990-05-15', 'Male', '123 Main St, Anytown, USA'),
    ('51006614-e8c5-4655-a716-b20a8c877946', 'eb7c3204-dfbe-4eba-a06c-5a8bade70671', 'Emily', 'Johnson', 'doctor@gmail.com', '+1987654321', '1985-08-20', 'Female', '456 Oak Ave, Somecity, USA')
ON CONFLICT (id) DO NOTHING;


CREATE TABLE IF NOT EXISTS medical_record (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    doctor_id UUID NOT NULL,
    clinic_id UUID NOT NULL,
    diagnosis TEXT NOT NULL,
    prescription TEXT NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- Sample medical records
INSERT INTO medical_record (id, patient_id, doctor_id, clinic_id, diagnosis, prescription, notes, created_at, updated_at)
VALUES
    ('d9230850-2481-42fc-8c03-fa3c16e4599a', '46ec95a3-d044-4d8c-bfbf-46dde351200c', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'Seasonal Allergies', 'Cetirizine 10mg daily', 'Patient reports improved symptoms', CURRENT_TIMESTAMP - INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '30 days'),
    ('fda361a7-de30-4af5-a01b-6ee2e7b4127f', 'af47f44e-8590-4254-9c15-b082f93f3135', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'Mild Hypertension', 'Lisinopril 5mg daily', 'Follow-up appointment in 3 months', CURRENT_TIMESTAMP - INTERVAL '60 days', CURRENT_TIMESTAMP - INTERVAL '60 days'),
    ('c485d9ec-1aad-4062-be08-525479d2df75', '51006614-e8c5-4655-a716-b20a8c877946', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'Acute Bronchitis', 'Azithromycin 500mg for 5 days', 'Rest and increased fluid intake recommended', CURRENT_TIMESTAMP - INTERVAL '20 days', CURRENT_TIMESTAMP - INTERVAL '20 days')
ON CONFLICT (id) DO NOTHING;

-- Create medical_test table to match the entity
CREATE TABLE IF NOT EXISTS medical_test (
                                            id UUID PRIMARY KEY,
                                            appointment_id UUID NOT NULL,
                                            test_name VARCHAR(255) NOT NULL,
    test_result TEXT NOT NULL,
    result_file TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- Sample medical tests
INSERT INTO medical_test (id, appointment_id, test_name, test_result, result_file, created_at, updated_at)
VALUES
    ('88b61ca4-d18a-46d8-a896-699c22b15e95', '08dde847-7e80-4e83-8c01-6b3c9db499ab', 'Complete Blood Count', 'Normal range', 'https://example.com/results/cbc.pdf', CURRENT_TIMESTAMP - INTERVAL '15 days', CURRENT_TIMESTAMP - INTERVAL '15 days'),
    ('674f04d3-4023-4bb6-9687-1c19e5372ae8', 'b82262f6-a3ba-4234-ad76-0aee94a780ea', 'Lipid Panel', 'Slightly elevated LDL', 'https://example.com/results/lipid.pdf', CURRENT_TIMESTAMP - INTERVAL '45 days', CURRENT_TIMESTAMP - INTERVAL '45 days'),
    ('0a46b50e-41e1-4cd0-b8b4-1a7c46e6c77c', '7a37aec0-600e-437b-ab35-0d4188a59f5e', 'Chest X-Ray', 'Clear lung fields', 'https://example.com/results/xray.pdf', CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '5 days')
ON CONFLICT (id) DO NOTHING;