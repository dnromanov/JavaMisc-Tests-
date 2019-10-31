package util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import helper.poi.ClientHelper;
import model.Client;

public class ReportsUtil {
	
	static String filePath = "input/file_example_XLS_1000_NEW.xlsx";
	
	public static Map<String, Integer> getCountryClientsCounter(){
		Map<String, Integer> data = new TreeMap<>();
		
		List<Client> clients = new ClientHelper().loadClients(filePath);
		
		for (Client client : clients) {
			String key = client.getCountry();
			if(!data.containsKey(key)) {
				data.put(key, 1);
			} else {
				data.put(key, data.get(key) + 1);
			}
		}
		return data;
	}
}
