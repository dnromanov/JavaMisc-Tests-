package test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import impl.ProductDBDAOimpl;
import model.Product;
import util.DBUtils;

public class ProductImplTest {

	public static void main(String[] args) {

//		ProductDBDAOimpl impl = new ProductDBDAOimpl();
//		Product product = impl.getAllProducts().get(0);
//		product.setProductCode("test");
//		impl.removeProduct(product);
		
		Date date = new Date();
		System.out.println(date);
		SimpleDateFormat formatter = new SimpleDateFormat("dd MM yy");
		String strDate = formatter.format(date);
		System.out.println(strDate);
		
		String dateString = "20 08 2019";
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date);
		
		Timestamp timestamp = new Timestamp(date.getTime());
		System.out.println(timestamp);
		

	}

}
