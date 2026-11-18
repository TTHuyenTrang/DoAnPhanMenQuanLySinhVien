Create database QLSinhVien_HuyenTrang
GO
Use QLSinhVien_HuyenTrang
GO
Create table Khoa
(
	MaKhoa char(10) not null Primary key,
	TenKhoa nvarchar(50) not null,
	TruongKhoa nvarchar(50),
	SoDienThoaiKhoa varchar(15),
	EmailKhoa varchar(100)
)
GO
Create table Lop
(
    MaLop CHAR(10) Primary key,
    TenLop nvarchar(50) not null,
    SiSo int,
    MaKhoa char(10) not null,
	constraint FK_Lop_Khoa Foreign key(MaKhoa) references Khoa(MaKhoa)
)
GO
Create table SinhVien
(
	MaSv char(10) not null Primary key,
	HoSinhVien nvarchar(50) not null ,
	Ten nvarchar(50) not null ,
	Nsinh date,
	GioiTinh nchar(3),
	DiaChi nvarchar(100),
	SoDienThoaiSV varchar(15),
    EmailSV varchar(100),
	MaLop char(10) not null,
	constraint FK_SinhVien_Lop Foreign key (MaLop) references Lop(MaLop)
)
GO
Create table MonHoc
(
	MaMH char(10) not null Primary key,
	TenMH nvarchar(50) not null,
	SoTiet int not null,
	SoTinChi int not null,
	LoaiMon nvarchar(50),
	MaKhoa char(10) not null,
	constraint FK_MonHoc_Khoa Foreign key(MaKhoa) references Khoa(MaKhoa)
)
GO
Create table KetQua
(
	MaSv char(10) not null,
	MaMH char(10) not null,
	LanThi int not null,
	DiemKiemtra decimal(4,2),
	DiemThi decimal(4,2),
	DiemHocPhan decimal(4,2),
	NgayThi date,
	HocKy int,
	NamHoc varchar(9),
	Primary Key(MaSv,MaMH,LanThi),
	constraint FK_KetQua_MaSv Foreign key(MaSv) references SinhVien(MaSv),
	constraint Ma_KetQua_MaMH Foreign key(MaMH) references MonHoc(MaMH)
)
GO
Create table TaiKhoan
(
    TenDangNhap varchar (50) Primary key,
    MatKhau varchar(50) NOT NULL,
    TenNguoiDung nvarchar(20)
)