#  Phần mềm Quản lý Sinh viên

Đồ án môn lập trình Java – ứng dụng desktop (Java Swing) kết nối cơ sở dữ liệu **SQL Server** để quản lý sinh viên, lớp, khoa, môn học, điểm và tài khoản.

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

## Cấu trúc thư mục

```
QuanLySinhVien/
├── README.md
├── .gitignore
├── pom.xml                         # Cấu hình Maven
├── database/
│   ├──SQL_DoAnQuanLySinhVien.sql         # Tạo database + 6 bảng
│   └──ThemDuLieu.sql         # Thêm dữ liệu cho các bảng
├── docs/
│   └── ThietKe_CoSoDuLieu_QuanLySinhVien.docx   # Báo cáo thiết kế CSDL
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

Chi tiết xem file Word trong thư mục `docs/`.

## 🚀 Hướng dẫn cài đặt và chạy

### Bước 1: Chuẩn bị
- Cài **JDK 11+** và **Maven** (hoặc dùng IDE NetBeans / IntelliJ đã có sẵn Maven).
- Cài **SQL Server** + SQL Server Management Studio (SSMS).

### Bước 2: Tạo database
Mở SSMS, lần lượt chạy (Execute) 2 file:
1. `database/01_create_tables.sql`
2. `database/02_sample_data.sql`

### Bước 3: Cấu hình kết nối
Mở `src/main/java/qlsv/db/DBConnection.java` và sửa cho đúng máy của bạn:

```java
private static final String SERVER   = "localhost";
private static final String PORT     = "1433";
private static final String DATABASE = "QLSinhVien_HuyenTrang";
private static final String USER     = "sa";
private static final String PASSWORD = "123456";   // <-- đổi thành mật khẩu của bạn
```

### Bước 4: Chạy chương trình

**Cách 1 – Dòng lệnh:**
```bash
mvn compile exec:java
```

**Cách 2 – IDE (NetBeans / IntelliJ / Eclipse):**
Chọn *Open Project* → chọn thư mục dự án (nhận dạng là Maven project) → chuột phải `Main.java` → *Run*.

### Tài khoản đăng nhập có sẵn

| Tên đăng nhập | Mật khẩu |
|---------------|----------|
| admin | 123456 |
| trang | 12345 |
| Nam | 1234 |

## 📝 Ghi chú
- Mật khẩu lưu dạng văn bản thường theo thiết kế CSDL của đồ án (không mã hóa)
- Khi xóa khoa/lớp/sinh viên/môn học đang có dữ liệu liên quan, hệ thống sẽ báo lỗi ràng buộc khóa ngoại (cần xóa dữ liệu con trước).
