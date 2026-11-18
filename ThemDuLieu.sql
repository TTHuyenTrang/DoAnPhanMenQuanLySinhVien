
USE QLSinhVien_HuyenTrang
GO

INSERT INTO Khoa VALUES
('CNTT', N'Công nghệ thông tin', N'Nguyễn Văn An',  '0241234567', 'cntt@school.edu.vn'),
('KT',   N'Kinh tế',             N'Trần Thị Bình',  '0241234568', 'kt@school.edu.vn'),
('NN',   N'Ngoại ngữ',           N'Lê Văn Cường',   '0241234569', 'nn@school.edu.vn')
GO

INSERT INTO Lop VALUES
('TH3021', N'Tin học 30.21',      30, 'CNTT'),
('TH3022', N'Tin học 30.22',      28, 'CNTT'),
('KT3021', N'Kế toán 30.21',      35, 'KT'),
('NN3021', N'Tiếng Anh 30.21',    32, 'NN')
GO

INSERT INTO SinhVien VALUES
('SV001', N'Trần Thị Huyền', N'Trang', '2007-12-30', N'Nữ',  N'Hà Nội',   '0355903594', 'Lantrang3112@mail.com', 'TH3021'),
('SV002', N'Nguyễn Văn',     N'Minh',  '2005-07-21', N'Nam', N'Hải Phòng','0987000002', 'minh@mail.com',  'TH3021'),
('SV003', N'Lê Thị',         N'Hoa',   '2005-11-05', N'Nữ',  N'Nam Định', '0987000003', 'hoa@mail.com',   'TH3022'),
('SV004', N'Phạm Văn',       N'Dũng',  '2004-01-30', N'Nam', N'Thái Bình','0987000004', 'dung@mail.com',  'KT3021'),
('SV005', N'Hoàng Thị',      N'Lan',   '2005-09-17', N'Nữ',  N'Hà Nam',   '0987000005', 'lan@mail.com',   'NN3021')
GO

INSERT INTO MonHoc VALUES
('MH01', N'Cơ sở dữ liệu',        45, 3, N'Bắt buộc', 'CNTT'),
('MH02', N'Lập trình Java',       60, 4, N'Bắt buộc', 'CNTT'),
('MH03', N'Kế toán căn bản',      45, 3, N'Bắt buộc', 'KT'),
('MH04', N'Tiếng Anh chuyên ngành',30, 2, N'Tự chọn', 'NN')
GO

INSERT INTO KetQua VALUES
('SV001', 'MH01', 1, 8.0, 8.5, 8.35, '2026-06-10', 1, '2025-2026'),
('SV001', 'MH02', 1, 9.0, 9.0, 9.00, '2026-06-15', 1, '2025-2026'),
('SV002', 'MH01', 1, 4.0, 3.5, 3.65, '2026-06-10', 1, '2025-2026'),
('SV002', 'MH01', 2, 6.0, 6.0, 6.00, '2026-07-05', 1, '2025-2026'),
('SV003', 'MH02', 1, 7.0, 7.5, 7.35, '2026-06-15', 1, '2025-2026')
GO

INSERT INTO TaiKhoan VALUES
('admin', '123456', N'Quản trị viên'),
('trang', '123',    N'Huyền Trang')
GO
