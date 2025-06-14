/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productDAO;

import dao.DBConnection;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import model.Product;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import userDAO.UserDAO;
import static userDAO.UserDAO.checkLogin;

/**
 *
 * @author Admin
 */
public class ProductDao implements IProductDao{
    
    private static final String INSERT_PRODUCT = "INSERT INTO Product (name, price, description, stock, import_date) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Product WHERE id = ?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product";
    private static final String DELETE_PRODUCT = "DELETE FROM Product WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, import_date = ? WHERE id = ?";

    
    @Override
    public void inserProdcut(Product pro) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(INSERT_PRODUCT);
            ps.setString(1, pro.getName());
            ps.setBigDecimal(2, pro.getPrice());
            ps.setString(3, pro.getDescription());
            ps.setInt(4, pro.getStock());
            ps.setTimestamp(5, Timestamp.valueOf(pro.getImportDate()));
            
            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product selectProduct(int id) {
        Product pro = null;
        try(Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String name = rs.getNString("name");
                BigDecimal price = rs.getBigDecimal("price");
                String desc = rs.getNString("description");
                int stock = rs.getInt("stock");
                Timestamp its = rs.getTimestamp("import_date");
                LocalDateTime importDate = its.toLocalDateTime();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pro;
    }

    @Override
    public List<Product> selectAllProducts() {
        List<Product> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(SELECT_ALL_PRODUCTS);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getNString("name");
                BigDecimal price = rs.getBigDecimal("price");
                String desc = rs.getNString("description");
                int stock = rs.getInt("stock");
                Timestamp its = rs.getTimestamp("import_date");
                LocalDateTime importDate = its.toLocalDateTime();
                list.add(new Product(id, name, price, desc, stock, importDate));
            }
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteProduct(int id) throws SQLException {
        boolean delete = false;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DELETE_PRODUCT);
            ps.setInt(1, id);
            delete = ps.executeUpdate() > 0;
        }catch(SQLException ex) {
            System.out.println(ex);
        }
        return delete;
    }

    @Override
    public boolean updateProduct(Product pro) throws SQLException {
        boolean updated;
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_PRODUCT)) {
            ps.setString(1, pro.getName());
            ps.setBigDecimal(2, pro.getPrice());
            ps.setString(3, pro.getDescription());
            ps.setInt(4, pro.getStock());
            ps.setTimestamp(5, Timestamp.valueOf(pro.getImportDate()));
            updated = ps.executeUpdate() > 0;
        }
        return updated;
    }
    
}
