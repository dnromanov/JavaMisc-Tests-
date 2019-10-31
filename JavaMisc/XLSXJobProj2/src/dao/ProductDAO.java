package dao;

import java.util.List;

import model.Product;

public interface ProductDAO {
	
	boolean removeProduct(Product product);
	boolean insertProduct(Product product);
	boolean updateProduct(Product product);
	
	List<Product> getAllProducts();
	
}
