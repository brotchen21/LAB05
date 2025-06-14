/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productDAO;

import java.sql.SQLException;
import java.util.List;
import model.Product;

/**
 *
 * @author Admin
 */
public class ProductService implements IProductService {
    private IProductDao ProductDao;
     
    public ProductService() {
        this.ProductDao = new ProductDao();
        
    }

    @Override
    public void addProduct(Product pro) throws SQLException {
        ProductDao.inserProdcut(pro);
    }

    @Override
    public Product getProductById(int id) {
        return ProductDao.selectProduct(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return ProductDao.selectAllProducts();
    }

    @Override
    public boolean removeProduct(int id) throws SQLException {
        return ProductDao.deleteProduct(id);
    }

    @Override
    public boolean modifyProduct(Product pro) throws SQLException {
        return ProductDao.updateProduct(pro);
    }
    
    
}
