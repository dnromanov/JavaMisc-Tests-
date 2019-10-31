package except;

public class AppException extends Exception{
	String host = "http://localhost:8888/mydb";
//		db.login = root
//		db.password = dbroot
	private int errorCode;
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public AppException() {
		this.errorCode = 100; // UNKNOWN ERROR CODE
	}
	
	public AppException(String msg, int errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public AppException(String msg, Throwable th) {
		super(msg, th);
	}
}
