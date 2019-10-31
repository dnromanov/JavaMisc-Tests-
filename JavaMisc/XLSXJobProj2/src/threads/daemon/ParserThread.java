package threads.daemon;

import java.time.LocalDate;

public class ParserThread extends Thread{
	public ParserThread() {
		super("NBRB Parser");
	}
	
	@Override
	public void run() {
		while (true) {
			//System.out.println(getName() + " -> " + LocalDate.now());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("CURRENT USD RATE = " + XMLCurrencyParser.getCurrency("840"));	
		}
	}
}
