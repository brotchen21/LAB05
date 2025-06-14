/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productDAO;

import model.Product;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IProductDao {
    public void inserProdcut(Product pro) throws SQLException;
    
    public Product selectProduct(int id);
    
    public List<Product> selectAllProducts();
    
    public boolean deleteProduct(int id) throws SQLException;
    
    public boolean updateProduct (Product pro) throws SQLException;
}
