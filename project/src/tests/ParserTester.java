package tests;

import org.junit.Assert;
import org.junit.Test;

import main.Console;
import parser.ParseException;


public class ParserTester {

	private void testCase(String expected, String expression) throws ParseException {
		Assert.assertEquals(expected, Console.accept(expression));
	}
	
	private void testNegativeCase(String expression) throws ParseException {
		Assert.assertFalse(Console.accept(expression));
	}
	
	@Test
	public void test() throws Exception {
		testCase("1", "1\n");
	}

	@Test
	public void test_arithmetic_ops() throws Exception {
		testCase("3", "1+2\n");
		testCase("-4", "1-2-3\n");
		testCase("4", "4/2/2\n");
		testCase("8", "4*2\n");
	}

	@Test
	public void test_error() throws Exception {
		testNegativeCase("1++1\n");
		testNegativeCase("*2\n");
		testNegativeCase("4/+2/2\n");
	}

	@Test
	public void tests_arithmetic() throws Exception {
		testCase("", "-1\n");
		testCase("", "-1*3\n");
		testCase("", "2*2\n");
		testCase("", "9/3\n");
		testCase("", "-1-3\n");
	}

	@Test
	public void tests_logical() throws Exception {
		testCase("", "true\n");
		testCase("", "false\n");
		testCase("", "~false\n");
		testCase("", "~false&&true\n");
		testCase("", "11 < 22\n");
		testCase("", "11 > 22\n");
		testCase("", "~(11 > 22)\n");
		testCase("", "11 = 22\n");
		testCase("", "~(11 = 22)\n");
		testCase("", "3*5 != 1+2 = true\n");
		testCase("", "1 = 2 && 3 = 4\n");
		testCase("", "~(1 = 2 && 3 = 4)\n");
		testNegativeCase("< 11\n");
		testNegativeCase("11 >\n");
		testNegativeCase("<= 11\n");
		testNegativeCase("&& A\n");
	}

	@Test
	public void tests_identifiers() throws Exception {
		testCase("", "let x = 9 in x + 2 \n");
		testCase("", "let x = true in x = true \n");
		testCase("", "let x = true in ~(x = true)||true \n");
		testCase("", "let x = 9 in x = 9 \n");
		testCase("", "let x = 9 in let y = 7 in x + y \n");
		testCase("", "let x = 9 in let y = 7 in let z = 5 in z + x + y \n");
		testCase("", "let x = 9 y = 8 in x + y \n");
		testCase("", "let x = 9 y = true in x > 9 && y \n");
	}

	@Test
	public void test_control_flow() throws Exception {
		testCase("", "if ~false then 2<5 end \n");
		testCase("", "if 2<5 then true&&~false end \n");
		testCase("", "if true then 2+2 end \n");
		testCase("", "let x = true in if x then x && true end \n");
		testCase("", "if 2<5&&true then let x = 9 in 5 + x end \n");
		testCase("", "let x = 9 in if x != 8 then x + 1 end \n");
		testCase("", "f false then 2+2 else 5 end \n");
		testCase("", "if false then 2+2 else 5 end \n");
		testCase("", "if true then 2+2 else 5 end \n");
	}

	@Test
	public void test_references() throws Exception {
		testCase("", "new(7) \n");
		testCase("", "let x = new(9) in !x + 4 \n");
		testCase("", "let x = new(true) in !x && false \n");
		testCase("", "let x = fun (z:ref) -> !z in x(new(4)) \n");
	}

	@Test
	public void test_functions() throws Exception {
		testCase("", "let x = fun (y:int) -> y+2 in x(3) \n");
		testCase("", "let x = fun (z:bool) -> z&&true in x(false) \n");
		testCase("", "let x = fun (z:bool) -> z||false in x(true) \n");
		testCase("", "let x = fun (z:bool) -> if 2<5&&z then let x = 9 in 5 + x end in x(false) \n");
		testCase("", "let x = fun (z:bool) -> if 2<5&&z then let x = 9 in 5 + x end in x(true) \n");
		testCase("", "let x = fun (z:bool) -> if z then true else 2+2 end in x(true) \n");
		testCase("", "let x = fun (z:bool) -> if z then true else 2+2 end in x(false) \n");
		testCase("", "let x = fun (z:bool) -> if z then 1 else 2+2 end in if x(true)<5 then 1+4 else 5 end \n");
	}

	@Test
	public void test_extras() throws Exception {
		testCase("", "first{5,9} \n");
		testCase("", "second{true,5} \n");
		testCase("", "second{5+5,true&&false} \n");
		testCase("", "let x = {5,6} in first(x) \n");
		testCase("", "let x = {5, {false, true}} in second second(x) \n");
		testCase("", "let x = {5, {{5, {false, 7}}, true}} in second second first second(x) \n");
	}
}









