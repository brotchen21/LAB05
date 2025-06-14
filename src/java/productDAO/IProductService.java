/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productDAO;

import model.Product;
import java.sql.*;
import java.util.List;
/**
 *
 * @author Admin
 */
public interface IProductService {
    void addProduct (Product pro) throws SQLException;
    
    Product getProductById (int id);
    
    List<Product> getAllProducts();
    
    boolean removeProduct(int id) throws SQLException;
    
    boolean modifyProduct(Product pro) throws SQLException;
}
