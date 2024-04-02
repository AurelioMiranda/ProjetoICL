package tests;

import org.junit.Assert;
import org.junit.Test;

import main.Console;
import parser.ParseException;


public class ParserTester {

	private void testCase(String expression) throws ParseException {
		Assert.assertTrue(Console.accept(expression));		
	}
	
	private void testNegativeCase(String expression) throws ParseException {
		Assert.assertFalse(Console.accept(expression));
	}
	
	@Test
	public void test01() throws Exception {
		testCase("1\n");
	}

	@Test
	public void test02ArithmeticOps() throws Exception {
		testCase("1+2\n");
		testCase("1-2-3\n");
		testCase("4*2\n");
		testCase("4/2/2\n");
	}
	
	@Test
	public void test03Error01() throws Exception {
		testNegativeCase("1++1\n");
		testNegativeCase("*2\n");
		testNegativeCase("4/+2/2\n");
	}
	
	@Test
	public void testsLabClass01() throws Exception {
		testCase("-1\n");
		testCase("-1*3\n");
		testCase("true\n");
		testCase("false\n");
		testCase("11 < 22\n");
		testCase("11 > 22\n");
		testCase("11 == 22\n");
		testCase("3*5 != 1+2 == true\n");
		testCase("1 == 2 && 3 == 4\n");
		testCase("1 == 2 || 3 == 4 && xpto \n");
		testCase("!(1 == 2) && xpto \n");
		testNegativeCase("< 11\n");
		testNegativeCase("11 >\n");
		testNegativeCase("<= 11\n");
		testNegativeCase("&& A\n");
	}
}









