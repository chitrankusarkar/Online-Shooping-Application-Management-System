package Connection;

import java.sql.*;
import javax.swing.JOptionPane;

public class MyConnection {

    public static final String username = "root";
    public static final String password = "Riju#1234";
    public static final String url = "jdbc:mysql://localhost:3306/online_shopping";
    public static Connection con = null;
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ""+ex,"", JOptionPane.WARNING_MESSAGE);
        }
        return con;
    }
}
