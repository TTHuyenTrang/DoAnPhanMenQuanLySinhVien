package qlsv.ui;

public class MonHocPanel extends FromGiaoDien {
    public MonHocPanel() {
        super("Quản lý Môn học", "MonHoc",
            new SetCotDuLieu("Mã môn", "MaMH").key(),
            new SetCotDuLieu("Tên môn", "TenMH").required(),
            new SetCotDuLieu("Số tiết", "SoTiet").required(),
            new SetCotDuLieu("Số tín chỉ", "SoTinChi").required(),
            new SetCotDuLieu("Loại môn", "LoaiMon").items("Bắt buộc", "Tự chọn"),
            new SetCotDuLieu("Mã khoa", "MaKhoa").required().sql("SELECT MaKhoa FROM Khoa ORDER BY MaKhoa"));
    }
}
