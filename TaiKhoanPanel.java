package qlsv.ui;

public class TaiKhoanPanel extends FromGiaoDien {
    public TaiKhoanPanel() {
        super("Quản lý Tài khoản", "TaiKhoan",
            new SetCotDuLieu("Tên đăng nhập", "TenDangNhap").key(),
            new SetCotDuLieu("Mật khẩu", "MatKhau").required(),
            new SetCotDuLieu("Tên người dùng", "TenNguoiDung"));
    }
}
