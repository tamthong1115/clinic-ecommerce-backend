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

-- Sample appointments
INSERT INTO appointment (
    id, doctor_id, patient_id, clinic_id, appointment_date, start_time, end_time, status, created_at, updated_at
) VALUES
      ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'c9ab5852-50f6-4989-b71a-2b7986fc70fa', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-10', '09:00:00', '09:30:00', 'CONFIRMED', CURRENT_TIMESTAMP, NULL),
      ('11a38b9a-b3da-4b1e-b9f9-c9f7c300fc78', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'c9ab5852-50f6-4989-b71a-2b7986fc70fa', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-11', '14:00:00', '14:30:00', 'SCHEDULED', CURRENT_TIMESTAMP, NULL),
      ('deafc4d2-eaea-4981-9fbd-3e423de7a7d8', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'eb7c3204-dfbe-4eba-a06c-5a8bade70671', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-08', '10:30:00', '11:00:00', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP - INTERVAL '1 day'),
      ('72e2e473-efcb-4a49-b9c3-2dcd9b0a1e38', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'eb7c3204-dfbe-4eba-a06c-5a8bade70671', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-15', '15:00:00', '15:30:00', 'SCHEDULED', CURRENT_TIMESTAMP, NULL),
      ('c5f5b982-3667-4f9d-ac3f-91cce8e8f66f', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'c9ab5852-50f6-4989-b71a-2b7986fc70fa', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-03', '11:00:00', '11:30:00', 'CANCELLED', CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '4 days'),
      ('19c7b550-3f3e-4c9c-8520-db564c0daacd', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'eb7c3204-dfbe-4eba-a06c-5a8bade70671', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-02', '16:30:00', '17:00:00', 'NO_SHOW', CURRENT_TIMESTAMP - INTERVAL '8 days', CURRENT_TIMESTAMP - INTERVAL '3 days'),
      ('d851e5f6-932a-4de1-b5de-83b0f61353ba', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'c9ab5852-50f6-4989-b71a-2b7986fc70fa', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-20', '13:00:00', '13:30:00', 'SCHEDULED', CURRENT_TIMESTAMP, NULL),
      ('e5cc2c24-873d-4f96-a05f-4ab452a8c6b0', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'eb7c3204-dfbe-4eba-a06c-5a8bade70671', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', '2025-05-25', '09:30:00', '10:00:00', 'SCHEDULED', CURRENT_TIMESTAMP, NULL)
    ON CONFLICT (id) DO NOTHING;