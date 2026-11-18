package qlsv.ui;

import qlsv.db.KetNoiSQLSever;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/** Form 1: Đăng nhập (kiểm tra bảng TaiKhoan). */
public class LoginForm extends JFrame {
    private final JTextField txtUser = new JTextField(15);
    private final JPasswordField txtPass = new JPasswordField(15);

    public LoginForm() {
        super("Đăng nhập - Quản lý sinh viên");
        JLabel title = new JLabel("QUẢN LÝ SINH VIÊN", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));

        JPanel form = new JPanel(new GridLayout(2, 2, 8, 10));
        form.add(new JLabel("Tên đăng nhập:")); form.add(txtUser);
        form.add(new JLabel("Mật khẩu:"));      form.add(txtPass);

        JButton btnLogin = new JButton("Đăng nhập"), btnExit = new JButton("Thoát");
        JPanel buttons = new JPanel();
        buttons.add(btnLogin); buttons.add(btnExit);

        JPanel root = new JPanel(new BorderLayout(10, 15));
        root.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        root.add(title, BorderLayout.NORTH);
        root.add(form, BorderLayout.CENTER);
        root.add(buttons, BorderLayout.SOUTH);
        setContentPane(root);

        btnLogin.addActionListener(e -> login());
        btnExit.addActionListener(e -> System.exit(0));
        getRootPane().setDefaultButton(btnLogin);   // nhấn Enter = Đăng nhập

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void login() {
        String sql = "SELECT TenNguoiDung FROM TaiKhoan WHERE TenDangNhap=? AND MatKhau=?";
        try (Connection cn = KetNoiSQLSever.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, txtUser.getText().trim());
            ps.setString(2, new String(txtPass.getPassword()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString(1) == null ? txtUser.getText().trim() : rs.getString(1);
                    new MainForm(name).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Không kết nối được CSDL:\n" + ex.getMessage()
                    + "\n\nKiểm tra lại thông tin trong DBConnection.java", "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
        }
    }
}
