#  Phần mềm Quản lý Sinh viên

Đồ án môn lập trình Java – áp dụng Java Swing kết nối cơ sở dữ liệu **SQL Server** để quản lý thông tin sinh viên, lớp, khoa, môn học và điểm.

- **Sinh viên thực hiện:** Trần Thị Huyền Trang
- **Lớp:** TH30.21

---

##  Chức năng

| Tab | Chức năng |
|-----|-----------|
| Đăng nhập | Xác thực bằng bảng `TaiKhoan` |
| Sinh viên | Thêm / Sửa / Xóa / Tìm kiếm thông tin sinh viên |
| Lớp | Quản lý lớp, thuộc khoa nào |
| Khoa | Quản lý khoa (trưởng khoa, SĐT, email) |
| Môn học | Quản lý môn học thuộc từng khoa |
| Kết quả | Quản lý điểm theo sinh viên – môn – lần thi (tự tính điểm học phần = 30% kiểm tra + 70% thi nếu để trống) |
| Tài khoản | Quản lý người dùng hệ thống |

Mọi tab đều có: **Thêm, Sửa, Xóa, Làm mới, Tìm kiếm** (tìm theo bất kỳ cột nào). Các khóa ngoại (mã khoa, mã lớp, mã SV, mã môn) được chọn bằng ComboBox nên không nhập sai mã.

## Công nghệ

- Java 11 trở lên (Swing, JDBC)
- SQL Server (Express / Developer đều được)
- Maven (quản lý thư viện `mssql-jdbc`)

## Cấu trúc 

```
QuanLySinhVien/
├── README.md
├── .gitignore
├── pom.xml                         # Cấu hình Maven
├── database/
│   ├──SQL_DoAnQuanLySinhVien.sql         # Tạo database + 6 bảng
│   └──ThemDuLieu.sql         # Thêm dữ liệu cho các bảng
└── src/main/java/qlsv/
    ├── Main.java                   # Điểm khởi chạy
    ├── db/
    │   └── KetNoiSQLSever.java       # Kết nối SQL Server
    └── ui/
        ├── SetCotDuLieu.java              # Mô tả 1 cột dữ liệu
        ├── FromGiaoDien.java          # Panel dùng chung: form + bảng + Thêm/Sửa/Xóa/Tìm
        ├── LoginForm.java          # Form đăng nhập
        ├── MainForm.java           # Form chính (các tab)
        ├── SinhVienPanel.java
        ├── LopPanel.java
        ├── KhoaPanel.java
        ├── MonHocPanel.java
        ├── KetQuaPanel.java
        └── TaiKhoanPanel.java
```

## 🗄 Cơ sở dữ liệu

Database: `QLSinhVien_HuyenTrang` gồm 6 bảng:

```
Khoa (MaKhoa PK, TenKhoa, TruongKhoa, SoDienThoaiKhoa, EmailKhoa)
Lop (MaLop PK, TenLop, SiSo, MaKhoa FK→Khoa)
SinhVien (MaSv PK, HoSinhVien, Ten, Nsinh, GioiTinh, DiaChi, SoDienThoaiSV, EmailSV, MaLop FK→Lop)
MonHoc (MaMH PK, TenMH, SoTiet, SoTinChi, LoaiMon, MaKhoa FK→Khoa)
KetQua (MaSv, MaMH, LanThi PK; DiemKiemtra, DiemThi, DiemHocPhan, NgayThi, HocKy, NamHoc)
TaiKhoan (TenDangNhap PK, MatKhau, TenNguoiDung)
```

Quan hệ: Khoa 1-n Lớp · Khoa 1-n Môn học · Lớp 1-n Sinh viên · Sinh viên 1-n Kết quả · Môn học 1-n Kết quả.

### Tài khoản đăng nhập có sẵn

| Tên đăng nhập | Mật khẩu |
|---------------|----------|
| admin | 123456 |
| trang | 12345 |
| Nam | 1234 |

## 📝 Ghi chú
- Mật khẩu lưu dạng văn bản thường theo thiết kế CSDL của đồ án (không mã hóa)
- Khi xóa khoa/lớp/sinh viên/môn học đang có dữ liệu liên quan, hệ thống sẽ báo lỗi ràng buộc khóa ngoại (cần xóa dữ liệu con trước).
