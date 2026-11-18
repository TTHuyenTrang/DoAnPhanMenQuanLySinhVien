package qlsv.ui;

public class SinhVienPanel extends FromGiaoDien {
    public SinhVienPanel() {
        super("Quản lý Sinh viên", "SinhVien",
            new SetCotDuLieu("Mã SV", "MaSv").key(),
            new SetCotDuLieu("Họ sinh viên", "HoSinhVien").required(),
            new SetCotDuLieu("Tên", "Ten").required(),
            new SetCotDuLieu("Ngày sinh (yyyy-MM-dd)", "Nsinh"),
            new SetCotDuLieu("Giới tính", "GioiTinh").items("Nam", "Nữ"),
            new SetCotDuLieu("Địa chỉ", "DiaChi"),
            new SetCotDuLieu("Điện thoại", "SoDienThoaiSV"),
            new SetCotDuLieu("Email", "EmailSV"),
            new SetCotDuLieu("Mã lớp", "MaLop").required().sql("SELECT MaLop FROM Lop ORDER BY MaLop"));
    }
}
