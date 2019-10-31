package except;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Example {

	
	//
	static void doSome(int a, int b) {
		System.out.println("BEGIN");
		try {
			// 
			System.out.println(String.format("RESULT is %d", a/b));
		} catch (ArithmeticException e) {
			e.printStackTrace();
			System.out.println(
					String.format("ROOT MESSAGE : %s",
							e.getMessage()));
		} finally {
			// 
			System.out.println("FINALLY BLOCK");
		}
		
		System.out.println("END.");
	}
	
	/**
	 * <b>In case of INCORRECT NAME </b> -
	 *  throws  @IllegalArgumentException
	 * 
	 * @param name
	 */
	static void hello(String name) {
		if(name == null || name.trim().length() == 0) {
			//
			throw new IllegalArgumentException("INCORRECT NAME = " + name);
		}
		System.out.println("Hello, my name is " + name);
	}
	
	
	static InputStream m3(String path) throws IOException {
		InputStream input = new FileInputStream(path);
		System.out.println(input.read());
		return input;

	}
	
	public static void main(String[] args) {
		// CLIENT 
		System.out.println("MAIN STARTED!");
		doSome(5, 0);
	
		try {
			hello(null);
		} catch (ArithmeticException e) {
			e.printStackTrace();
			//do some Arithmetic handling
		} catch (Exception e) {
			e.printStackTrace();
			//do some generic handling
		} finally {
			hello("Unknown"); 
		}
		System.out.println("...");
		
		InputStream in = null;
		try {
			in = m3("input/data.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(in!= null) {
				try {
					in.close();
					System.out.println("CLOSED SUCCESSFULLY");
				} catch (IOException e) {
					System.out.println("ERROR WHEN TRYING TO CLOSE STREAM");
					e.printStackTrace();
				}
			}
		}
		
		
		
		try (InputStream in2 = m3("input/data.txt")){
			in2.read();
			System.out.println(in2.read());
			
		} catch (Exception e) {
			System.out.println("Ooops!");
			e.printStackTrace();
		}
		
		
		System.out.println("Do something else");
		
		AutoCloseable  a;
		
		
		
	}

}
