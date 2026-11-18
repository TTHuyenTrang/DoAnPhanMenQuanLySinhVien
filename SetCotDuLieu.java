package qlsv.ui;

/** Mô tả 1 cột của bảng: nhãn hiển thị, tên cột SQL, là khóa chính?, bắt buộc?, dạng combobox? */
public class SetCotDuLieu {
    final String label, column;
    boolean key, required;
    String comboSql;   // nếu có: combobox lấy dữ liệu từ câu SQL này (khóa ngoại)
    String[] items;    // nếu có: combobox với danh sách cố định

    public SetCotDuLieu(String label, String column) {
        this.label = label;
        this.column = column;
    }

    public SetCotDuLieu key()               { key = true; required = true; return this; }
    public SetCotDuLieu required()          { required = true; return this; }
    public SetCotDuLieu sql(String sql)     { comboSql = sql; return this; }
    public SetCotDuLieu items(String... i)  { items = i; return this; }
}
