package Worker.database;

import java.sql.*;

public class DBInstance {
    Connection con;

    public Connection getCon() {
        return con;
    }

    Statement stmt;

    DBInstance() throws SQLException, ClassNotFoundException {
        String urls = "jdbc:mysql://localhost:3306/isc?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.jdbc.Driver");
        this.con = DriverManager.getConnection(urls,"root","root");
        this.stmt = con.createStatement();
    }

    public ResultSet query(String sql) throws SQLException {
        return this.stmt.executeQuery(sql);
    }

    void close() throws SQLException {
        this.con.close();
    }

    public PreparedStatement preparedQuery(String sql) throws SQLException {
        return this.con.prepareStatement(sql);

    }
}
