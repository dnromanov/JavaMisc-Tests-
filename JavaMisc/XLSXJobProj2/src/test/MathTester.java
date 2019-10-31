package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MathTester {
	
	private static Math math;
	
	/**
	 *  Before all tests
	 */
	@BeforeClass
	public static void init() {
		System.out.println("initialization job");
		math = new Math();
	}
	
	/**
	 *  After all tests
	 */
	@AfterClass
	public static void destroy() {
		System.out.println("finalization job");
		math = null;
	}
	
	/**
	 *  Before all tests
	 */
	@Before
	public void init2() {
		System.out.println("before each test");
	}
	
	/**
	 *  After all tests
	 */
	@After
	public void destroy2() {
		System.out.println("after each test");
	}
	
	@Test
	public void testSumma1() {
		int s = math.summa(5, 5);
		assertEquals(s, 10);
	}
	
	@Test
	public void testSumma2() {
		int s = math.summa(-5, 5);
		assertEquals(s, 0);
	}
	
	@Test
	public void testDiv1() {
		double d = math.div(4, 2);
		assertTrue(d == 2.0);
	}
	
	@Test(expected = java.lang.ArithmeticException.class, timeout = 500)
	public void testDiv3() {
		double d = math.div(4, 0);
//		assertTrue(d == 2.0);
	}
	
}
