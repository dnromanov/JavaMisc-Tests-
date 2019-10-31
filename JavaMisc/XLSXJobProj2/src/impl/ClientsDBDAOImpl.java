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
import model.ClientHistory;
import util.DBUtils;

public class ClientsDBDAOImpl implements ClientsDAO {

//	private String DB_NAME = "" + DBUtils.DB_NAME + "";
	private String TABLE_NAME = "CLIENTS";
	private String COLUMNS = "ID, FIRST_NAME, LAST_NAME, GENDER, COUNTRY, AGE, REG_DATE, CREATED_TS, UPDATED_TS";

	// getClientById()
	private String SELECT_BY_ID_SQL = "SELECT " + COLUMNS + " FROM " + DBUtils.DB_NAME + "." + TABLE_NAME
			+ " WHERE ID = ?";

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
//			DBUtils.switchDB("db_name");
			DBUtils.switchTableDB("db_table_clients");
			pstmt = conn.prepareStatement(
					"SELECT " + COLUMNS + " FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME + " WHERE ID = ?");
			pstmt.setInt(1, client.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
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
			Client delClient = getClientById(client);
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_name");
			DBUtils.switchTableDB("db_table_clients");
			pstmt = conn
					.prepareStatement("DELETE FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME + " WHERE ID = ?");
			pstmt.setInt(1, client.getId());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			DBUtils.release(null, null, pstmt);

			if (rowCount != 0) {
//				System.out.println("CLIENT DELETED");
//				DBUtils.switchDB("db_name");
				DBUtils.switchTableDB("db_table_clients_history");
				pstmt = conn.prepareStatement("INSERT INTO " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME
						+ "(ACTION_CODE, ID, FIRST_NAME, LAST_NAME, GENDER, COUNTRY, AGE, REG_DATE) "
						+ "VALUES ('D', ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setInt(1, delClient.getId());
				pstmt.setString(2, delClient.getFirstName());
				pstmt.setString(3, delClient.getLastName());
				pstmt.setString(4, delClient.getGender());
				pstmt.setString(5, delClient.getCountry());
				pstmt.setInt(6, delClient.getAge());
				pstmt.setDate(7, new java.sql.Date(delClient.getRegDate().getTime()));

				rowCount = pstmt.executeUpdate();

				if (rowCount == 1) {
//					System.out.println("Client history action inserted");
				}

				DBUtils.release(conn, null, pstmt);
				return true;
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insertClient(Client client) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_name");
			DBUtils.switchTableDB("db_table_clients");
			pstmt = conn.prepareStatement("INSERT INTO " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME
					+ "(FIRST_NAME, LAST_NAME, GENDER, COUNTRY, AGE, REG_DATE) " + "VALUES (?, ?, ?, ?, ?, ?);");
			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setString(3, client.getGender());
			pstmt.setString(4, client.getCountry());
			pstmt.setInt(5, client.getAge());
			pstmt.setDate(6, new java.sql.Date(client.getRegDate().getTime()));

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			DBUtils.release(null, null, pstmt);

			if (rowCount != 0) {
//				System.out.println("CLIENT INSERTED");
//				DBUtils.switchDB("db_name");
				DBUtils.switchTableDB("db_table_clients_history");
				pstmt = conn.prepareStatement("INSERT INTO " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME
						+ "(ACTION_CODE, ID, FIRST_NAME, LAST_NAME, GENDER, COUNTRY, AGE, REG_DATE) "
						+ "VALUES ('I', (SELECT MAX(ID) FROM " + DBUtils.DB_NAME + ".clients), ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, client.getFirstName());
				pstmt.setString(2, client.getLastName());
				pstmt.setString(3, client.getGender());
				pstmt.setString(4, client.getCountry());
				pstmt.setInt(5, client.getAge());
				pstmt.setDate(6, new java.sql.Date(client.getRegDate().getTime()));

				rowCount = pstmt.executeUpdate();

				if (rowCount == 1) {
//					System.out.println("Client history action inserted");
				}
				DBUtils.release(conn, null, pstmt);
				return true;
			}

			DBUtils.release(conn, null, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateClient(Client client) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_name");
			DBUtils.switchTableDB("db_table_clients");
			pstmt = conn.prepareStatement("UPDATE " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME
					+ " SET FIRST_NAME = ?, LAST_NAME = ?, GENDER = ?, COUNTRY = ?, AGE = ?, REG_DATE = ?, UPDATED_TS = ?"
					+ "WHERE ID = ?");
			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setString(3, client.getGender());
			pstmt.setString(4, client.getCountry());
			pstmt.setInt(5, client.getAge());
			pstmt.setDate(6, new java.sql.Date(client.getRegDate().getTime()));
			pstmt.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
			pstmt.setInt(8, client.getId());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			DBUtils.release(null, null, pstmt);

			if (rowCount != 0) {
//				DBUtils.switchDB("db_name");
				DBUtils.switchTableDB("db_table_clients_history");
				pstmt = conn.prepareStatement("INSERT INTO " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME
						+ "(ACTION_CODE, ID, FIRST_NAME, LAST_NAME, GENDER, COUNTRY, AGE, REG_DATE) "
						+ "VALUES ('U', ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setInt(1, client.getId());
				pstmt.setString(2, client.getFirstName());
				pstmt.setString(3, client.getLastName());
				pstmt.setString(4, client.getGender());
				pstmt.setString(5, client.getCountry());
				pstmt.setInt(6, client.getAge());
				pstmt.setDate(7, new java.sql.Date(client.getRegDate().getTime()));

				rowCount = pstmt.executeUpdate();

				if (rowCount == 1) {
//					System.out.println("Client history action inserted");
				}
				DBUtils.release(conn, null, pstmt);
				return true;
			}
			DBUtils.release(conn, null, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<Client> getAllClients() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Client> clients = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
//			DBUtils.switchDB("db_name");
			DBUtils.switchTableDB("db_table_clients");
			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME);

			ResultSet rs = pstmt.executeQuery(); // INSERT, UPDATE or DELETE
			int counter = 0;
			while (rs.next()) {
				Client client = new Client();
				mapClient(client, rs);
				client.setId(rs.getInt("ID"));
				clients.add(client);
				counter++;
			}
//			System.out.println(counter + " clients selected");
			DBUtils.release(conn, null, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clients;
	}

	public boolean insertOrUpdateClient(Client client) {
		List<Client> allClients = getAllClients();
		boolean clientExist = false;
		for (Client client2 : allClients) {

			if (client2.getId() == client.getId()) {
				clientExist = true;
				updateClient(client);

			}
		}
		if (!clientExist) {
			insertClient(client);
		}
		return false;
	}

	public boolean restoreClient(Client client) {
//		DBUtils.switchDB("db_name");
		DBUtils.switchTableDB("db_table_clients_history");
		List<ClientHistory> dbHistoryClients = new ArrayList<>();
		List<Client> dbClients = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				ClientHistory clientHistory = new ClientHistory();
				clientHistory.setActionId(rs.getInt("ACTION_ID"));
				clientHistory.setActionCode(rs.getString("ACTION_CODE"));
				clientHistory.setId(rs.getInt("ID"));
				clientHistory.setFirstName(rs.getString("FIRST_NAME"));
				clientHistory.setLastName(rs.getString("LAST_NAME"));
				clientHistory.setGender(rs.getString("Gender"));
				clientHistory.setCountry(rs.getString("Country"));
				clientHistory.setAge(rs.getInt("Age"));
				clientHistory.setRegDate(rs.getDate("REG_DATE"));
				dbHistoryClients.add(clientHistory);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean clientInHistory = false;
		boolean clientInDB = false;
		for (Client client2 : dbHistoryClients) {
			if (client2.getId() == client.getId()) {
				clientInHistory = true;
				break;
			}
		}
		if (clientInHistory == false) {
			System.out.println("Cant restore the client. There is no data about the client in history");
			return false;
		} else {	
//			DBUtils.switchDB("db_name");
			DBUtils.switchTableDB("db_table_clients");
			dbClients = getAllClients();
			for (Client client2 : dbClients) {
				if (client2.getId() == client.getId()) {
					clientInDB = true;
					break;
				}
			}
			if (clientInDB == false) {
				for (int i = dbHistoryClients.size() - 1; i >= 0; i--) {
					if (dbHistoryClients.get(i).getId() == client.getId()) {
						insertClient(dbHistoryClients.get(i));
						break;
					}
				}
				return true;
			}
			return false;
		}

	}
	
	public List<Client> getAllClients(Timestamp createdFrom, Timestamp createdTo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Client> clients = new ArrayList<>();
		int counter = 0;
		
//		DBUtils.switchDB("db_name");
		DBUtils.switchTableDB("db_table_clients");
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + "." + DBUtils.DB_TABLE_NAME + " WHERE " + DBUtils.DB_TABLE_NAME + ".CREATED_TS BETWEEN ? AND ?");
			pstmt.setTimestamp(1, createdFrom);
			pstmt.setTimestamp(2, createdTo);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Client client = new Client();
				mapClient(client, rs);
				client.setId(rs.getInt("ID"));
				clients.add(client);
				counter++;
			}
//			System.out.println(counter + " clients selected");
			DBUtils.release(conn, null, pstmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clients;
	}
}
