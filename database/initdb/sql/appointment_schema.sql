

-- Table schema
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


-- Create the appointment table
CREATE TABLE IF NOT EXISTS appointment (
                                           id UUID PRIMARY KEY,
                                           doctor_id UUID NOT NULL,
                                           doctor_first_name TEXT,
                                           doctor_last_name TEXT,
                                           doctor_profile_picture TEXT,
                                           patient_id UUID NOT NULL,
                                           clinic_id UUID NOT NULL,
                                           clinic_name TEXT,
                                           clinic_address TEXT,
                                           service_id UUID NOT NULL,
                                           service_name TEXT,
                                           service_price TEXT,
                                           appointment_date DATE NOT NULL,
                                           start_time TIME NOT NULL,
                                           end_time TIME NOT NULL,
                                           status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'CONFIRMED', 'RESCHEDULED', 'CANCELLED','COMPLETED', 'NO_SHOW')),
                                           reason TEXT,
                                           medical_record_id UUID UNIQUE,
                                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                           CONSTRAINT fk_medical_record
                                               FOREIGN KEY (medical_record_id)
                                                   REFERENCES medical_record(id)
                                                   ON DELETE SET NULL
);



-- Table schema
CREATE TABLE IF NOT EXISTS medical_test (
                                            id UUID PRIMARY KEY,
                                            test_name TEXT NOT NULL,
                                            test_result TEXT NOT NULL,
                                            result_file TEXT,
                                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            appointment_id UUID,
                                            CONSTRAINT fk_appointment
                                                FOREIGN KEY (appointment_id)
                                                    REFERENCES appointment(id)
                                                    ON DELETE CASCADE
);


-- Sample appointments with correct patient_id values
-- Updated sample appointments
-- INSERT INTO appointment (
--     id, doctor_id, doctor_first_name, doctor_last_name, doctor_profile_picture,
--     patient_id, clinic_id, clinic_name, clinic_address,
--     service_id, service_name, service_price,
--     appointment_date, start_time, end_time, status, reason,
--     created_at, updated_at
-- ) VALUES
--       ('08dde847-7e80-4e83-8c01-6b3c9db499ab', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Nguyen', 'Van A', 'profile1.jpg',
--        '27edc3be-4230-4747-a516-2a13fcc59df0', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'Phòng khám Hòa Bình', '123 Lý Thường Kiệt',
--        'e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761', 'Khám sức khỏe tổng quát', '500000',
--        '2025-05-10', '09:00:00', '09:30:00', 'CONFIRMED', 'Đau đầu', CURRENT_TIMESTAMP, NULL),
--
--       ('b82262f6-a3ba-4234-ad76-0aee94a780ea', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'Tran', 'Thi B', 'profile2.jpg',
--        '27edc3be-4230-4747-a516-2a13fcc59df0', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'Phòng khám Hòa Bình', '123 Lý Thường Kiệt',
--        'ba8e6b51-d1ec-4f35-9f58-6c6947fbd26b', 'Tư vấn tiểu đường', '550000',
--        '2025-05-11', '14:00:00', '14:30:00', 'PENDING', NULL, CURRENT_TIMESTAMP, NULL),
--
--       ('7a37aec0-600e-437b-ab35-0d4188a59f5e', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Nguyen', 'Van A', 'profile1.jpg',
--        '27edc3be-4230-4747-a516-2a13fcc59df0', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'Phòng khám Hòa Bình', '123 Lý Thường Kiệt',
--        '5ac9d4d9-1e82-464e-8a6e-705aee4f0c83', 'Kiểm tra tăng huyết áp', '550000',
--        '2025-05-20', '13:00:00', '13:30:00', 'PENDING', NULL, CURRENT_TIMESTAMP, NULL),
--
--       ('0a685e30-f3b1-4255-bbf5-42d8b9cf17df', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'Nguyen', 'Van A', 'profile1.jpg',
--        'af47f44e-8590-4254-9c15-b082f93f3135', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'Phòng khám Hòa Bình', '123 Lý Thường Kiệt',
--        'cfb78b79-ff2e-4891-8b4b-b672f85d160f', 'Tầm soát gan mật', '600000',
--        '2025-05-15', '10:30:00', '11:00:00', 'COMPLETED', 'Khó chịu bụng', CURRENT_TIMESTAMP - INTERVAL '15 days', CURRENT_TIMESTAMP - INTERVAL '15 days'),
--
--       ('1b210831-196c-431f-b078-bbe8c5db3c26', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'Tran', 'Thi B', 'profile2.jpg',
--        'bfe6acd9-4a55-43b9-aec6-52766cf14554', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'Phòng khám Quận 10', '56 Ba Tháng Hai',
--        'e6a54fb8-1fc4-44f3-b503-9f5a96154770', 'Khám tiền hôn nhân', '600000',
--        '2025-05-18', '15:00:00', '15:45:00', 'COMPLETED', NULL, CURRENT_TIMESTAMP - INTERVAL '45 days', CURRENT_TIMESTAMP - INTERVAL '45 days')
-- ON CONFLICT (id) DO NOTHING;
--
--
-- -- Sample data
-- INSERT INTO medical_record (
--     id, patient_id, doctor_id, clinic_id, diagnosis, prescription, notes, created_at, updated_at
-- ) VALUES
--       ('91e3b0c0-ff65-4b33-bb7b-14f3a8b34200', '27edc3be-4230-4747-a516-2a13fcc59df0', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f',
--        'Flu', 'Rest and hydration', 'Follow up in 1 week', CURRENT_TIMESTAMP - INTERVAL '20 days', CURRENT_TIMESTAMP - INTERVAL '20 days'),
--
--       ('3e25a5c3-cf6d-4e5c-b51e-5ec5db6c90b3', 'bfe6acd9-4a55-43b9-aec6-52766cf14554', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'c51b8083-58a3-4db1-98b7-326c9e3e7571',
--        'Diabetes Type II', 'Metformin 500mg', 'Monitor blood sugar weekly', CURRENT_TIMESTAMP - INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '30 days')
-- ON CONFLICT (id) DO NOTHING;
--
--
--
--
-- -- Sample data
-- INSERT INTO medical_test (
--     id, test_name, test_result, result_file, created_at, updated_at, appointment_id
-- ) VALUES
--       ('43d6e5cb-df41-4c0a-a9b0-4b6f9a8b9e11', 'Blood Test', 'Normal', 'blood_test_result_1.pdf', CURRENT_TIMESTAMP - INTERVAL '20 days', CURRENT_TIMESTAMP - INTERVAL '20 days', '08dde847-7e80-4e83-8c01-6b3c9db499ab'),
--
--       ('8aa5424f-c292-4d6e-8ae8-bd3f674f7c61', 'X-Ray', 'Fracture in right arm', 'xray_result_1.pdf', CURRENT_TIMESTAMP - INTERVAL '10 days', CURRENT_TIMESTAMP - INTERVAL '10 days', '0a685e30-f3b1-4255-bbf5-42d8b9cf17df')
-- ON CONFLICT (id) DO NOTHING;

