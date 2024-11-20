package DAO;

import Connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PurchaseDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public PurchaseDao() throws SQLException {
        this.con = MyConnection.getConnection();

    }

    public int getMaxRow() {
        int row = 0;
        try {

            st = con.createStatement();
            rs = st.executeQuery("select max(id) from purchase");
            while (rs.next()) {
                row = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public String[] getUsersValue(String email) {
        String[] value = new String[5];
        String sql = "select uid, ufname, ulname, uphone, uaddress from user where uemail = '" + email + "'";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next())
            {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    public void insert(int id, int uid, String ufname, String ulname, String uphone, int pid, String proname, int qty, double price, double total, String pdate, String uaddress)
    {
        String sql = "insert into purchase values(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, id);
            ps.setInt(2, uid);
            ps.setString(3, ufname);
            ps.setString(4, ulname);
            ps.setString(5, uphone);
            ps.setInt(6, pid);
            ps.setString(7, proname);
            ps.setInt(8, qty);
            ps.setDouble(9, price);
            ps.setDouble(10, total);
            ps.setString(11, pdate);
            ps.setString(12, uaddress);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getQty(int pid)
    {
        int qty = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select pqty from product where pid = " + pid + "");
            if(rs.next())
            {
                qty = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qty;
    }
    public void updateQty(int pid, int qty)
    {
        
        String sql = "update product set pqty = ? where pid = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, qty);
            ps.setInt(2, pid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getProductsValue(JTable table, String search, int uid) {
        String sql = "select * from purchase where concat(id,pid,product_name) like ? and uid =? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, uid);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(6);
                row[2] = rs.getString(7);
                row[3] = rs.getInt(8);
                row[4] = rs.getDouble(9);
                row[5] = rs.getDouble(10);
                row[6] = rs.getString(11);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void transaction(JTable table, String search) {
        //String sql = "select * from purchase where concat(id,pid,uid) like ? order by id desc";
        String sql = "select id, uid, pid, qty, price, total, p_date from purchase";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(6);
                row[3] = rs.getInt(8);
                row[4] = rs.getDouble(9);
                row[5] = rs.getDouble(10);
                row[6] = rs.getString(11);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

