package qlsv.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KetNoiSQLSever {
    private static final String SERVER   = "127.0.0.1";
    private static final String PORT     = "1433";
    private static final String DATABASE = "QLSinhVien_HuyenTrang";
    private static final String USER     = "HuyenTrang";
    private static final String PASSWORD = "Lantrang3112@"; 

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://" + SERVER + ":" + PORT
                   + ";databaseName=" + DATABASE
                   + ";encrypt=true;trustServerCertificate=true";
        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}
