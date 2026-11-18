package qlsv.ui;

public class SetCotDuLieu {
    final String label, column;
    boolean key, required;
    String comboSql;   
    String[] items;    
    public SetCotDuLieu(String label, String column) {
        this.label = label;
        this.column = column;
    }

    public SetCotDuLieu key()               { key = true; required = true; return this; }
    public SetCotDuLieu required()          { required = true; return this; }
    public SetCotDuLieu sql(String sql)     { comboSql = sql; return this; }
    public SetCotDuLieu items(String... i)  { items = i; return this; }
}
