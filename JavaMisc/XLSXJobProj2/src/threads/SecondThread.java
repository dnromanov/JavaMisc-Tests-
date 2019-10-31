package threads;

public class SecondThread extends Thread{
	int delay;
	
	public SecondThread(int delay) {
		super("SECOND TH");
		this.delay = delay;
	}
	
	@Override
	public void run() {
		System.out.println(currentThread() + "SECOND THREAD STARTED");
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("SECOND THREAD COUNTER:"+ i);	
		}
		System.out.println(currentThread() + " THREAD FINISHED");
		
	}
}
