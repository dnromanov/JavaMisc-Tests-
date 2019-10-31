package threads;

import threads.daemon.ParserThread;

public class Bank {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("START. Bank job in " + Thread.currentThread());
		//START EXTRA JOB
		ParserThread parser  = new ParserThread();
		parser.setDaemon(true);
		parser.start();
		
		for (int i = 0; i < 8; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("WORKING WITH CLIENTS");
		}
		
		
		Thread extra = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					System.out.println("EXTRA JOB");
				}
			}
		});
		
		extra.start();
		extra.join();
		
		System.out.println("FINISHED. Bank job in " + Thread.currentThread());

	}

}




class One implements Runnable{

	@Override
	public void run() {
		System.out.println("ONE");
		
	}
	
}


class OneRun {
	public static void main(String[] args) throws InterruptedException {
		Thread th = new Thread(new One(), "TRALALA");
		th.join();
		th.start();
		
	}
}