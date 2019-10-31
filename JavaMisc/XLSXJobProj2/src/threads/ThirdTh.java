package threads;

public class ThirdTh extends Thread{
	private boolean isActive = true;
	private Thread thToJoint;
	public void disable() {
		isActive = false;
	}
	
	public void setThToJoint(Thread thToJoint) {
		this.thToJoint = thToJoint;
	}
	
	public ThirdTh() {
		//for (int i = 0; i < 100; i++) {
			this.start();
		//}
		
		
	}
	
	@Override
	public void run() {
		
		System.out.println(currentThread() + " START");
		while (isActive) {
			try {
				ThirdTh.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("ThirdTh JOB");	
		}
		try {
			thToJoint.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(currentThread() + " END.");
	}
}
