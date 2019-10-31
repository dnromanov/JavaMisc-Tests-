package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import helper.poi.ClientHelper;
import impl.ClientsDBDAOImpl;
import model.Client;
import util.DBUtils;

public class Test {

	public static void main(String[] args) {

		ClientsDBDAOImpl impl = new ClientsDBDAOImpl();
		List<Client> clients = new ArrayList<>();
		int counter = 0;
//
//		// insert all clients from file
//		clients = new ClientHelper().loadClients("input/file_example_XLS_1000_NEW.xlsx");
//		for (Client client : clients) {
//			impl.insertOrUpdateClient(client);
//			counter++;
//		}
//		System.out.println(counter + " clients inserted");
//
//		// select all clients from DB
//		clients = impl.getAllClients();
//
//		//update clients from DB
//		counter = 0;
//		for (Client client : clients) {
//			if (client.getCountry().equals("Great Britain")) {
//				client.setCountry("Great Britain1");
//				impl.updateClient(client);
//				counter++;
//				break;
//			}
//		}
//		System.out.println(counter + " clients updated");
//
//		// remove all clients from DB
//		counter = 0;
//		for (Client client : clients) {
//			if (client.getCountry().equals("Great Britain")) {
//				impl.removeClient(client);
//				counter++;
//				break;
//			}
//		}
//		System.out.println(counter + " clients deleted");
//		
//		System.out.println(impl.restoreClient(new Client(3010)));
		
//		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
//		Timestamp createdFrom = null;
//		Timestamp createdTo = null;
//		try {
//			Date dateCreatedFrom = formatter.parse("14.08.2019 12:00:00");
//			Date dateCreatedTo = formatter.parse("20.08.2019 12:00:00");
//			createdFrom = new Timestamp(dateCreatedFrom.getTime());
//			createdTo = new Timestamp(dateCreatedTo.getTime());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Timestamp createdFrom = Timestamp.valueOf("2019-08-14 00:00:00");
//		Timestamp createdTo = Timestamp.valueOf("2019-8-20 0:0:0");
//		
//		List<Client> clients1 = impl.getAllClients(createdFrom, createdTo);
//		System.out.println(clients1.size());
		
		DBUtils.executeSQL("input/SQLCodeToExecute");
		

	}
}
