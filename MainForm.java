package qlsv.ui;

import javax.swing.*;
import java.awt.*;

/** Form 2: Giao diện chính, mỗi bảng là một tab. */
public class MainForm extends JFrame {
    public MainForm(String userName) {
        super("Phần mềm quản lý sinh viên");

        JTabbedPane tabs = new JTabbedPane();
        FromGiaoDien[] panels = {
            new SinhVienPanel(), new LopPanel(), new KhoaPanel(),
            new MonHocPanel(), new KetQuaPanel(), new TaiKhoanPanel()
        };
        String[] names = { "Sinh viên", "Lớp", "Khoa", "Môn học", "Kết quả", "Tài khoản" };
        for (int i = 0; i < panels.length; i++) tabs.addTab(names[i], panels[i]);
        // Mỗi lần chuyển tab thì tải lại dữ liệu (để combobox khóa ngoại luôn mới nhất)
        tabs.addChangeListener(e -> panels[tabs.getSelectedIndex()].reload());

        JLabel hello = new JLabel("  Xin chào, " + userName);
        hello.setFont(hello.getFont().deriveFont(Font.BOLD));
        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.addActionListener(e -> { new LoginForm().setVisible(true); dispose(); });
        JPanel top = new JPanel(new BorderLayout());
        top.add(hello, BorderLayout.WEST);
        top.add(btnLogout, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }
}
