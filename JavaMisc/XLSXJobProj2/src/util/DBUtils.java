package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

	public static String DB_NAME = AppSettings.getInstance().getProperty("db_name");
	public static String DB_TABLE_NAME = AppSettings.getInstance().getProperty("db_table_clients");;

	public static void switchDB(String key) {
		DB_NAME = AppSettings.getInstance().getProperty(key);
	}

	public static void switchTableDB(String key) {
		DB_TABLE_NAME = AppSettings.getInstance().getProperty(key);
	}

	public static final String DB_URL = "jdbc:mysql://localhost:3306/new_db";
	public static final String DB_USER = "root";
	public static final String DB_USER_PSW = "";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_USER_PSW);
//			System.out.println("Connection is open - " + conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void release(Connection conn, Statement stmt, PreparedStatement pstmt) {

		try {
			if (stmt != null) {
				stmt.close();
//				System.out.println("Statement is CLOSED - " + stmt);
			}

			if (pstmt != null) {
//				System.out.println("PreparedStatement is CLOSED - " + pstmt);
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
//				System.out.println("Connection is CLOSED - " + conn);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void executeSQL(String path) {
		//read file
		File file = new File(path);
		String fileContent = "";
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = bufferedReader.readLine();
			while (line != null) {
				fileContent = fileContent + line + " ";
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//divide file by statements
		int firstIndex = 0;
		int indexOfDelimiter = 0;
		List<String> listOfStatements = new ArrayList<>();
		String statement = "";
		if (fileContent.trim().length() != 0) {
			while (fileContent.indexOf(';') != -1) {
				indexOfDelimiter = fileContent.indexOf(';');
				statement = fileContent.substring(firstIndex, indexOfDelimiter);
				listOfStatements.add(statement.trim());
				fileContent = fileContent.substring(indexOfDelimiter + 1);
			}
			
			if (fileContent.trim().length() > 0) {
				listOfStatements.add(fileContent.trim());
			}
			System.out.println("List of statements is formed");
		}
		else {
			System.out.println("The path does not contein valid content");
		}

		
		//try to execute statements in the file
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		conn = getConnection();
		boolean hasSelectStatement = false;
		int counter = 0;
		for (String string : listOfStatements) {
			try {
				pstmt = conn.prepareStatement(string);
				if(pstmt.execute()) {
					hasSelectStatement = true;
					counter++;
				};
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (hasSelectStatement) {
			System.out.println("The file contains SELECT statements: " + counter);
		}
		System.out.println("The file is executed");
	}
}
