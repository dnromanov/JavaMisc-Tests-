package threads.task;

import helper.poi.ReportHelper;

public class ThreadPOIExecutor extends Thread {
	private String path;
	public String getPath() {
		return path;
	}
	 public ThreadPOIExecutor(String path) {
		 this.path = path;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread());
		ReportHelper.generateClientTemplate(path);
		System.out.println(Thread.currentThread() + " <- END");
		
	}

}
