package qlsv.ui;

public class KhoaPanel extends FromGiaoDien {
    public KhoaPanel() {
        super("Quản lý Khoa", "Khoa",
            new SetCotDuLieu("Mã khoa", "MaKhoa").key(),
            new SetCotDuLieu("Tên khoa", "TenKhoa").required(),
            new SetCotDuLieu("Trưởng khoa", "TruongKhoa"),
            new SetCotDuLieu("Điện thoại", "SoDienThoaiKhoa"),
            new SetCotDuLieu("Email", "EmailKhoa"));
    }
}
