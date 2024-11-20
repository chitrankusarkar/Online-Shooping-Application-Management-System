package DAO;

import Connection.MyConnection;
import User.UserForgotPassword;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UserForgotPasswordDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public UserForgotPasswordDao() throws SQLException {
        this.con = MyConnection.getConnection();
    }

    public boolean isEmailExists(String email) {
        try {
            ps = con.prepareStatement("select * from user where uemail =?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                UserForgotPassword.jTextField2.setText(rs.getString(7));
            } else {
                JOptionPane.showMessageDialog(null, "Email address does not exist");

            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean getAns(String email, String newAns) {
        try {
            ps = con.prepareStatement("select * from user where uemail =?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String oldAns = rs.getString(8);
                if (newAns.equals(oldAns)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Security answer didn't match");
                    return false;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void setNewPass(String email, String pass) {
        String sql = "update user set upassword =? where uemail =?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setString(2, email);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Password updated successfully");

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
