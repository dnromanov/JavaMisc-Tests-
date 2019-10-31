package test;

import java.util.Date;
import java.util.List;

import helper.mail.MailHelper;
import helper.poi.ReportHelper;


public class Test2 {

	public static void main(String[] args) {
//		ReportHelper.generateClientTemplate(
//				String.format("output/clients_report_%s.docx", new Date().toString()));
//		
//		
	
		
		
		String file = "output/all_clients_reports.docx";
		ReportHelper.generateAllClientsTemplate(
				file,
				"input/file_example_XLS_1000_NEW.xlsx");
		
		MailHelper.sendMail(
				MailHelper.MAIL_ACCOUNT, 
				MailHelper.MAIL_ACCOUNT, 
				"REPORT WITH ALL CLIENTS", 
				"OPEN FILE", 
				file);
		
	}

}
