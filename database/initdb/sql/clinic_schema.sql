-- Clinic Owner table definition
CREATE TABLE IF NOT EXISTS clinic_owner (
    id UUID PRIMARY KEY,
    address VARCHAR(255),
    city VARCHAR(255),
    date_of_birth DATE,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    license_number VARCHAR(255),
    phone_number VARCHAR(20) NOT NULL,
    postal_code VARCHAR(20),
    profile_image_url TEXT,
    state VARCHAR(255),
    user_id UUID
);

-- Clinic table definition
CREATE TABLE IF NOT EXISTS clinic (
    id UUID PRIMARY KEY,
    address TEXT NOT NULL,
    name TEXT NOT NULL,
    phone TEXT NOT NULL,
    description TEXT,
    email TEXT NOT NULL,
    image TEXT,
    status VARCHAR(20) NOT NULL CHECK (status IN ('OPEN', 'CLOSED')),
    owner_id UUID NOT NULL,
    CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES clinic_owner(id) ON DELETE CASCADE
);

-- Speciality table definition
CREATE TABLE IF NOT EXISTS speciality (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_url TEXT NOT NULL
);

-- Service table definition
CREATE TABLE IF NOT EXISTS service (
    service_id UUID PRIMARY KEY,
    service_name TEXT NOT NULL,
    speciality_id UUID NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    duration TIME NOT NULL,
    requires_prescription BOOLEAN NOT NULL,
    active BOOLEAN NOT NULL,
    CONSTRAINT fk_speciality FOREIGN KEY (speciality_id) REFERENCES speciality(id) ON DELETE CASCADE
);

-- Clinic Service association table
CREATE TABLE IF NOT EXISTS clinic_service (
    clinic_id UUID NOT NULL,
    service_id UUID NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    PRIMARY KEY (clinic_id, service_id),
    CONSTRAINT fk_clinic FOREIGN KEY (clinic_id) REFERENCES clinic(id) ON DELETE CASCADE,
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES service(service_id) ON DELETE CASCADE
);

-- Special requirements for services table
CREATE TABLE IF NOT EXISTS special_requirement (
    id UUID PRIMARY KEY,
    requirement TEXT NOT NULL,
    service_id UUID NOT NULL,
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES service(service_id) ON DELETE CASCADE
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
    status VARCHAR(20),
    clinic_id UUID,
    CONSTRAINT fk_clinic FOREIGN KEY (clinic_id) REFERENCES clinic(id)
);


CREATE TABLE IF NOT EXISTS doctor_speciality (
    doctor_id UUID NOT NULL,
    speciality_id UUID NOT NULL,
    PRIMARY KEY (doctor_id, speciality_id),
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES "doctor"(id) ON DELETE CASCADE,
    CONSTRAINT fk_speciality FOREIGN KEY (speciality_id) REFERENCES "speciality"(id) ON DELETE CASCADE
);

INSERT INTO clinic_owner (id,
                          address,
                          city,
                          date_of_birth,
                          email,
                          first_name,
                          last_name,
                          license_number,
                          phone_number,
                          postal_code,
                          profile_image_url,
                          state,
                          user_id)
VALUES ('affd0938-a77d-4404-8461-788e64ad1dab',
        NULL,
        NULL,
        '1999-02-13',
        '2251120349@ut.edu.vn',
        'Hoàng',
        'Lê',
        NULL,
        '0399733849',
        NULL,
        NULL,
        NULL,
        '3f852db7-8d9c-4ba0-af91-b96bf9ddfcb7'),
       ('b7893c41-c5e2-48f1-9454-46235b19d48c',
        '456 Đường Lê Lợi, Quận 3, TP.HCM',
        'Hồ Chí Minh',
        '1985-07-22',
        'nguyenthi@mail.com',
        'Thi',
        'Nguyễn',
        'CLO-001234',
        '0912345678',
        '70000',
        'https://example.com/profiles/nguyenthi.jpg',
        'Hồ Chí Minh',
        '12d9d732-f0a6-40fa-8b81-72c181e372c8'),

       ('4d8c03d1-8735-46b1-a88f-4a7ac1c0a238',
        '789 Đường Trần Hưng Đạo, Quận 5, TP.HCM',
        'Hồ Chí Minh',
        '1990-03-15',
        'tranvan@mail.com',
        'Văn',
        'Trần',
        'CLO-005678',
        '0909876543',
        '70000',
        'https://example.com/profiles/tranvan.jpg',
        'Hồ Chí Minh',
        'f31cd6b3-ffa0-4a09-9a79-87147ca5d90d')
ON CONFLICT (id) DO NOTHING;

INSERT INTO clinic (id,
                           address,
                           name,
                           phone,
                           description,
                           email,
                           image,
                           status,
                           owner_id)
VALUES ('a774500c-6dd1-4378-a5f9-ac91458a9b6f',
        '123 Đường Nguyễn Huệ, Quận 1, TP.HCM',
        'Phòng Khám Lê Hoàng',
        '0399733849',
        'Phòng khám đa khoa chất lượng cao do bác sĩ Lê Hoàng quản lý.',
        '2251120349@ut.edu.vn',
        'https://example.com/images/clinic.png',
        'CLOSED',
        'affd0938-a77d-4404-8461-788e64ad1dab'),
       ('c51b8083-58a3-4db1-98b7-326c9e3e7571',
        '456 Đường Lê Lợi, Quận 3, TP.HCM',
        'Phòng Khám Đa Khoa Nguyễn Thi',
        '0912345678',
        'Phòng khám chuyên khoa tai mũi họng với trang thiết bị hiện đại và đội ngũ y bác sĩ giàu kinh nghiệm.',
        'nguyenthi@mail.com',
        'https://example.com/images/clinic_nguyen_thi.png',
        'OPEN',
        'b7893c41-c5e2-48f1-9454-46235b19d48c'),

       ('7eb0a7de-9658-4dbf-a42f-1804f2b5d734',
        '789 Đường Trần Hưng Đạo, Quận 5, TP.HCM',
        'Phòng Khám Nhi Khoa Trần Văn',
        '0909876543',
        'Phòng khám chuyên khoa nhi cung cấp dịch vụ chăm sóc sức khỏe tổng thể cho trẻ em từ sơ sinh đến 15 tuổi.',
        'tranvan@mail.com',
        'https://example.com/images/clinic_tran_van.png',
        'OPEN',
        '4d8c03d1-8735-46b1-a88f-4a7ac1c0a238')
ON CONFLICT (id) DO NOTHING;

INSERT INTO speciality (id,
                               name,
                               description,
                               image_url)
VALUES ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Nội tổng quát', 'Khám và điều trị các bệnh nội khoa.',
        'https://example.com/images/noi-tong-quat.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Ngoại tổng quát', 'Phẫu thuật và điều trị ngoại khoa cơ bản.',
        'https://example.com/images/ngoai-tong-quat.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b03', 'Nhi khoa', 'Chăm sóc sức khỏe trẻ em.',
        'https://example.com/images/nhi-khoa.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Sản phụ khoa', 'Chăm sóc sức khỏe phụ nữ và thai kỳ.',
        'https://example.com/images/san-phu-khoa.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b05', 'Tai Mũi Họng', 'Khám và điều trị các bệnh về tai, mũi, họng.',
        'https://example.com/images/tai-mui-hong.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Da liễu', 'Chăm sóc và điều trị các bệnh ngoài da.',
        'https://example.com/images/da-lieu.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b07', 'Tim mạch', 'Khám và điều trị các bệnh về tim.',
        'https://example.com/images/tim-mach.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b08', 'Tiêu hóa', 'Chẩn đoán và điều trị các bệnh về tiêu hóa.',
        'https://example.com/images/tieu-hoa.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b09', 'Thần kinh', 'Khám và điều trị bệnh lý thần kinh.',
        'https://example.com/images/than-kinh.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b10', 'Chấn thương chỉnh hình', 'Xử lý các vấn đề xương khớp, gãy xương.',
        'https://example.com/images/chinh-hinh.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b11', 'Mắt', 'Khám và điều trị các bệnh về mắt.',
        'https://example.com/images/mat.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b12', 'Răng Hàm Mặt', 'Chăm sóc sức khỏe răng miệng.',
        'https://example.com/images/rang-ham-mat.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b13', 'Nội tiết', 'Khám và điều trị rối loạn nội tiết.',
        'https://example.com/images/noi-tiet.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b14', 'Thận - Tiết niệu', 'Chẩn đoán và điều trị bệnh về thận, tiết niệu.',
        'https://example.com/images/than-tiet-nieu.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b15', 'Ung bướu', 'Chẩn đoán và điều trị ung thư.',
        'https://example.com/images/ung-buou.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b16', 'Hô hấp', 'Điều trị các bệnh về đường hô hấp.',
        'https://example.com/images/ho-hap.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b17', 'Huyết học', 'Chẩn đoán và điều trị bệnh về máu.',
        'https://example.com/images/huyet-hoc.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b18', 'Truyền nhiễm', 'Khám và điều trị bệnh truyền nhiễm.',
        'https://example.com/images/truyen-nhiem.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b19', 'Y học cổ truyền', 'Điều trị theo phương pháp Đông y.',
        'https://example.com/images/y-hoc-co-truyen.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b20', 'Tâm thần', 'Khám và điều trị các rối loạn tâm lý.',
        'https://example.com/images/tam-than.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b21', 'Vật lý trị liệu', 'Phục hồi chức năng, điều trị không dùng thuốc.',
        'https://example.com/images/vat-ly-tri-lieu.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b22', 'Phục hồi chức năng', 'Hỗ trợ bệnh nhân sau điều trị, phẫu thuật.',
        'https://example.com/images/phuc-hoi-chuc-nang.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b23', 'Y học hạt nhân', 'Ứng dụng hạt nhân trong chẩn đoán và điều trị.',
        'https://example.com/images/y-hoc-hat-nhan.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b24', 'Nội soi', 'Chẩn đoán, điều trị bằng phương pháp nội soi.',
        'https://example.com/images/noi-soi.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b25', 'Siêu âm', 'Chẩn đoán hình ảnh bằng siêu âm.',
        'https://example.com/images/sieu-am.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b26', 'Xét nghiệm', 'Phân tích mẫu bệnh phẩm để chẩn đoán.',
        'https://example.com/images/xet-nghiem.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Chẩn đoán hình ảnh', 'CT, MRI, X-quang, chụp cộng hưởng từ.',
        'https://example.com/images/chan-doan-hinh-anh.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b28', 'Bệnh nghề nghiệp', 'Điều trị các bệnh do môi trường làm việc.',
        'https://example.com/images/benh-nghe-nghiep.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b29', 'Khám sức khỏe tổng quát', 'Tổng kiểm tra định kỳ toàn diện.',
        'https://example.com/images/kham-suc-khoe.jpg'),
       ('f5c101d2-9f2a-46c0-b541-b1c09c1a1b30', 'Tư vấn di truyền', 'Phân tích và tư vấn bệnh lý di truyền.',
        'https://example.com/images/tu-van-di-truyen.jpg') ON CONFLICT (id) DO NOTHING;



INSERT INTO service (service_id,
                            active,
                            speciality_id,
                            description,
                            duration,
                            price,
                            requires_prescription,
                            service_name)
VALUES
-- Nội tổng quát - f5c101d2-9f2a-46c0-b541-b1c09c1a1b01
('e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01',
 'Khám sức khoẻ định kỳ tổng quát cho người lớn.', '00:30:00', 500000, FALSE, 'Khám sức khỏe tổng quát'),
('ba8e6b51-d1ec-4f35-9f58-6c6947fbd26b', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Tư vấn kiểm tra tiểu đường.',
 '00:30:00', 550000, FALSE, 'Tư vấn tiểu đường'),
('5ac9d4d9-1e82-464e-8a6e-705aee4f0c83', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Khám tăng huyết áp.',
 '00:30:00', 550000, FALSE, 'Kiểm tra tăng huyết áp'),
('cfb78b79-ff2e-4891-8b4b-b672f85d160f', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Tầm soát bệnh lý gan mật.',
 '00:40:00', 600000, FALSE, 'Tầm soát gan mật'),
('e6a54fb8-1fc4-44f3-b503-9f5a96154770', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Khám sức khỏe tiền hôn nhân.',
 '00:40:00', 600000, FALSE, 'Khám tiền hôn nhân'),

-- Nhi khoa - f5c101d2-9f2a-46c0-b541-b1c09c1a1b03
('68c73b74-d5db-4ff3-8e09-5e930b36086d', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b03', 'Khám bệnh tổng quát trẻ em.',
 '00:25:00', 400000, FALSE, 'Khám tổng quát cho trẻ'),
('8a7d6f87-099e-4971-8e06-556522d9f6ee', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b03', 'Tư vấn dinh dưỡng cho bé.',
 '00:30:00', 450000, FALSE, 'Tư vấn dinh dưỡng nhi'),
('16c78249-5084-4d90-874e-d2fcbeb99795', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b03', 'Khám hô hấp trẻ em.',
 '00:30:00', 450000, FALSE, 'Khám hô hấp nhi'),

-- Ngoại tổng quát - f5c101d2-9f2a-46c0-b541-b1c09c1a1b02
('0ad58e00-9e3d-41c4-8a7d-3f417b06c21f', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Tư vấn chấn thương nhẹ.',
 '00:30:00', 500000, FALSE, 'Tư vấn chấn thương cơ bản'),
('c05b57e6-c1b5-4f62-84c3-5611d021a062', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Khám chấn thương thể thao.',
 '00:40:00', 700000, FALSE, 'Khám chấn thương vận động'),
('cae7281f-9420-4b4e-9ad4-43765df2030c', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Đánh giá phục hồi chức năng.',
 '00:40:00', 650000, FALSE, 'Tư vấn phục hồi chức năng'),
('fa23de2e-9622-4747-997b-53fcfa6527a3', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Tư vấn hậu phẫu.', '00:30:00',
 500000, FALSE, 'Tư vấn sau phẫu thuật'),

-- Sản phụ khoa - f5c101d2-9f2a-46c0-b541-b1c09c1a1b04
('fb5114a1-f800-4e14-b82a-8a9b9f7c86d9', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Khám thai định kỳ.', '00:20:00',
 400000, FALSE, 'Khám thai'),
('939ee6fc-2d48-4c07-83c8-7166be4223a7', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Tư vấn tiền sản.', '00:30:00',
 450000, FALSE, 'Tư vấn tiền sản'),
('bb73a0e0-3d77-4b9a-a8d1-f00f1188d927', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Khám phụ khoa định kỳ.',
 '00:20:00', 400000, FALSE, 'Khám phụ khoa'),
('50b72e83-303d-4bc0-8e9f-28b40fa3ba36', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Siêu âm thai 2D.', '00:20:00',
 500000, FALSE, 'Siêu âm thai'),
('7f247ec6-36fd-4f7d-b69c-3eaa0bdb0c7b', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Tầm soát ung thư cổ tử cung.',
 '00:25:00', 600000, FALSE, 'Tầm soát cổ tử cung'),

-- Tai Mũi Họng - f5c101d2-9f2a-46c0-b541-b1c09c1a1b05
('47b041b2-7690-4662-9962-003207f4db06', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b05', 'Khám tai mũi họng tổng quát.',
 '00:20:00', 350000, FALSE, 'Khám tai mũi họng'),
('fdbcb36e-5d78-4a14-9a0e-0f49d295d780', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b05', 'Nội soi tai mũi họng.',
 '00:25:00', 500000, TRUE, 'Nội soi tai mũi họng'),
('b0dfb7ad-2ac0-41d3-9d62-3c39f098d1aa', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b05', 'Đo thính lực.', '00:20:00',
 400000, FALSE, 'Đo thính lực'),

-- Da liễu - f5c101d2-9f2a-46c0-b541-b1c09c1a1b06
('6a6c5fd8-f4f7-4c1d-91f2-31f9b3fcbb38', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Tư vấn điều trị mụn.',
 '00:45:00', 700000, FALSE, 'Điều trị mụn'),
('f9db6d4a-0d77-4d45-b7ee-8027032bdf63', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Soi da chuyên sâu.', '00:30:00',
 600000, FALSE, 'Soi da'),
('e41b50a5-c167-4c46-8aa5-227b8efeb14b', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Chăm sóc da cơ bản.',
 '01:00:00', 800000, FALSE, 'Chăm sóc da mặt'),
('b7e579b7-8b02-4685-813e-8c4e9d5c6a19', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Laser điều trị sẹo.',
 '00:45:00', 1500000, TRUE, 'Laser trị sẹo'),

-- Chẩn đoán hình ảnh - f5c101d2-9f2a-46c0-b541-b1c09c1a1b27
('070d2233-c35d-4e7d-80a9-f57bcdab3a15', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Siêu âm ổ bụng tổng quát.',
 '00:20:00', 500000, FALSE, 'Siêu âm ổ bụng'),
('1d8c9bd6-1797-4309-8f4f-bc8a3345fd00', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'X-quang phổi.', '00:15:00',
 400000, FALSE, 'X-quang phổi'),
('5a14f62a-7b3e-4fc7-80f3-fb23be71610a', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Chụp MRI não.', '00:45:00',
 2500000, TRUE, 'MRI não'),
('16b446b6-72a4-4fa3-88de-d88e52ef2529', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'CT-scan ngực.', '00:30:00',
 1800000, TRUE, 'CT ngực'),
('b37bde4d-bf2f-4b14-8181-72923ec0ff03', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Nội soi dạ dày.', '01:00:00',
 1500000, TRUE, 'Nội soi tiêu hóa'),
('35453ac7-4d1d-44fa-90a6-4b18c676ec34', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Nội soi đại tràng.', '00:45:00',
 2000000, TRUE, 'Nội soi đại tràng') ON CONFLICT (service_id) DO NOTHING;



INSERT INTO clinic_service (clinic_id, service_id, status)
VALUES ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'ba8e6b51-d1ec-4f35-9f58-6c6947fbd26b', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '5ac9d4d9-1e82-464e-8a6e-705aee4f0c83', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'cfb78b79-ff2e-4891-8b4b-b672f85d160f', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'e6a54fb8-1fc4-44f3-b503-9f5a96154770', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '68c73b74-d5db-4ff3-8e09-5e930b36086d', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '8a7d6f87-099e-4971-8e06-556522d9f6ee', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '16c78249-5084-4d90-874e-d2fcbeb99795', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '0ad58e00-9e3d-41c4-8a7d-3f417b06c21f', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'c05b57e6-c1b5-4f62-84c3-5611d021a062', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'cae7281f-9420-4b4e-9ad4-43765df2030c', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'fa23de2e-9622-4747-997b-53fcfa6527a3', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'fb5114a1-f800-4e14-b82a-8a9b9f7c86d9', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '939ee6fc-2d48-4c07-83c8-7166be4223a7', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'bb73a0e0-3d77-4b9a-a8d1-f00f1188d927', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '50b72e83-303d-4bc0-8e9f-28b40fa3ba36', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '7f247ec6-36fd-4f7d-b69c-3eaa0bdb0c7b', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', '47b041b2-7690-4662-9962-003207f4db06', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'fdbcb36e-5d78-4a14-9a0e-0f49d295d780', 'ACTIVE'),
       ('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'b0dfb7ad-2ac0-41d3-9d62-3c39f098d1aa', 'ACTIVE')
ON CONFLICT (clinic_id, service_id) DO NOTHING;


INSERT INTO special_requirement (id, requirement, service_id)
VALUES ('1c34e8ee-8387-40b7-8f8e-1e3c4c2a0a55', 'Nhịn ăn 6 tiếng trước khám', 'e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761'),
       ('c76c7e5d-6bb6-45a2-8516-6938a92980ed', 'Mang theo hồ sơ bệnh cũ', 'ba8e6b51-d1ec-4f35-9f58-6c6947fbd26b'),
       ('99fb6bfa-12b6-4500-bd06-11b53907a0e5', 'Không uống thuốc trước 8 tiếng',
        '5ac9d4d9-1e82-464e-8a6e-705aee4f0c83'),
       ('ae4232fd-2e5b-4ac9-9006-9f9e15d1887e', 'Mang sổ khám thai', 'fb5114a1-f800-4e14-b82a-8a9b9f7c86d9'),
       ('bffdbbd1-5fb1-4d89-91a7-2d6f11782a50', 'Không đeo kính áp tròng 8 giờ trước khám',
        'b0dfb7ad-2ac0-41d3-9d62-3c39f098d1aa'),
       ('4c1e2c9a-1ea6-4a5c-8f2e-30c8c858eb17', 'Không trang điểm khi khám da', '6a6c5fd8-f4f7-4c1d-91f2-31f9b3fcbb38'),
       ('35bc541d-9f59-45d3-8c50-85eb1a79b5e3', 'Kiêng ăn 6 tiếng trước nội soi',
        'b37bde4d-bf2f-4b14-8181-72923ec0ff03'),
       ('f9a3c49f-9e06-4a31-b804-4e61b67ba33f', 'Làm test COVID trước nội soi', '35453ac7-4d1d-44fa-90a6-4b18c676ec34')
    ON CONFLICT (id) DO NOTHING;




-- Sample doctors with clinic assignments
INSERT INTO "doctor" (
    id,
    user_id,
    first_name,
    last_name,
    email,
    phone,
    gender,
    profile_picture,
    experience_years,
    license_number,
    education,
    status,
    clinic_id
) VALUES
      ('a1b2c3d4-e5f6-7890-abcd-ef1234567890',
       'eb7c3204-dfbe-4eba-a06c-5a8bade70671',
       'John',
       'Doe',
       'doctor@gmail.com',
       '+1234567890',
       'Male',
       'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4IJtnDL22_yu-R6A4wxvCOfofHIihezQK-Q&s',
       10,
       'LIC123456',
       'MD, Cardiology, Harvard Medical School',
       'ACTIVE',
       'a774500c-6dd1-4378-a5f9-ac91458a9b6f'),

      ('b2c3d4e5-f678-90ab-cdef-234567890abc',
       'ce97d623-dde7-437e-aa71-5535e491ba33',
       'Jane',
       'Smith',
       'doctor1@gmail.com',
       '+1987654321',
       'Female',
       'https://img.freepik.com/free-psd/cute-3d-cartoon-female-doctor-wearing-glasses-white-coat-with-stethoscope-healthcare-professional-illustration_632498-32034.jpg',
       7,
       'LIC654321',
       'MD, Dermatology, Stanford University',
       'ACTIVE',
       'c51b8083-58a3-4db1-98b7-326c9e3e7571'),
      ('2d1a60e1-fb44-4b93-86c3-1c5ef7c379a1', '94a75cbe-1a45-4666-b9a7-22bc6d12856f', 'Minh', 'Nguyễn', 'minh.nguyen@clinic.com', '0901122334', 'Male',
       'https://example.com/doctor/minh.png', 10, 'DOC-001', 'Đại học Y Dược TP.HCM', 'ACTIVE', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f'),

      ('3c83ab8b-28d6-4b6b-a8a4-d6d2421f1b88', '35c1b961-383b-4c0f-a4bc-9be7b15a743b', 'Lan', 'Trần', 'lan.tran@clinic.com', '0912345678', 'Female',
       'https://example.com/doctor/lan.png', 8, 'DOC-002', 'Đại học Y Hà Nội', 'ACTIVE', 'c51b8083-58a3-4db1-98b7-326c9e3e7571'),

      ('5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', '73a69a3c-ff94-4458-8c26-0b2c4ff0b163', 'Tùng', 'Phạm', 'tung.pham@clinic.com', '0923456789', 'Male',
       'https://example.com/doctor/tung.png', 12, 'DOC-003', 'Đại học Y Dược TP.HCM', 'ACTIVE', 'c51b8083-58a3-4db1-98b7-326c9e3e7571'),

      ('9a654af0-49e1-4a2d-b12d-e5b87f95cd93', 'aa463bf6-1669-409f-b6d1-d92ea6ff1a8a', 'Hương', 'Lê', 'huong.le@clinic.com', '0934567890', 'Female',
       'https://example.com/doctor/huong.png', 5, 'DOC-004', 'Đại học Y Cần Thơ', 'ACTIVE', '7eb0a7de-9658-4dbf-a42f-1804f2b5d734')
ON CONFLICT (id) DO NOTHING;




-- Doctor-Specialty many-to-many relationship
INSERT INTO doctor_speciality (doctor_id, speciality_id) VALUES
                                                             ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b07'), -- Cardiology
                                                             ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01'), -- General medicine
                                                             ('b2c3d4e5-f678-90ab-cdef-234567890abc', 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06')  -- Dermatology
ON CONFLICT (doctor_id, speciality_id) DO NOTHING;

CREATE TABLE IF NOT EXISTS doctor_schedule (
    id UUID PRIMARY KEY,
    doctor_id UUID NOT NULL,
    clinic_id UUID NOT NULL,
    day_of_week VARCHAR(20) NOT NULL CHECK (day_of_week IN ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    slot_duration_minutes INT NOT NULL DEFAULT 60,
    break_minutes_between_slots INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE,
    CONSTRAINT fk_clinic FOREIGN KEY (clinic_id) REFERENCES clinic(id) ON DELETE CASCADE,
    CONSTRAINT check_times CHECK (start_time < end_time)
);

-- Sample data for doctor_schedule
INSERT INTO doctor_schedule (
    id,
    doctor_id,
    clinic_id,
    day_of_week,
    start_time,
    end_time,
    slot_duration_minutes,
    break_minutes_between_slots,
    is_active
) VALUES
    -- Doctor John Doe's schedule
    ('307d75c0-3810-497a-845a-71ef69eb5516', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'MONDAY', '08:00:00', '12:00:00', 30, 10, TRUE),
    ('eb31d12c-07bf-482a-b914-d8e676c26046', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'WEDNESDAY', '13:00:00', '17:00:00', 30, 10, TRUE),
    ('e7263444-5c09-4a7a-917a-87bb07d4d10b', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'FRIDAY', '08:00:00', '12:00:00', 30, 10, TRUE),

    -- Doctor Jane Smith's schedule
    ('484fe408-88c6-4a36-a323-b2cf0c8f1d01', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'TUESDAY', '09:00:00', '12:00:00', 45, 15, TRUE),
    ('8d78385e-1b9e-484f-8fe2-0e63d170650f', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'THURSDAY', '14:00:00', '18:00:00', 45, 15, TRUE),
    ('3aab909e-67c0-4fa7-93ac-537f4b22efba', 'b2c3d4e5-f678-90ab-cdef-234567890abc', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'SATURDAY', '10:00:00', '14:00:00', 45, 15, TRUE),

-- Doctor Tùng Phạm's schedule (6 days, 9 slots)
-- Monday
('b7a3d317-04a0-40a0-bfae-4a1d24d6e441', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'MONDAY', '08:00:00', '12:00:00', 30,5, TRUE),
('cc8d2c6d-8391-4f86-91c2-d0c8c34c258e', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'MONDAY', '13:00:00', '17:00:00', 30, 5,TRUE),
-- Tuesday
('52417132-cb01-46e2-a617-6d438d287e14', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'TUESDAY', '08:00:00', '12:00:00', 30, 5,TRUE),
-- Wednesday
('38b19310-4448-4de4-aef5-558a3b1e7502', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'WEDNESDAY', '13:00:00', '17:00:00', 30, 5,TRUE),
-- Thursday
('ebf0988a-4f74-4ef4-89f5-9bbf8f456bf9', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'THURSDAY', '08:00:00', '12:00:00', 30, 5,TRUE),
-- Friday
('30d7d5fd-3217-4706-a9b6-305e2928bb3d', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'FRIDAY', '13:00:00', '17:00:00', 30, 5,TRUE),
-- Saturday
('fd9a9c70-010e-4d67-a994-392f20bd889e', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'SATURDAY', '08:00:00', '12:00:00', 30, 5,TRUE),
('d56f4e3f-d09c-4a9b-a650-9016e0e5e81c', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'SATURDAY', '13:00:00', '17:00:00', 30, 5,TRUE),
-- Sunday
('e6223c9f-8c56-4cc0-aad1-999f9a0d77e2', '5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'SUNDAY', '09:00:00', '11:00:00', 30, 5,TRUE),
-- Doctor Hương Lê's schedule (3 days)

    ('ee775cb4-9a18-4986-91c3-7352a33cf06a', '9a654af0-49e1-4a2d-b12d-e5b87f95cd93', '7eb0a7de-9658-4dbf-a42f-1804f2b5d734', 'MONDAY', '08:00:00', '11:00:00', 15, 10,TRUE),
    ('a3a17177-2c5b-4628-b99a-c319143cb4c7', '9a654af0-49e1-4a2d-b12d-e5b87f95cd93', '7eb0a7de-9658-4dbf-a42f-1804f2b5d734', 'WEDNESDAY', '13:00:00', '16:00:00', 15, 10,TRUE),
    ('1f9c8f2e-2904-441b-bdf2-7d648a58fcb9', '9a654af0-49e1-4a2d-b12d-e5b87f95cd93', '7eb0a7de-9658-4dbf-a42f-1804f2b5d734', 'FRIDAY', '09:00:00', '12:00:00', 15, 10,TRUE),
-- Doctor Minh Nguyễn's schedule (4 days)
    ('a6b5ff20-927c-4e5c-a7ff-bd6b3b0666c0', '2d1a60e1-fb44-4b93-86c3-1c5ef7c379a1', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'TUESDAY', '08:00:00', '12:00:00', 20, 15,TRUE),
    ('7c3d6c48-cbaa-4b13-a176-9c6fd26b7f91', '2d1a60e1-fb44-4b93-86c3-1c5ef7c379a1', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'THURSDAY', '13:00:00', '17:00:00', 20, 15,TRUE),
    ('7df2c2ed-7a49-48e8-8f45-e09f7b7fc90f', '2d1a60e1-fb44-4b93-86c3-1c5ef7c379a1', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'SATURDAY', '09:00:00', '13:00:00', 20, 15,TRUE),
    ('f25d4a1e-b7f0-43e4-a97b-fd6377b40813', '2d1a60e1-fb44-4b93-86c3-1c5ef7c379a1', 'a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'SUNDAY', '10:00:00', '12:00:00', 20, 15,TRUE),
-- Doctor Lan Trần's schedule (2 days)
    ('3dd7b01c-1c7c-4bc7-bdd2-1c674376db12', '3c83ab8b-28d6-4b6b-a8a4-d6d2421f1b88', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'WEDNESDAY', '08:00:00', '12:00:00', 20, 5,TRUE),
    ('1e3f2480-3e5e-44a6-a7a4-4a2683d4c9f3', '3c83ab8b-28d6-4b6b-a8a4-d6d2421f1b88', 'c51b8083-58a3-4db1-98b7-326c9e3e7571', 'FRIDAY', '13:00:00', '17:00:00', 20, 5,TRUE)
ON CONFLICT (id) DO NOTHING;

-- Doctor-Service join table
CREATE TABLE IF NOT EXISTS doctor_service (
    doctor_id UUID NOT NULL,
    service_id UUID NOT NULL,
    PRIMARY KEY (doctor_id, service_id),
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE,
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES service(service_id) ON DELETE CASCADE
);

-- Sample data for doctor_service
INSERT INTO doctor_service (doctor_id, service_id) VALUES
    -- Doctor John Doe's services (Cardiology and General medicine)
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761'),  -- General checkup
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'ba8e6b51-d1ec-4f35-9f58-6c6947fbd26b'),  -- Diabetes consultation
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', '5ac9d4d9-1e82-464e-8a6e-705aee4f0c83'),  -- Hypertension checkup

    -- Doctor Jane Smith's services (Dermatology)
    ('b2c3d4e5-f678-90ab-cdef-234567890abc', '6a6c5fd8-f4f7-4c1d-91f2-31f9b3fcbb38'),  -- Acne treatment
    ('b2c3d4e5-f678-90ab-cdef-234567890abc', 'f9db6d4a-0d77-4d45-b7ee-8027032bdf63'),  -- Skin analysis
    ('b2c3d4e5-f678-90ab-cdef-234567890abc', 'e41b50a5-c167-4c46-8aa5-227b8efeb14b'),  -- Skin care

    -- Doctor Minh Nguyễn's services
    ('2d1a60e1-fb44-4b93-86c3-1c5ef7c379a1', 'e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761'),  -- General checkup
    ('2d1a60e1-fb44-4b93-86c3-1c5ef7c379a1', 'cfb78b79-ff2e-4891-8b4b-b672f85d160f'),  -- Liver screening
    ('2d1a60e1-fb44-4b93-86c3-1c5ef7c379a1', '68c73b74-d5db-4ff3-8e09-5e930b36086d'),  -- Pediatric checkup

    -- Doctor Lan Trần's services
    ('3c83ab8b-28d6-4b6b-a8a4-d6d2421f1b88', 'fb5114a1-f800-4e14-b82a-8a9b9f7c86d9'),  -- Pregnancy checkup
    ('3c83ab8b-28d6-4b6b-a8a4-d6d2421f1b88', '939ee6fc-2d48-4c07-83c8-7166be4223a7'),  -- Prenatal consultation
    ('3c83ab8b-28d6-4b6b-a8a4-d6d2421f1b88', 'bb73a0e0-3d77-4b9a-a8d1-f00f1188d927'),  -- Gynecology checkup

    -- Doctor Tùng Phạm's services
    ('5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', '47b041b2-7690-4662-9962-003207f4db06'),  -- ENT checkup
    ('5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'fdbcb36e-5d78-4a14-9a0e-0f49d295d780'),  -- ENT endoscopy
    ('5fd451cc-c4aa-4ab4-b2d1-ec33a4d2de75', 'b0dfb7ad-2ac0-41d3-9d62-3c39f098d1aa'),  -- Hearing test

    -- Doctor Hương Lê's services
    ('9a654af0-49e1-4a2d-b12d-e5b87f95cd93', '16c78249-5084-4d90-874e-d2fcbeb99795'),  -- Pediatric respiratory checkup
    ('9a654af0-49e1-4a2d-b12d-e5b87f95cd93', '8a7d6f87-099e-4971-8e06-556522d9f6ee'),  -- Pediatric nutrition consultation
    ('9a654af0-49e1-4a2d-b12d-e5b87f95cd93', '68c73b74-d5db-4ff3-8e09-5e930b36086d')   -- Pediatric checkup
ON CONFLICT (doctor_id, service_id) DO NOTHING;

-- Adding index to improve query performance for service lookup
CREATE INDEX IF NOT EXISTS idx_doctor_service_service_id ON doctor_service(service_id);
CREATE INDEX IF NOT EXISTS idx_doctor_service_doctor_id ON doctor_service(doctor_id);