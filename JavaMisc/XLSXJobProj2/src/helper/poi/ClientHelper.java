package helper.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Client;

public class ClientHelper {

	public List<Client> loadClients(String filePath) {
		List<Client> clients = new ArrayList<Client>();
		try {

			File excelFile = new File(filePath);
			FileInputStream fis = new FileInputStream(excelFile);
			
			// we create an XSSF Workbook object for our XLSX Excel File
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			// we get first sheet
			XSSFSheet sheet = workbook.getSheetAt(0);

			// we iterate on rows
			Iterator<Row> rowIt = sheet.iterator();
			rowIt.next();// to skip first row (headers)
			Client client;
			while (rowIt.hasNext()) {
				client = new Client();
				Row row = rowIt.next();
				Iterator<Cell> cellIter = row.cellIterator();
				cellIter.next();// to skip first column
				for (int i = 1; cellIter.hasNext(); i++) {
					Cell cell = cellIter.next();
					switch (i) {
					case 1:
						client.setFirstName(cell.getStringCellValue());
						break;
					case 2:
						client.setLastName(cell.getStringCellValue());
						break;
					case 3:
						client.setGender(cell.getStringCellValue());
						break;
					case 4:
						client.setCountry(cell.getStringCellValue());
						break;
					case 5:
						client.setAge((int)cell.getNumericCellValue());
						break;
					case 6:
						//  16/08/2016   - dd/MM/yyyy
						//client.setDate();
						//client.setDate(DateUtil.getJavaDate(cell.getNumericCellValue()));
						 DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	                     Date date = null;
						try {
							date = (Date) formatter.parse(cell.getStringCellValue());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                        client.setRegDate(date);
						
						break;
					case 7:
						client.setId((int)cell.getNumericCellValue());
						break;

					default:
						break;
					}
				}
				
				clients.add(client);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clients;
	}
	
	public static void main(String[] args) {
		System.out.println(new ClientHelper().loadClients("input/file_example_XLS_1000_NEW.xlsx").size());
		//new ClientHelper().loadClients("input/file_example_XLS_1000_NEW.xlsx").size();
	}

}
