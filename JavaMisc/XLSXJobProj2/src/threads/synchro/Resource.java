package threads.synchro;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Resource {
	private Vector<Long> ids = new Vector<Long>();
	private ArrayList<Long> ids2 = new ArrayList<>();
	public List<Long> getIds() {
		return ids;
	}
}


class ResourceUser implements Runnable{
	private Resource res;
	public ResourceUser(Resource res) {
		this.res = res;
	}

	@Override
	public void run() {
		System.out.printf("run() - starting - %s ID:%d\n",
				Thread.currentThread().getName(), Thread.currentThread().getId());		
		synchronized (res) {
			res.getIds().clear();
			for (int i = 0; i < 5; i++) {
				
				res.getIds().add(Thread.currentThread().getId());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String list = res.getIds().toString();
				System.out.printf("%s ID:%d - list size: %d - %s\n",Thread.currentThread().getName(), 
						Thread.currentThread().getId(), res.getIds().size(), list);
			}
		}
		
		System.out.printf("run() - finishing - %s ID:%d\n",
				Thread.currentThread().getName(), Thread.currentThread().getId());		
		
	}
}


class SynchroTest {
	public static void main(String[] args) {
		Resource res = new Resource();
		int p = 5;
		for (int i = 0; i < 5; i++) {
			Thread th = new Thread(new ResourceUser(res));
			th.setPriority(p--);
			th.setName("TH#"+(i+1));
			th.start();
		}
		StringBuffer sbuf = new StringBuffer(128);
		String str1 = sbuf.append("A").append(456).append("").toString();
		
		StringBuilder sbui = new StringBuilder(128);
		String str2 = sbui.append("A").append(456).append("").toString();
		
		
	}
}