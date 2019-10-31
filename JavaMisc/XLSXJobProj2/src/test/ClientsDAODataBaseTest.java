package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.istack.Pool.Impl;

import helper.poi.ClientHelper;
import impl.ClientsDBDAOImpl;
import model.Client;
import util.DBUtils;

public class ClientsDAODataBaseTest {

	private static ClientsDBDAOImpl impl;
	static List<Client> clients = new ArrayList<>();

	@BeforeClass
	public static void init() {
		impl = new ClientsDBDAOImpl();

		// switch DB
		DBUtils.switchDB("db_test_name");
		// create TEST DB
		String create_test_db_sql = "CREATE DATABASE `" + DBUtils.DB_NAME+"`;";
		Connection conn = DBUtils.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(create_test_db_sql);

		
			// create same tables

			String create_table_clients_sql = "CREATE TABLE \r\n" + DBUtils.DB_NAME + ".clients\r\n" + "    (\r\n"
					+ "        ID INT NOT NULL AUTO_INCREMENT,\r\n" + "        FIRST_NAME VARCHAR(32) NOT NULL,\r\n"
					+ "        LAST_NAME VARCHAR(32) NOT NULL,\r\n" + "        GENDER VARCHAR(16) NOT NULL,\r\n"
					+ "        COUNTRY VARCHAR(32) NOT NULL,\r\n" + "        AGE INT NOT NULL,\r\n"
					+ "        REG_DATE DATE NOT NULL,\r\n"
					+ "        CREATED_TS TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,\r\n"
					+ "        UPDATED_TS TIMESTAMP NULL,\r\n" + "        PRIMARY KEY (ID)\r\n" + "    )\r\n"
					+ "    ENGINE=InnoDB DEFAULT CHARSET=utf8;";

			String create_table_clients_history_sql = "CREATE TABLE \r\n" + DBUtils.DB_NAME + ".clients_history\r\n"
					+ "    (\r\n" + "        ACTION_ID INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "        ACTION_CODE VARCHAR(8) NOT NULL,\r\n" + "        ID INT NOT NULL,\r\n"
					+ "        FIRST_NAME VARCHAR(32) NOT NULL,\r\n" + "        LAST_NAME VARCHAR(32) NOT NULL,\r\n"
					+ "        GENDER VARCHAR(16) NOT NULL,\r\n" + "        COUNTRY VARCHAR(32) NOT NULL,\r\n"
					+ "        AGE INT NOT NULL,\r\n" + "        REG_DATE DATE NOT NULL,\r\n"
					+ "        ACTION_TS TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,\r\n"
					+ "        PRIMARY KEY (ACTION_ID)\r\n" + "    )\r\n" + "    ENGINE=InnoDB DEFAULT CHARSET=utf8;";


			
			stmt.execute(create_table_clients_sql);
			stmt.execute(create_table_clients_history_sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBUtils.release(conn, stmt, null);
		
		//upload data
		ClientsDBDAOImpl impl = new ClientsDBDAOImpl();
		clients = new ClientHelper().loadClients("input/file_example_XLS_1000_NEW.xlsx");
		for (Client client : clients) {
			impl.insertOrUpdateClient(client);
		}
		clients = impl.getAllClients();
	}
	
	@AfterClass
	public static void destroy() {
	//DROP test db
	Connection conn = DBUtils.getConnection();
	String drop_test_db = "DROP DATABASE " + DBUtils.DB_NAME;
	Statement stmt = null;
	try {
		stmt = conn.createStatement();
		stmt.execute(drop_test_db);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//switch db back

		DBUtils.switchDB("db_name");
		DBUtils.release(conn, stmt, null);
	}


	@Test
	public void getClientByIdNotExisting() {
		Client client = impl.getClientById(new Client(-100));
		assertNull(client);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getClientByIdIfNull() {
		Client client = impl.getClientById(null);
	}

	@Test
	public void getClientByIdExisting() {
		Client client = impl.getClientById(clients.get(0));
		assertNotNull(client);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void removeClientIfClientIsNull() {
		boolean result = impl.removeClient(null);
	}

	@Test
	public void removeClientIfClientExists() {
		Client clientToRemove = clients.get(clients.size() - 1);
		boolean result = impl.removeClient(clientToRemove);
		assertTrue(result);
	}

	@Test
	public void removeClientIfClientNotExists() {
		Client clientToRemove = clients.get(10);
		clientToRemove.setId(clients.get(0).getId() - 1);
		boolean result = impl.removeClient(clientToRemove);
		assertFalse(result);
	}

	@Test
	public void updateClientIfClientExists() {
		Client clientToUpdate = clients.get(0);
		boolean result = impl.updateClient(clientToUpdate);
		assertTrue(result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void updateClientIfClientIsNull() {
		boolean result = impl.updateClient(null);
	}

	@Test
	public void updateClientIfClientNotExists() {
		Client clientToUpdate = clients.get(0);
		clientToUpdate.setId(clientToUpdate.getId() - 1);
		boolean result = impl.updateClient(clientToUpdate);
		assertFalse("Cant update client", result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void insertClientIfClientIsNull() {
		boolean result = impl.insertClient(null);
	}

	@Test
	public void insertClientIfClientExists() {
		boolean result = impl.insertClient(clients.get(0));
		assertTrue(result);
	}

	@Test(expected = java.lang.AssertionError.class)
	public void insertClientIfClientInvalid() {
		Client client = impl.getClientById(clients.get(5));
		client.setFirstName(null);
		boolean result = impl.insertClient(client);
		assertTrue(result);
	}
	
	@Test
	public void restoreClientIfClientWasDeleted() {
		Client clientToDelete = impl.getClientById(clients.get(6));
		impl.removeClient(clientToDelete);
		boolean result = impl.restoreClient(clientToDelete);
		assertTrue(result);
	}
	
	@Test
	public void restoreClientIfClientWasNotDeleted() {
		Client clientToDelete = impl.getClientById(clients.get(6));
		boolean result = impl.restoreClient(clientToDelete);
		assertFalse(result);
	}
	
	@Test (expected = NullPointerException.class)
	public void restoreClientIfClientIsNull() {
		boolean result = impl.restoreClient(null);
	}
	
	@Test
	public void restoreClientIfClientIsNotInHistory() {
		boolean result = impl.restoreClient(new Client(0));
		assertFalse(result);
	}
	
	@Test
	public void restoreClientIfClientWithNullId() {
		boolean result = impl.restoreClient(new Client());
		assertFalse(result);
	}
	
	@Test
	public void getAllClientsByTimestampWithValidValues() {
		Timestamp createdFrom = clients.get(0).getCreatedTs();
		Timestamp createdTo = clients.get(clients.size()-1).getCreatedTs();
//		List<Client> clients2 = impl.getAllClients(Timestamp.valueOf("2019-08-14 00:00:00"), Timestamp.valueOf("2019-08-21 00:00:00"));
		List<Client> clients2 = impl.getAllClients(createdFrom, createdTo);
		assertNotEquals(0, clients.size());
	}
	
	@Test
	public void getAllClientsByTimestampWithINValidValues() {
		List<Client> clients = impl.getAllClients(Timestamp.valueOf("2019-08-10 00:00:00"), Timestamp.valueOf("2019-08-11 00:00:00"));
		assertEquals(0, clients.size());
	}
}
