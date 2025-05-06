-- First create the specialty table
CREATE TABLE IF NOT EXISTS "specialty" (
                                           id UUID PRIMARY KEY,
                                           name VARCHAR(100) NOT NULL,
                                           description TEXT
);

-- Then create the doctor table that references specialty
CREATE TABLE IF NOT EXISTS "doctor" (
                                        id UUID PRIMARY KEY,
                                        user_id UUID NOT NULL,
                                        first_name VARCHAR(100),
                                        last_name VARCHAR(100),
                                        email VARCHAR(255),
                                        phone VARCHAR(50),
                                        gender VARCHAR(20),
                                        profile_picture VARCHAR(255),
                                        experience_years INT,
                                        license_number VARCHAR(100),
                                        education TEXT,
                                        specialty_id UUID,
                                        CONSTRAINT fk_specialty FOREIGN KEY (specialty_id) REFERENCES "specialty"(id)
);


CREATE TABLE IF NOT EXISTS "doctor_clinic" (
                                               id UUID PRIMARY KEY,
                                               doctor_id UUID NOT NULL,
                                               clinic_id UUID NOT NULL,
                                               CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES "doctor"(id),
                                               CONSTRAINT uk_doctor_clinic UNIQUE (doctor_id, clinic_id)
);

-- Sample specialties
INSERT INTO "specialty" (id, name, description) VALUES
                                                    ('11111111-1111-1111-1111-111111111111', 'Cardiology', 'Heart specialist'),
                                                    ('22222222-2222-2222-2222-222222222222', 'Dermatology', 'Skin specialist')
ON CONFLICT (id) DO NOTHING;

-- Sample doctors
INSERT INTO "doctor" (
    id, user_id, first_name, last_name, email, phone, gender, profile_picture, experience_years, license_number, education, specialty_id
) VALUES
      ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'eb7c3204-dfbe-4eba-a06c-5a8bade70671', 'John', 'Doe', 'doctor@gmail.com', '+1234567890', 'Male', 'https://example.com/profile1.jpg', 10, 'LIC123456', 'MD, Cardiology, Harvard Medical School', '11111111-1111-1111-1111-111111111111'),
      ('b2c3d4e5-f678-90ab-cdef-234567890abc', 'c9ab5852-50f6-4989-b71a-2b7986fc70fa', 'Jane', 'Smith', 'user@gmail.com', '+1987654321', 'Female', 'https://example.com/profile2.jpg', 7, 'LIC654321', 'MD, Dermatology, Stanford University', '22222222-2222-2222-2222-222222222222')
ON CONFLICT (id) DO NOTHING;


INSERT INTO "doctor_clinic" (
    id,
    doctor_id,
    clinic_id
) VALUES
      ('6ac34e79-8d9b-45c3-9b4e-2f63a5198181', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f'),
      ('8d92f9a6-3c5b-47e1-b6d4-9f12aa5e84c2', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'c51b8083-58a3-4db1-98b7-326c9e3e7571'),
      ('7f26e5d1-9a38-4cb1-8d9f-43ea71bc962a', 'b2c3d4e5-f678-90ab-cdef-234567890abc', '7eb0a7de-9658-4dbf-a42f-1804f2b5d734')
ON CONFLICT (id) DO NOTHING;