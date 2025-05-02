CREATE TABLE IF NOT EXISTS public.clinic_owner (
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

CREATE TABLE IF NOT EXISTS public.clinic (
    id UUID PRIMARY KEY,
    address TEXT NOT NULL,
    name TEXT NOT NULL,
    phone TEXT NOT NULL,
    description TEXT,
    email TEXT NOT NULL,
    image TEXT,
    status VARCHAR(20) NOT NULL CHECK (status IN ('OPEN', 'CLOSED')),
    owner_id UUID NOT NULL,
    CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES public.clinic_owner(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.speciality (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_url TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.service (
    service_id UUID PRIMARY KEY,
    service_name TEXT NOT NULL,
    speciality_id UUID NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    duration TIME NOT NULL,
    requires_prescription BOOLEAN NOT NULL,
    active BOOLEAN NOT NULL,

    CONSTRAINT fk_speciality
        FOREIGN KEY (speciality_id)
        REFERENCES public.speciality(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.clinic_service (
    clinic_id UUID NOT NULL,
    service_id UUID NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    PRIMARY KEY (clinic_id, service_id),
    CONSTRAINT fk_clinic FOREIGN KEY (clinic_id) REFERENCES public.clinic(id) ON DELETE CASCADE,
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES public.service(service_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.special_requirement (
    id UUID PRIMARY KEY,
    requirement TEXT NOT NULL,
    service_id UUID NOT NULL,
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES public.service(service_id) ON DELETE CASCADE
);


INSERT INTO public.clinic_owner (
    id,
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
    user_id
)
VALUES (
    'affd0938-a77d-4404-8461-788e64ad1dab',
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
    '3f852db7-8d9c-4ba0-af91-b96bf9ddfcb7'
);

INSERT INTO public.clinic (
    id,
    address,
    name,
    phone,
    description,
    email,
    image,
    status,
    owner_id
)
VALUES (
    'a774500c-6dd1-4378-a5f9-ac91458a9b6f',
    '123 Đường Nguyễn Huệ, Quận 1, TP.HCM',
    'Phòng Khám Lê Hoàng',
    '0399733849',
    'Phòng khám đa khoa chất lượng cao do bác sĩ Lê Hoàng quản lý.',
    '2251120349@ut.edu.vn',
    'https://example.com/images/clinic.png',
    'CLOSED',
    'affd0938-a77d-4404-8461-788e64ad1dab'
       );

INSERT INTO public.speciality (
    id,
    name,
    description,
    image_url
) VALUES
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Nội tổng quát', 'Khám và điều trị các bệnh nội khoa.', 'https://example.com/images/noi-tong-quat.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Ngoại tổng quát', 'Phẫu thuật và điều trị ngoại khoa cơ bản.', 'https://example.com/images/ngoai-tong-quat.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b03', 'Nhi khoa', 'Chăm sóc sức khỏe trẻ em.', 'https://example.com/images/nhi-khoa.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Sản phụ khoa', 'Chăm sóc sức khỏe phụ nữ và thai kỳ.', 'https://example.com/images/san-phu-khoa.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b05', 'Tai Mũi Họng', 'Khám và điều trị các bệnh về tai, mũi, họng.', 'https://example.com/images/tai-mui-hong.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Da liễu', 'Chăm sóc và điều trị các bệnh ngoài da.', 'https://example.com/images/da-lieu.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b07', 'Tim mạch', 'Khám và điều trị các bệnh về tim.', 'https://example.com/images/tim-mach.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b08', 'Tiêu hóa', 'Chẩn đoán và điều trị các bệnh về tiêu hóa.', 'https://example.com/images/tieu-hoa.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b09', 'Thần kinh', 'Khám và điều trị bệnh lý thần kinh.', 'https://example.com/images/than-kinh.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b10', 'Chấn thương chỉnh hình', 'Xử lý các vấn đề xương khớp, gãy xương.', 'https://example.com/images/chinh-hinh.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b11', 'Mắt', 'Khám và điều trị các bệnh về mắt.', 'https://example.com/images/mat.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b12', 'Răng Hàm Mặt', 'Chăm sóc sức khỏe răng miệng.', 'https://example.com/images/rang-ham-mat.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b13', 'Nội tiết', 'Khám và điều trị rối loạn nội tiết.', 'https://example.com/images/noi-tiet.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b14', 'Thận - Tiết niệu', 'Chẩn đoán và điều trị bệnh về thận, tiết niệu.', 'https://example.com/images/than-tiet-nieu.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b15', 'Ung bướu', 'Chẩn đoán và điều trị ung thư.', 'https://example.com/images/ung-buou.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b16', 'Hô hấp', 'Điều trị các bệnh về đường hô hấp.', 'https://example.com/images/ho-hap.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b17', 'Huyết học', 'Chẩn đoán và điều trị bệnh về máu.', 'https://example.com/images/huyet-hoc.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b18', 'Truyền nhiễm', 'Khám và điều trị bệnh truyền nhiễm.', 'https://example.com/images/truyen-nhiem.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b19', 'Y học cổ truyền', 'Điều trị theo phương pháp Đông y.', 'https://example.com/images/y-hoc-co-truyen.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b20', 'Tâm thần', 'Khám và điều trị các rối loạn tâm lý.', 'https://example.com/images/tam-than.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b21', 'Vật lý trị liệu', 'Phục hồi chức năng, điều trị không dùng thuốc.', 'https://example.com/images/vat-ly-tri-lieu.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b22', 'Phục hồi chức năng', 'Hỗ trợ bệnh nhân sau điều trị, phẫu thuật.', 'https://example.com/images/phuc-hoi-chuc-nang.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b23', 'Y học hạt nhân', 'Ứng dụng hạt nhân trong chẩn đoán và điều trị.', 'https://example.com/images/y-hoc-hat-nhan.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b24', 'Nội soi', 'Chẩn đoán, điều trị bằng phương pháp nội soi.', 'https://example.com/images/noi-soi.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b25', 'Siêu âm', 'Chẩn đoán hình ảnh bằng siêu âm.', 'https://example.com/images/sieu-am.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b26', 'Xét nghiệm', 'Phân tích mẫu bệnh phẩm để chẩn đoán.', 'https://example.com/images/xet-nghiem.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Chẩn đoán hình ảnh', 'CT, MRI, X-quang, chụp cộng hưởng từ.', 'https://example.com/images/chan-doan-hinh-anh.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b28', 'Bệnh nghề nghiệp', 'Điều trị các bệnh do môi trường làm việc.', 'https://example.com/images/benh-nghe-nghiep.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b29', 'Khám sức khỏe tổng quát', 'Tổng kiểm tra định kỳ toàn diện.', 'https://example.com/images/kham-suc-khoe.jpg'),
('f5c101d2-9f2a-46c0-b541-b1c09c1a1b30', 'Tư vấn di truyền', 'Phân tích và tư vấn bệnh lý di truyền.', 'https://example.com/images/tu-van-di-truyen.jpg');



INSERT INTO public.service (
    service_id,
    active,
    speciality_id,
    description,
    duration,
    price,
    requires_prescription,
    service_name
)
VALUES
-- Nội tổng quát - f5c101d2-9f2a-46c0-b541-b1c09c1a1b01
('e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Khám sức khoẻ định kỳ tổng quát cho người lớn.', '00:30:00', 500000, false, 'Khám sức khỏe tổng quát'),
('ba8e6b51-d1ec-4f35-9f58-6c6947fbd26b', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Tư vấn kiểm tra tiểu đường.', '00:30:00', 550000, false, 'Tư vấn tiểu đường'),
('5ac9d4d9-1e82-464e-8a6e-705aee4f0c83', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Khám tăng huyết áp.', '00:30:00', 550000, false, 'Kiểm tra tăng huyết áp'),
('cfb78b79-ff2e-4891-8b4b-b672f85d160f', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Tầm soát bệnh lý gan mật.', '00:40:00', 600000, false, 'Tầm soát gan mật'),
('e6a54fb8-1fc4-44f3-b503-9f5a96154770', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b01', 'Khám sức khỏe tiền hôn nhân.', '00:40:00', 600000, false, 'Khám tiền hôn nhân'),

-- Nhi khoa - f5c101d2-9f2a-46c0-b541-b1c09c1a1b03
('68c73b74-d5db-4ff3-8e09-5e930b36086d', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b03', 'Khám bệnh tổng quát trẻ em.', '00:25:00', 400000, false, 'Khám tổng quát cho trẻ'),
('8a7d6f87-099e-4971-8e06-556522d9f6ee', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b03', 'Tư vấn dinh dưỡng cho bé.', '00:30:00', 450000, false, 'Tư vấn dinh dưỡng nhi'),
('16c78249-5084-4d90-874e-d2fcbeb99795', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b03', 'Khám hô hấp trẻ em.', '00:30:00', 450000, false, 'Khám hô hấp nhi'),

-- Ngoại tổng quát - f5c101d2-9f2a-46c0-b541-b1c09c1a1b02
('0ad58e00-9e3d-41c4-8a7d-3f417b06c21f', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Tư vấn chấn thương nhẹ.', '00:30:00', 500000, false, 'Tư vấn chấn thương cơ bản'),
('c05b57e6-c1b5-4f62-84c3-5611d021a062', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Khám chấn thương thể thao.', '00:40:00', 700000, false, 'Khám chấn thương vận động'),
('cae7281f-9420-4b4e-9ad4-43765df2030c', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Đánh giá phục hồi chức năng.', '00:40:00', 650000, false, 'Tư vấn phục hồi chức năng'),
('fa23de2e-9622-4747-997b-53fcfa6527a3', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b02', 'Tư vấn hậu phẫu.', '00:30:00', 500000, false, 'Tư vấn sau phẫu thuật'),

-- Sản phụ khoa - f5c101d2-9f2a-46c0-b541-b1c09c1a1b04
('fb5114a1-f800-4e14-b82a-8a9b9f7c86d9', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Khám thai định kỳ.', '00:20:00', 400000, false, 'Khám thai'),
('939ee6fc-2d48-4c07-83c8-7166be4223a7', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Tư vấn tiền sản.', '00:30:00', 450000, false, 'Tư vấn tiền sản'),
('bb73a0e0-3d77-4b9a-a8d1-f00f1188d927', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Khám phụ khoa định kỳ.', '00:20:00', 400000, false, 'Khám phụ khoa'),
('50b72e83-303d-4bc0-8e9f-28b40fa3ba36', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Siêu âm thai 2D.', '00:20:00', 500000, false, 'Siêu âm thai'),
('7f247ec6-36fd-4f7d-b69c-3eaa0bdb0c7b', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b04', 'Tầm soát ung thư cổ tử cung.', '00:25:00', 600000, false, 'Tầm soát cổ tử cung'),

-- Tai Mũi Họng - f5c101d2-9f2a-46c0-b541-b1c09c1a1b05
('47b041b2-7690-4662-9962-003207f4db06', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b05', 'Khám tai mũi họng tổng quát.', '00:20:00', 350000, false, 'Khám tai mũi họng'),
('fdbcb36e-5d78-4a14-9a0e-0f49d295d780', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b05', 'Nội soi tai mũi họng.', '00:25:00', 500000, true, 'Nội soi tai mũi họng'),
('b0dfb7ad-2ac0-41d3-9d62-3c39f098d1aa', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b05', 'Đo thính lực.', '00:20:00', 400000, false, 'Đo thính lực'),

-- Da liễu - f5c101d2-9f2a-46c0-b541-b1c09c1a1b06
('6a6c5fd8-f4f7-4c1d-91f2-31f9b3fcbb38', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Tư vấn điều trị mụn.', '00:45:00', 700000, false, 'Điều trị mụn'),
('f9db6d4a-0d77-4d45-b7ee-8027032bdf63', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Soi da chuyên sâu.', '00:30:00', 600000, false, 'Soi da'),
('e41b50a5-c167-4c46-8aa5-227b8efeb14b', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Chăm sóc da cơ bản.', '01:00:00', 800000, false, 'Chăm sóc da mặt'),
('b7e579b7-8b02-4685-813e-8c4e9d5c6a19', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b06', 'Laser điều trị sẹo.', '00:45:00', 1500000, true, 'Laser trị sẹo'),

-- Chẩn đoán hình ảnh - f5c101d2-9f2a-46c0-b541-b1c09c1a1b27
('070d2233-c35d-4e7d-80a9-f57bcdab3a15', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Siêu âm ổ bụng tổng quát.', '00:20:00', 500000, false, 'Siêu âm ổ bụng'),
('1d8c9bd6-1797-4309-8f4f-bc8a3345fd00', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'X-quang phổi.', '00:15:00', 400000, false, 'X-quang phổi'),
('5a14f62a-7b3e-4fc7-80f3-fb23be71610a', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Chụp MRI não.', '00:45:00', 2500000, true, 'MRI não'),
('16b446b6-72a4-4fa3-88de-d88e52ef2529', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'CT-scan ngực.', '00:30:00', 1800000, true, 'CT ngực'),
('b37bde4d-bf2f-4b14-8181-72923ec0ff03', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Nội soi dạ dày.', '01:00:00', 1500000, true, 'Nội soi tiêu hóa'),
('35453ac7-4d1d-44fa-90a6-4b18c676ec34', TRUE, 'f5c101d2-9f2a-46c0-b541-b1c09c1a1b27', 'Nội soi đại tràng.', '00:45:00', 2000000, true, 'Nội soi đại tràng');



INSERT INTO public.clinic_service (clinic_id, service_id, status)
VALUES
('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761', 'ACTIVE'),
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
('a774500c-6dd1-4378-a5f9-ac91458a9b6f', 'b0dfb7ad-2ac0-41d3-9d62-3c39f098d1aa', 'ACTIVE');

INSERT INTO public.special_requirement (id, requirement, service_id)
VALUES
    ('1c34e8ee-8387-40b7-8f8e-1e3c4c2a0a55', 'Nhịn ăn 6 tiếng trước khám', 'e8a1c2b1-3b7b-49c2-9138-9df3e1f4b761'),
    ('c76c7e5d-6bb6-45a2-8516-6938a92980ed', 'Mang theo hồ sơ bệnh cũ', 'ba8e6b51-d1ec-4f35-9f58-6c6947fbd26b'),
    ('99fb6bfa-12b6-4500-bd06-11b53907a0e5', 'Không uống thuốc trước 8 tiếng', '5ac9d4d9-1e82-464e-8a6e-705aee4f0c83'),
    ('ae4232fd-2e5b-4ac9-9006-9f9e15d1887e', 'Mang sổ khám thai', 'fb5114a1-f800-4e14-b82a-8a9b9f7c86d9'),
    ('bffdbbd1-5fb1-4d89-91a7-2d6f11782a50', 'Không đeo kính áp tròng 8 giờ trước khám', 'b0dfb7ad-2ac0-41d3-9d62-3c39f098d1aa'),
    ('4c1e2c9a-1ea6-4a5c-8f2e-30c8c858eb17', 'Không trang điểm khi khám da', '6a6c5fd8-f4f7-4c1d-91f2-31f9b3fcbb38'),
    ('35bc541d-9f59-45d3-8c50-85eb1a79b5e3', 'Kiêng ăn 6 tiếng trước nội soi', 'b37bde4d-bf2f-4b14-8181-72923ec0ff03'),
    ('f9a3c49f-9e06-4a31-b804-4e61b67ba33f', 'Làm test COVID trước nội soi', '35453ac7-4d1d-44fa-90a6-4b18c676ec34');


