package DAO;

import Connection.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CategoryDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public CategoryDao() throws SQLException {
        this.con = MyConnection.getConnection();
    }
    
    //getting maz row
     public int getMaxRow() {
        int row = 0;
        try {

            st = con.createStatement();
            rs = st.executeQuery("select max(cid) from category");
            while (rs.next()) {
                row = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
      public boolean isCategoryNameExists(String cname) {
        try {
            ps = con.prepareStatement("select * from category where cname =?");
            ps.setString(1, cname);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
      public void insert(int cid, String cname, String cdesc) {
        String sql = "insert into category values(?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setString(2, cname);
            ps.setString(3, cdesc);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Category added Successful");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public void getCategoriesValue(JTable table, String search)
    {
        String sql = "select * from category where concat(cid, cname) like ? order by cid desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,"%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[3];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public void update(int cid, String cname, String cdesc) {
        String sql = "update category set cname =?, cdesc=? where cid =?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cname);
            ps.setString(2, cdesc);
            ps.setInt(3, cid);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Category updated successfully");

            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
       public void delete(int cid) {
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Category?", "Delete Category?", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from category where cid =?");
                ps.setInt(1, cid);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Category Deleted successfully");

                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
      public boolean isCategoryIDExists(int cid) {
        try {
            ps = con.prepareStatement("select * from category where cid =?");
            ps.setInt(1, cid);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
