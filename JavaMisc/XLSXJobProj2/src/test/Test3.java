package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import helper.poi.ClientHelper;
import impl.ClientsDBDAOImplOneConnection;
import model.Client;
import util.DBUtils;

public class Test3 {

	
	public static void main(String[] args) {
		
		ClientsDBDAOImplOneConnection impl = new ClientsDBDAOImplOneConnection();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int counter = 0;
		List<Client> clients = new ArrayList<>();
		conn = DBUtils.getConnection();
		
		//insert all clients via one connection
		counter = 0;
		clients = new ClientHelper().loadClients("input/file_example_XLS_1000_NEW.xlsx");
		for (Client client : clients) {
			try {
				impl.insertClientSqlStmt(pstmt,  conn,  client);
				counter++;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(counter + " clients inserted");
		
		//select all clients from DB via one connection
		clients = new ArrayList<>();
		try {
			clients = impl.getAllClientsSqlStmt(pstmt,  conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//update all clients via one connection
		counter = 0;
		for (Client client : clients) {
			try {
				if(client.getCountry().equals("Great Britain")) {
					client.setCountry("Great Britain1");
					impl.updateClientSqlStmt(pstmt, conn, client);
					counter++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(counter + " clients updated");
		
		//remove all clients via one connection
		counter = 0;
		for (Client client : clients) {
			try {
				if(client.getCreatedTs().before(new Date())) {
					impl.removeClientSqlStmt(pstmt, conn, client);
					counter++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(counter + " clients deleted");
		
		DBUtils.release(conn, null, pstmt);
		

		
		
		
		
	}

}
