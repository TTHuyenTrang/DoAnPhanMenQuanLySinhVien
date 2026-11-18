package qlsv.ui;

public class LopPanel extends FromGiaoDien {
    public LopPanel() {
        super("Quản lý Lớp", "Lop",
            new SetCotDuLieu("Mã lớp", "MaLop").key(),
            new SetCotDuLieu("Tên lớp", "TenLop").required(),
            new SetCotDuLieu("Sĩ số", "SiSo"),
            new SetCotDuLieu("Mã khoa", "MaKhoa").required().sql("SELECT MaKhoa FROM Khoa ORDER BY MaKhoa"));
    }
}
