package impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.ClientsDAO;
import model.Client;
import util.DBUtils;

public class ClientsDBDAOImplOneConnection implements ClientsDAO {
	private static String DB_NAME = "NEW_DB";
	private static String TABLE_NAME = "CLIENTS";
	private static String COLUMNS = "ID, FIRST_NAME, LAST_NAME, GENDER, COUNTRY, AGE, REG_DATE, CREATED_TS, UPDATED_TS";
	
	//getClientById()
	private static String SELECT_BY_ID_SQL = "SELECT " + COLUMNS 
				+ " FROM " + DB_NAME + "." + TABLE_NAME + " WHERE ID = ?";
	
	private Client mapClient(Client client, ResultSet rs) throws SQLException {
		client.setFirstName(rs.getString("FIRST_NAME"));
		client.setLastName(rs.getString("LAST_NAME"));
		client.setGender(rs.getString("GENDER"));
		client.setCountry(rs.getString("COUNTRY"));
		client.setAge(rs.getInt("AGE"));
		client.setRegDate(rs.getDate("REG_DATE"));
		client.setCreatedTs(rs.getTimestamp("CREATED_TS"));
		client.setUpdatedTs(rs.getTimestamp("UPDATED_TS"));
		return client;
	}
	
	
	@Override
	public Client getClientById(Client client) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement("SELECT " + COLUMNS 
					+ " FROM " + DB_NAME + "." + TABLE_NAME + " WHERE ID = ?");
			pstmt.setInt(1, client.getId());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				mapClient(client, rs);
			} else {
				System.out.println("CAN'T FIND CLIENT WITH ID = " + client.getId());
				client = null;
			}
			DBUtils.release(conn, null, pstmt);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}

	@Override
	public boolean removeClient(Client client) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtils.getConnection();
			if(removeClientSqlStmt(pstmt,  conn,  client)) {
				return true;
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return false;
	}
	
	public boolean removeClientSqlStmt(PreparedStatement pstmt, Connection conn, Client client) throws SQLException {
		pstmt = conn.prepareStatement("DELETE FROM NEW_DB.Clients WHERE ID = ?");
		pstmt.setInt(1, client.getId());
		
		int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE
		if(rowCount != 0) {
//			System.out.println("CLIENT DELETED");
			return true;
		}
		else return false;
	}

	@Override
	public boolean insertClient(Client client) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtils.getConnection();
			if(insertClientSqlStmt(pstmt,  conn,  client)) {
				return true;
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return false;
	}
	
	public boolean insertClientSqlStmt(PreparedStatement pstmt, Connection conn, Client client) throws SQLException {
		pstmt = conn.prepareStatement("INSERT INTO NEW_DB.Clients (FIRST_NAME, LAST_NAME, GENDER, COUNTRY, AGE, REG_DATE) " + 
				"VALUES (?, ?, ?, ?, ?, ?);");
		pstmt.setString(1, client.getFirstName());
		pstmt.setString(2, client.getLastName());
		pstmt.setString(3, client.getGender());
		pstmt.setString(4, client.getCountry());
		pstmt.setInt(5, client.getAge());
		pstmt.setDate(6, new java.sql.Date(client.getRegDate().getTime()));
		
		int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE
		
		if(rowCount != 0) {
//			System.out.println("CLIENT INSERTED");
			return true;
		}
		else return false;
	}

	@Override
	public boolean updateClient(Client client) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtils.getConnection();
			if(updateClientSqlStmt(pstmt,  conn,  client)) {
				return true;
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return false;
	}
	
	public boolean updateClientSqlStmt(PreparedStatement pstmt, Connection conn, Client client) throws SQLException {
		pstmt = conn.prepareStatement("UPDATE NEW_DB.Clients SET FIRST_NAME = ?, LAST_NAME = ?, GENDER = ?, COUNTRY = ?, AGE = ?, REG_DATE = ?, UPDATED_TS = ?" + 
				"WHERE ID = ?");
		pstmt.setString(1, client.getFirstName());
		pstmt.setString(2, client.getLastName());
		pstmt.setString(3, client.getGender());
		pstmt.setString(4, client.getCountry());
		pstmt.setInt(5, client.getAge());
		pstmt.setDate(6, new java.sql.Date(client.getRegDate().getTime()));
		pstmt.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
		pstmt.setInt(8, client.getId());
		
		int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE
		
		if(rowCount != 0) {
//			System.out.println("CLIENT UPDATED");
			return true;
		}
		else return false;
	}

	@Override
	public List<Client> getAllClients() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Client> clients = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			clients = getAllClientsSqlStmt(pstmt,  conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return clients;
	}
	
	public List<Client> getAllClientsSqlStmt(PreparedStatement pstmt, Connection conn) throws SQLException {
		List<Client> clients = new ArrayList<>();
		pstmt = conn.prepareStatement("SELECT * FROM NEW_DB.Clients");
		
		ResultSet rs = pstmt.executeQuery(); // INSERT, UPDATE or DELETE
		int counter = 0;
		while(rs.next()) {
			Client client = new Client();
			mapClient(client, rs);
			client.setId(rs.getInt("ID"));
			clients.add(client);
			counter++;
		}
		System.out.println(counter + " clients selected");
		return clients;
	}
	
	public List<Client> getAllClients(Timestamp createdFrom, Timestamp createdTo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Client> clients = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			clients = getAllClientsFromToSqlStmt(pstmt, conn, clients, createdFrom, createdTo);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return clients;
	}
	
	public List<Client> getAllClientsFromToSqlStmt(PreparedStatement pstmt, Connection conn, List<Client> clients, Timestamp createdFrom, Timestamp createdTo) throws SQLException {
		List<Client> clientsChoosen = new ArrayList<>();
		pstmt = conn.prepareStatement("SELECT * FROM NEW_DB.Clients WHERE CREATED_TS BETWEEN " + createdFrom + " AND " + createdTo);
		
		ResultSet rs = pstmt.executeQuery(); // INSERT, UPDATE or DELETE
		int counter = 0;
		while(rs.next()) {
			Client client = new Client();
			mapClient(client, rs);
			client.setId(rs.getInt("ID"));
			clients.add(client);
			counter++;
		}
		System.out.println(counter + " clients selected");
		return clientsChoosen;
	}
}
