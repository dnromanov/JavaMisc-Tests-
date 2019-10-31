package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDAO;
import model.Product;
import util.DBUtils;

public class ProductDBDAOimpl implements ProductDAO {

//	public Product getProductByCode(Product product) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = DBUtils.getConnection();
////			DBUtils.switchDB("db_retailer_name");
//			DBUtils.switchTableDB("db_table_products");
//			pstmt = conn.prepareStatement(
//					"SELECT * FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME + " WHERE PRODUCTCODE = ?");
//			pstmt.setString(1, product.getProductCode());
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				product.setProductName(rs.getString("ProductName"));
//				product.setProductLine(rs.getString("ProductLine"));
//				product.setProductScale(rs.getString("ProductScale"));
//				product.setProductVendor(rs.getString("ProductVendor"));
//				product.setProductDescription(rs.getString("ProductDescription"));
//				product.setQuantityInStock(rs.getInt("QuantityInStock"));
//				product.setBuyPrice(rs.getDouble("BuyPrice"));
//				product.setMSRP(rs.getDouble("MSRP"));
//			} else {
//				System.out.println("CAN'T FIND PRODUCT WITH PRODUCT CODE = " + product.getProductCode());
//				product = null;
//			}
//			DBUtils.release(conn, null, pstmt);
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return product;
//	}

	public List<Product> getProductByProductLine(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Product> products = new ArrayList<>();

		try {
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_retailer_name");
			DBUtils.switchTableDB("db_table_products");
			pstmt = conn.prepareStatement(
					"SELECT * FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME + " WHERE PRODUCTLINE = ?");
			pstmt.setString(1, product.getProductLine());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product1 = new Product();
				product1.setProductName(rs.getString("ProductName"));
				product1.setProductCode(rs.getString("ProductCode"));
				product1.setProductScale(rs.getString("ProductScale"));
				product1.setProductVendor(rs.getString("ProductVendor"));
				product1.setProductDescription(rs.getString("ProductDescription"));
				product1.setQuantityInStock(rs.getInt("QuantityInStock"));
				product1.setBuyPrice(rs.getDouble("BuyPrice"));
				product1.setMSRP(rs.getDouble("MSRP"));
				products.add(product1);
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public boolean removeProduct(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_retailer_name");
			DBUtils.switchTableDB("db_table_products");
			pstmt = conn.prepareStatement(
					"DELETE FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME + " WHERE PRODUCTCODE = ?");
			pstmt.setString(1, product.getProductCode());
			int result = pstmt.executeUpdate();

			if (result != 0) {
				return true;
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean insertProduct(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_retailer_name");
			DBUtils.switchTableDB("db_table_products");
			pstmt = conn.prepareStatement("INSERT INTO " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME
					+ "(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, product.getProductCode());
			pstmt.setString(2, product.getProductName());
			pstmt.setString(3, product.getProductLine());
			pstmt.setString(4, product.getProductScale());
			pstmt.setString(5, product.getProductVendor());
			pstmt.setString(6, product.getProductDescription());
			pstmt.setInt(7, product.getQuantityInStock());
			pstmt.setDouble(8, product.getBuyPrice());
			pstmt.setDouble(9, product.getMSRP());
			
			int result = pstmt.executeUpdate();
			if(result != 0) {
				System.out.println("A product has been inserted");
				return true;
			}
			
			DBUtils.release(conn, null, pstmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		

		try {
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_retailer_name");
			DBUtils.switchTableDB("db_table_products");
			pstmt = conn.prepareStatement("UPDATE " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME
					+ " SET productName = ?, productLine = ?, productScale = ?, productVendor = ?, productDescription = ?, quantityInStock = ?, buyPrice = ?, MSRP = ?"
					+ "WHERE productCode = ?");

			pstmt.setString(1, product.getProductName());
			pstmt.setString(2, product.getProductLine());
			pstmt.setString(3, product.getProductScale());
			pstmt.setString(4, product.getProductVendor());
			pstmt.setString(5, product.getProductDescription());
			pstmt.setInt(6, product.getQuantityInStock());
			pstmt.setDouble(7, product.getBuyPrice());
			pstmt.setDouble(8, product.getMSRP());
			pstmt.setString(9, product.getProductCode());
			
			int result = pstmt.executeUpdate();
			if (result != 0) {
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Product> getAllProducts() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Product> products = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_retailer_name");
			DBUtils.switchTableDB("db_table_products");
			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setProductCode(rs.getString("ProductCode"));
				product.setProductName(rs.getString("ProductName"));
				product.setProductLine(rs.getString("ProductLine"));
				product.setProductScale(rs.getString("ProductScale"));
				product.setProductVendor(rs.getString("ProductVendor"));
				product.setProductDescription(rs.getString("ProductDescription"));
				product.setQuantityInStock(rs.getInt("QuantityInStock"));
				product.setBuyPrice(rs.getDouble("BuyPrice"));
				product.setMSRP(rs.getDouble("MSRP"));
				products.add(product);
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return products;
	}

}
