package DAO;

import Connection.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public UserDao() throws SQLException {
        this.con = MyConnection.getConnection();
    }

    //getting the max row
    public int getMaxRow() {
        int row = 0;
        try {

            st = con.createStatement();
            rs = st.executeQuery("select max(uid) from user");
            while (rs.next()) {
                row = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    //email exist checking
    public boolean isEmailExists(String email) {
        try {
            ps = con.prepareStatement("select * from user where uemail =?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //phone number exist checking
    public boolean isPhoneNumberExists(String phone) {
        try {
            ps = con.prepareStatement("select * from user where uphone =?");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//inserting user data to backend

    public void insert(int id, String fname, String lname, String email, String phno, String pass, String seq, String ans, String add) {
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, fname);
            ps.setString(3, lname);
            ps.setString(4, email);
            ps.setString(5, phno);
            ps.setString(6, pass);
            ps.setString(7, seq);
            ps.setString(8, ans);
            ps.setString(9, add);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User Registration Successful");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//get user value

    public String[] getUserVal(int id) {
        String[] val = new String[9];
        try {
            ps = con.prepareStatement("select * from user where uid =?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                val[0] = rs.getString(1);
                val[1] = rs.getString(2);
                val[2] = rs.getString(3);
                val[3] = rs.getString(4);
                val[4] = rs.getString(5);
                val[5] = rs.getString(6);
                val[6] = rs.getString(7);
                val[7] = rs.getString(8);
                val[8] = rs.getString(9);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return val;
    }
//get user id
    public int getUserId(String email) {
        int id = 0;
        try {
            ps = con.prepareStatement("select uid from user where uemail =?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
//update user accont
    public void update(int id, String fname, String lname, String email, String phno, String pass, String seq, String ans, String add) {
        String sql = "update user set ufname =?, ulname=?, uemail =?, uphone =?, upassword =?,  usecqus =?, uans =?, uaddress =? where uid =?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, phno);
            ps.setString(5, pass);
            ps.setString(6, seq);
            ps.setString(7, ans);
            ps.setString(8, add);
            ps.setInt(9, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User Data updated successfully");

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//delete user account
    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this account?", "Delete Account?", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from user where uid =?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Account Deleted successfully");

                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //get user data
    public void getUsersValue(JTable table, String search)
    {
        String sql = "select * from user where concat(uid, ufname, ulname, uemail) like ? order by uid desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,"%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[9];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
