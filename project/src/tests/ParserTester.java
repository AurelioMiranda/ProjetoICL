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
	public void testsArithmetic() throws Exception {
		testCase("-1\n");
		testCase("-1*3\n");
		testCase("2*2\n");
		testCase("9/3\n");
		testCase("-1-3\n");
	}

	@Test
	public void testsLogical() throws Exception {
		testCase("true\n");
		testCase("false\n");
		testCase("~false\n");
		testCase("~false&&true\n");
		testCase("11 < 22\n");
		testCase("11 > 22\n");
		testCase("~(11 > 22)\n");
		testCase("11 = 22\n");
		testCase("~(11 = 22)\n");
		testCase("3*5 != 1+2 = true\n");
		testCase("1 = 2 && 3 = 4\n");
		testCase("~(1 = 2 && 3 = 4)\n");
		testNegativeCase("< 11\n");
		testNegativeCase("11 >\n");
		testNegativeCase("<= 11\n");
		testNegativeCase("&& A\n");
	}

	@Test
	public void testsIdentifiers() throws Exception {
		testCase("let x = 9 in x + 2 \n");
		testCase("let x = true in x = true \n");
		testCase("let x = true in ~(x = true)||true \n");
		testCase("let x = 9 in x = 9 \n");
		testCase("let x = 9 in let y = 7 in x + y \n");
		testCase("let x = 9 in let y = 7 in let z = 5 in z + x + y \n");
	}

	@Test
	public void testControlFlow() throws Exception {
		testCase("if ~false then 2<5 end \n");
		testCase("if 2<5 then true&&~false end \n");
		testCase("if true then 2+2 end \n");
		testCase("let x = true in if x then x && true end \n");
		testCase("if 2<5&&true then let x = 9 in 5 + x end \n");
		testCase("let x = 9 in if x != 8 then x + 1 end \n");
		testCase("if false then true else 2+2 end \n");
		testCase("if false then 2+2 else true end \n");
		testCase("if true then 2+2 else true end \n");
	}
}









