package DAO;

import Connection.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProductDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public ProductDao() throws SQLException {
        this.con = MyConnection.getConnection();

    }

    public int getMaxRow() {
        int row = 0;
        try {

            st = con.createStatement();
            rs = st.executeQuery("select max(pid) from product");
            while (rs.next()) {
                row = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public int countCategories() {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from category");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public String[] getCat() {
        String[] categories = new String[countCategories()];
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from category");
            int i = 0;
            while (rs.next()) {
                categories[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public boolean isProductIDExists(int pid) {
        try {
            ps = con.prepareStatement("select * from product where pid =?");
            ps.setInt(1, pid);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isProductCategoryExists(String pro, String cat) {
        try {
            ps = con.prepareStatement("select * from product where pname =? and cname = ?");
            ps.setString(1, pro);
            ps.setString(2, cat);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insert(int pid, String pname, String cname, int pqty, double pprice) {
        String sql = "insert into product values(?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pid);
            ps.setString(2, pname);
            ps.setString(3, cname);
            ps.setInt(4, pqty);
            ps.setDouble(5, pprice);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Product added Successful");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getProductsValue(JTable table, String search) {
        String sql = "select * from product where concat(pid, pname, cname) like ? order by pid desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getDouble(5);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int pid) {
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this Product?", "Delete Product?", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from product where pid =?");
                ps.setInt(1, pid);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Product Deleted successfully");

                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isProductNameExists(String pname) {
        try {
            ps = con.prepareStatement("select * from product where pname =?");
            ps.setString(1, pname);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void update(int pid, String pname, String cname, int pqty, double pprice) {
        String sql = "update product set pname =?, cname=?, pqty=?, pprice=? where pid =?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pname);
            ps.setString(2, cname);
            ps.setInt(3, pqty);
            ps.setDouble(4, pprice);
            ps.setInt(5, pid);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Product updated successfully");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
