package threads.synchro;

public class Resource2 {
	int counter;
	public void increment() {
		System.out.println("WAITING THREAD - " + Thread.currentThread().getName());	
		
		synchronized (this) {
			System.out.printf("run() - starting - %s ID:%d\n",
					Thread.currentThread().getName(), Thread.currentThread().getId());	
			counter = 0;
			for (int i = 0; i < 5; i++) {
				counter++;	
				System.out.printf("%s ID:%d COUNTER%d\n",
						Thread.currentThread().getName(), 
						Thread.currentThread().getId(), counter);	
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.printf("run() - finishing - %s ID:%d\n",
					Thread.currentThread().getName(), Thread.currentThread().getId());	
			
		}
		
	}
}

class Resource2User implements Runnable{
	private Resource2 res;
	public Resource2User(Resource2 res) {
		this.res = res;
	}
	@Override
	public void run() {
		res.increment();
	}
	
}

class Synchro2Test{
	public static void main(String[] args) throws InterruptedException {
		Resource2 rs = new Resource2();
		
		for (int i = 1; i < 6; i++) {
			Thread.sleep(50);
			new Thread(new Resource2User(rs), "THREAD #"+i).start();;
		}
	}
}
