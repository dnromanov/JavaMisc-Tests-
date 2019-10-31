package except;

public class EmailValidationException extends AppException {
	
	public EmailValidationException(int errorCode) {
		super("EMAIL VALIDATION EXTEPTION", errorCode);
	}
	
	public EmailValidationException(String msg, Throwable th) {
		super(msg, th);
	}
}
