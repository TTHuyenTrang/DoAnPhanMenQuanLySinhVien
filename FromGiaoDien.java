package qlsv.ui;

import qlsv.db.KetNoiSQLSever;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Panel dùng chung cho mọi bảng: form nhập + bảng dữ liệu + Thêm / Sửa / Xóa / Tìm kiếm.
 * Mỗi bảng chỉ cần khai báo danh sách Field (xem KhoaPanel, LopPanel, ...).
 */
public class FromGiaoDien extends JPanel {
    private final String table;
    private final SetCotDuLieu[] fields;
    private final Map<String, JComponent> inputs = new LinkedHashMap<>();
    private final DefaultTableModel model;
    private final JTable grid;
    private final JTextField txtSearch = new JTextField(15);

    public FromGiaoDien(String title, String table, SetCotDuLieu... fields) {
        this.table = table;
        this.fields = fields;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Form nhập liệu (2 cột) ----
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder(title));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < fields.length; i++) {
            SetCotDuLieu f = fields[i];
            JComponent in;
            if (f.comboSql != null || f.items != null) {
                JComboBox<String> cb = new JComboBox<>(f.items == null ? new String[0] : f.items);
                in = cb;
            } else {
                in = new JTextField(18);
            }
            inputs.put(f.column, in);
            c.gridx = (i % 2) * 2; c.gridy = i / 2; c.weightx = 0;
            form.add(new JLabel(f.label + (f.required ? " *" : "")), c);
            c.gridx++; c.weightx = 1;
            form.add(in, c);
        }

        // ---- Hàng nút + tìm kiếm ----
        JButton btnAdd = new JButton("Thêm"), btnEdit = new JButton("Sửa"),
                btnDelete = new JButton("Xóa"), btnClear = new JButton("Làm mới"),
                btnSearch = new JButton("Tìm");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnAdd); buttons.add(btnEdit); buttons.add(btnDelete); buttons.add(btnClear);
        buttons.add(Box.createHorizontalStrut(30));
        buttons.add(new JLabel("Tìm kiếm:")); buttons.add(txtSearch); buttons.add(btnSearch);

        JPanel north = new JPanel(new BorderLayout());
        north.add(form, BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        // ---- Bảng dữ liệu ----
        model = new DefaultTableModel(Arrays.stream(fields).map(f -> f.label).toArray(), 0) {
            @Override public boolean isCellEditable(int r, int col) { return false; }
        };
        grid = new JTable(model);
        grid.setRowHeight(24);
        grid.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(grid), BorderLayout.CENTER);

        // ---- Sự kiện ----
        grid.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && grid.getSelectedRow() >= 0) fillForm(grid.getSelectedRow());
        });
        btnAdd.addActionListener(e -> onAdd());
        btnEdit.addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> onDelete());
        btnClear.addActionListener(e -> { txtSearch.setText(""); clearForm(); load(); });
        btnSearch.addActionListener(e -> load());
        txtSearch.addActionListener(e -> load());

        reload();
    }

    // ================= Hook cho lớp con =================
    /** Gọi trước khi Thêm/Sửa (lớp con có thể ghi đè, ví dụ tự tính điểm). */
    protected void beforeSave() { }

    protected String get(String column) {
        JComponent in = inputs.get(column);
        if (in instanceof JComboBox) {
            Object o = ((JComboBox<?>) in).getSelectedItem();
            return o == null ? "" : o.toString().trim();
        }
        return ((JTextField) in).getText().trim();
    }

    protected void set(String column, String value) {
        JComponent in = inputs.get(column);
        if (in instanceof JComboBox) ((JComboBox<?>) in).setSelectedItem(value);
        else ((JTextField) in).setText(value);
    }

    // ================= Tải dữ liệu =================
    /** Tải lại danh sách combobox (khóa ngoại) và bảng. Gọi mỗi khi mở tab. */
    public void reload() {
        for (SetCotDuLieu f : fields) {
            if (f.comboSql == null) continue;
            @SuppressWarnings("unchecked")
            JComboBox<String> cb = (JComboBox<String>) inputs.get(f.column);
            String old = get(f.column);
            cb.removeAllItems();
            try (Connection cn = KetNoiSQLSever.getConnection();
                 Statement st = cn.createStatement();
                 ResultSet rs = st.executeQuery(f.comboSql)) {
                while (rs.next()) cb.addItem(rs.getString(1).trim());
            } catch (SQLException ex) { showError(ex); }
            cb.setSelectedItem(old);
        }
        load();
    }

    private void load() {
        String cols = Arrays.stream(fields).map(f -> f.column).collect(Collectors.joining(", "));
        String keys = Arrays.stream(fields).filter(f -> f.key).map(f -> f.column).collect(Collectors.joining(", "));
        String sql = "SELECT " + cols + " FROM " + table;
        String kw = txtSearch.getText().trim();
        if (!kw.isEmpty()) {
            sql += " WHERE CONCAT(" + Arrays.stream(fields).map(f -> f.column)
                    .collect(Collectors.joining(", ' ', ")) + ") LIKE ?";
        }
        sql += " ORDER BY " + keys;

        model.setRowCount(0);
        try (Connection cn = KetNoiSQLSever.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            if (!kw.isEmpty()) ps.setString(1, "%" + kw + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        String v = rs.getString(i + 1);
                        row[i] = v == null ? "" : v.trim();   // char(10) bị đệm khoảng trắng -> trim
                    }
                    model.addRow(row);
                }
            }
        } catch (SQLException ex) { showError(ex); }
    }

    // ================= Form =================
    private void fillForm(int row) {
        for (int i = 0; i < fields.length; i++) {
            set(fields[i].column, String.valueOf(model.getValueAt(row, i)));
            if (fields[i].key) inputs.get(fields[i].column).setEnabled(false); // không cho sửa khóa chính
        }
    }

    private void clearForm() {
        grid.clearSelection();
        for (SetCotDuLieu f : fields) {
            JComponent in = inputs.get(f.column);
            in.setEnabled(true);
            if (in instanceof JComboBox) {
                JComboBox<?> cb = (JComboBox<?>) in;
                cb.setSelectedIndex(cb.getItemCount() > 0 ? 0 : -1);
            } else ((JTextField) in).setText("");
        }
    }

    private boolean validateForm() {
        for (SetCotDuLieu f : fields) {
            if (f.required && get(f.column).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập: " + f.label);
                inputs.get(f.column).requestFocus();
                return false;
            }
        }
        return true;
    }

    // ================= Thêm / Sửa / Xóa =================
    private void onAdd() {
        beforeSave();
        if (!validateForm()) return;
        String cols = Arrays.stream(fields).map(f -> f.column).collect(Collectors.joining(", "));
        String sql = "INSERT INTO " + table + "(" + cols + ") VALUES(" + "?,".repeat(fields.length - 1) + "?)";
        List<String> params = Arrays.stream(fields).map(f -> get(f.column)).collect(Collectors.toList());
        if (execute(sql, params)) { JOptionPane.showMessageDialog(this, "Thêm thành công!"); clearForm(); load(); }
    }

    private void onEdit() {
        if (grid.getSelectedRow() < 0) { JOptionPane.showMessageDialog(this, "Hãy chọn một dòng trong bảng để sửa."); return; }
        beforeSave();
        if (!validateForm()) return;
        String set = Arrays.stream(fields).filter(f -> !f.key).map(f -> f.column + "=?").collect(Collectors.joining(", "));
        String where = Arrays.stream(fields).filter(f -> f.key).map(f -> f.column + "=?").collect(Collectors.joining(" AND "));
        List<String> params = new ArrayList<>();
        for (SetCotDuLieu f : fields) if (!f.key) params.add(get(f.column));
        for (SetCotDuLieu f : fields) if (f.key) params.add(get(f.column));
        if (execute("UPDATE " + table + " SET " + set + " WHERE " + where, params)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!"); clearForm(); load();
        }
    }

    private void onDelete() {
        if (grid.getSelectedRow() < 0) { JOptionPane.showMessageDialog(this, "Hãy chọn một dòng trong bảng để xóa."); return; }
        if (JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa dòng này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        String where = Arrays.stream(fields).filter(f -> f.key).map(f -> f.column + "=?").collect(Collectors.joining(" AND "));
        List<String> params = Arrays.stream(fields).filter(f -> f.key).map(f -> get(f.column)).collect(Collectors.toList());
        if (execute("DELETE FROM " + table + " WHERE " + where, params)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!"); clearForm(); load();
        }
    }

    /** Chạy câu lệnh INSERT/UPDATE/DELETE. Chuỗi rỗng được lưu thành NULL. */
    private boolean execute(String sql, List<String> params) {
        try (Connection cn = KetNoiSQLSever.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                String v = params.get(i);
                ps.setString(i + 1, v.isEmpty() ? null : v);
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { showError(ex); return false; }
    }

    private void showError(SQLException ex) {
        String msg;
        switch (ex.getErrorCode()) {
            case 547:  msg = "Vi phạm khóa ngoại: dữ liệu đang được bảng khác sử dụng, hoặc mã tham chiếu không tồn tại."; break;
            case 2627:
            case 2601: msg = "Mã (khóa chính) đã tồn tại, không thể thêm trùng."; break;
            case 241: case 242: case 245: case 8114: case 8115:
                       msg = "Sai định dạng dữ liệu (ngày: yyyy-MM-dd, điểm: số thập phân, số tiết/tín chỉ: số nguyên)."; break;
            default:   msg = ex.getMessage();
        }
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
