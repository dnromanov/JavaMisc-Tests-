package helper.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import model.Client;
import util.ReportsUtil;

public class ReportHelper {
	public static final String DATE_FORMAT_PATTERN = "yyyyMMdd"; 
	
	
	
	public static void generateAllClientsTemplate(String outputFilePath, String inputFilePath) {
		try {
			List<Client> clients = new ClientHelper().loadClients(inputFilePath);
			if(clients.isEmpty()) {
				System.out.println("NO INPUT DATA");
				return;
			}
			Collections.sort(clients);
			
			File outputFile = new File(outputFilePath);
			if(!outputFile.exists()) {
				outputFile.createNewFile();
			}
			
			OutputStream out = new FileOutputStream(outputFile);
			XWPFDocument template = new XWPFDocument();

			XWPFParagraph paragraph = template.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setBold(true);
			SimpleDateFormat simpleDateFormat  = new SimpleDateFormat(DATE_FORMAT_PATTERN);
			String dateStr = simpleDateFormat.format(new Date());
			
			run.setText(String.format("ALL CLIENTS. SINCE - %s", dateStr));
			run.addBreak();
			
			
			//TODO
			for (Client client : clients) {
				XWPFRun clientRun = paragraph.createRun();
				clientRun.setBold(false);
				clientRun.setText(
						client.toString().substring(
								client.toString().indexOf("[") + 1, 
								client.toString().indexOf("]")));
				clientRun.addBreak();
			}
			template.write(out);
			out.close();
			template.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void generateClientTemplate(String path) {
		
		//File folder = new File("output");
		//folder.listFiles();
		
		
		
		XWPFDocument template = new XWPFDocument();
		OutputStream os = null;
		
		try {
			
			File outputFile = new File(path);
			if(!outputFile.exists()) {
				outputFile.createNewFile();
			}
			
			os = new FileOutputStream(outputFile);
			XWPFParagraph paragraph = template.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setBold(true);
			SimpleDateFormat simpleDateFormat  = new SimpleDateFormat(DATE_FORMAT_PATTERN);
			String dateStr = simpleDateFormat.format(new Date());
			
			run.setText(String.format("CLIENTS REPORT. SINCE - %s", dateStr));
			run.addBreak();
		
			
			Map<String, Integer> data = ReportsUtil.getCountryClientsCounter();
			
			for(Entry<String, Integer> entry : data.entrySet()) {
				XWPFRun run2 = paragraph.createRun();
				run2.setBold(false);
				run2.setText(entry.getKey() + " : " + entry.getValue());
				run2.addBreak();
			}	
			
			template.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				template.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
