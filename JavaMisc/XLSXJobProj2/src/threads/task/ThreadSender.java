package threads.task;

import helper.mail.MailHelper;

public class ThreadSender extends Thread {
	private Thread joinTo;
	public ThreadSender(Thread joinTo) {
		this.joinTo = joinTo;
	}
	@Override
	public void run() {
		//joinTo.join();
		System.out.println("run() -> " + Thread.currentThread());
		MailHelper.sendMail(MailHelper.MAIL_ACCOUNT, MailHelper.MAIL_ACCOUNT, Thread.currentThread().getId()+"",
				Thread.currentThread().getId()+"", joinTo);
	}
}
