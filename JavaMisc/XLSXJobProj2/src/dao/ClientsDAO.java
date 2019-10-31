package dao;

import java.util.List;

import model.Client;

public interface ClientsDAO {
	//CRUD
	Client getClientById(Client client);
	boolean removeClient(Client client);
	boolean insertClient(Client client);
	boolean updateClient(Client client);
	
	//EXTRA
	List<Client> getAllClients();
	//...	
}
