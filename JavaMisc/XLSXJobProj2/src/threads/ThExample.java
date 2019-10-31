package threads;

public class ThExample {

	public static void main(String[] args) {
		System.out.println("MAIN STARTED");
		Thread myThread = Thread.currentThread();
			
		SecondThread sth = new SecondThread(1000);
		sth.setName("EXTRA THREAD");
		//sth.run();
		sth.start();
		
		SecondThread sth2 = new SecondThread(100);
		sth2.setName("EXTRA2 THREAD");
		//sth.run();
		sth2.start();
		
		SecondThread sth3 = new SecondThread(50);
		sth3.setName("EXTRA3 THREAD");
		//sth.run();
		sth3.start();
		
		
		ThirdTh third = new ThirdTh();
		third.start();
		
		try {
			ThirdTh.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		third.disable();
		
		System.out.println(myThread);
		System.out.println("MAIN FINISHED");
		
		
	}

}
