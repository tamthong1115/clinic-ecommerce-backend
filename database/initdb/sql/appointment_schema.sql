-- Create the appointment table
CREATE TABLE IF NOT EXISTS appointment (
                                           id UUID PRIMARY KEY,
                                           doctor_id UUID NOT NULL,
                                           patient_id UUID NOT NULL,
                                           clinic_id UUID NOT NULL,
                                           appointment_date DATE NOT NULL,
                                           start_time TIME NOT NULL,
                                           end_time TIME NOT NULL,
                                           status VARCHAR(20) NOT NULL CHECK (status IN ('SCHEDULED', 'CONFIRMED', 'COMPLETED', 'CANCELLED', 'NO_SHOW')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Sample appointments with correct patient_id values
INSERT INTO appointment (
    id, doctor_id, patient_id, clinic_id, appointment_date, start_time, end_time, status, created_at, updated_at
) VALUES
      ('08dde847-7e80-4e83-8c01-6b3c9db499ab', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '27edc3be-4230-4747-a516-2a13fcc59df0', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-10', '09:00:00', '09:30:00', 'CONFIRMED', CURRENT_TIMESTAMP, NULL),
      ('b82262f6-a3ba-4234-ad76-0aee94a780ea', 'b2c3d4e5-f678-90ab-cdef-234567890abc', '27edc3be-4230-4747-a516-2a13fcc59df0', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-11', '14:00:00', '14:30:00', 'SCHEDULED', CURRENT_TIMESTAMP, NULL),
      ('7a37aec0-600e-437b-ab35-0d4188a59f5e', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '27edc3be-4230-4747-a516-2a13fcc59df0', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-20', '13:00:00', '13:30:00', 'SCHEDULED', CURRENT_TIMESTAMP, NULL),
      -- Additional appointments for diverse patient data
      ('0a685e30-f3b1-4255-bbf5-42d8b9cf17df', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'af47f44e-8590-4254-9c15-b082f93f3135', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-15', '10:30:00', '11:00:00', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '15 days', CURRENT_TIMESTAMP - INTERVAL '15 days'),
      ('1b210831-196c-431f-b078-bbe8c5db3c26', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'bfe6acd9-4a55-43b9-aec6-52766cf14554', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', '2025-05-18', '15:00:00', '15:45:00', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '45 days', CURRENT_TIMESTAMP - INTERVAL '45 days'),
      ('0d4c9ccd-fcff-4ea3-9ea2-f2311d4f9d86', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'bfe6acd9-4a55-43b9-aec6-52766cf14554', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-22', '09:30:00', '10:00:00', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '5 days'),
      ('42483caa-8c3b-40db-a485-eaa973a5ed67', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', '5f83048e-9366-42a6-909c-8471d3745d10', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-25', '11:30:00', '12:00:00', 'SCHEDULED', CURRENT_TIMESTAMP, NULL),
      ('1eac42d4-c02b-45de-84e7-3d5f0d03006f', 'b2c3d4e5-f678-90ab-cdef-234567890abc', '3b95bb9a-25f5-466d-ad22-43c05a0e58b0', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', '2025-05-27', '16:00:00', '16:45:00', 'SCHEDULED', CURRENT_TIMESTAMP, NULL)
ON CONFLICT (id) DO NOTHING;