package threads;

public class Executer {

	public static void main(String[] args) {
		ThirdTh th1 = new ThirdTh();
		
		try {
			th1.join();
			//Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		th1.disable();
		
		
		NewThread newTh = new NewThread();
		Thread th4 = new Thread(newTh, "NEW RUNNABLE TH");
		th4.start();
		try {
			th4.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("END." + Thread.currentThread());
	}

}
