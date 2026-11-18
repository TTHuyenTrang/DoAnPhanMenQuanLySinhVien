package qlsv.ui;

import java.util.Locale;

public class KetQuaPanel extends FromGiaoDien {
    public KetQuaPanel() {
        super("Quản lý Kết quả (Điểm)", "KetQua",
            new SetCotDuLieu("Mã SV", "MaSv").key().sql("SELECT MaSv FROM SinhVien ORDER BY MaSv"),
            new SetCotDuLieu("Mã môn", "MaMH").key().sql("SELECT MaMH FROM MonHoc ORDER BY MaMH"),
            new SetCotDuLieu("Lần thi", "LanThi").key(),
            new SetCotDuLieu("Điểm kiểm tra", "DiemKiemTra"),
            new SetCotDuLieu("Điểm thi", "DiemThi"),
            new SetCotDuLieu("Điểm học phần", "DiemHocPhan"),
            new SetCotDuLieu("Ngày thi (yyyy-MM-dd)", "NgayThi"),
            new SetCotDuLieu("Học kỳ", "HocKy"),
            new SetCotDuLieu("Năm học (VD 2025-2026)", "NamHoc"));
    }

    /** Nếu để trống Điểm học phần thì tự tính = 30% điểm kiểm tra + 70% điểm thi. */
    @Override
    protected void beforeSave() {
        if (!get("DiemHocPhan").isEmpty() || get("DiemKiemTra").isEmpty() || get("DiemThi").isEmpty()) return;
        try {
            double dhp = Double.parseDouble(get("DiemKiemTra")) * 0.3 + Double.parseDouble(get("DiemThi")) * 0.7;
            set("DiemHocPhan", String.format(Locale.US, "%.2f", dhp));
        } catch (NumberFormatException ignored) { }
    }
}
