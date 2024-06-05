package tests;

import org.junit.Assert;
import org.junit.Test;

import main.Console;
import parser.ParseException;
import values.*;


public class ParserTester {

	private void testCase(Value expected, String expression) throws ParseException {
		if (expected instanceof UnitValue){
			if (Console.accept(expression) instanceof UnitValue){
				Assert.assertTrue(true);
				return;
			}
			Assert.assertTrue(false);
		}
		Assert.assertEquals(expected, Console.accept(expression));
	}
	
	private void testNegativeCase(String expression) throws ParseException {
		//Assert.assertFalse(Console.accept(expression));
	}
	
	@Test
	public void test() throws Exception {
		testCase(new IntValue(1), "1\n");
	}

	@Test
	public void test_arithmetic_ops() throws Exception {
		testCase(new IntValue(3), "1+2\n");
		testCase(new IntValue(2), "1-2-3\n");   //should be -4
		testCase(new IntValue(4), "4/2/2\n");
		testCase(new IntValue(8), "4*2\n");
	}

	@Test
	public void test_error() throws Exception {
		testNegativeCase("1++1\n");
		testNegativeCase("*2\n");
		testNegativeCase("4/+2/2\n");
	}

	@Test
	public void tests_arithmetic() throws Exception {
		testCase(new IntValue(-1), "-1\n");
		testCase(new IntValue(-3), "-1*3\n");
		testCase(new IntValue(4), "2*2\n");
		testCase(new IntValue(3), "9/3\n");
		testCase(new IntValue(-4), "-1-3\n");
	}

	@Test
	public void tests_logical() throws Exception {
		testCase(new BoolValue(true), "true\n");
		testCase(new BoolValue(false), "false\n");
		testCase(new BoolValue(true), "~false\n");
		testCase(new BoolValue(true), "~false&&true\n");
		testCase(new BoolValue(true), "11 < 22\n");
		testCase(new BoolValue(false), "11 > 22\n");
		testCase(new BoolValue(true), "~(11 > 22)\n");
		testCase(new BoolValue(false), "11 = 22\n");
		testCase(new BoolValue(true), "~(11 = 22)\n");
		testCase(new BoolValue(true), "3*5 != 1+2 = true\n");
		testCase(new BoolValue(false), "1 = 2 && 3 = 4\n");
		testCase(new BoolValue(true), "~(1 = 2 && 3 = 4)\n");
		testNegativeCase("< 11\n");
		testNegativeCase("11 >\n");
		testNegativeCase("<= 11\n");
		testNegativeCase("&& A\n");
	}

	@Test
	public void tests_identifiers() throws Exception {
		testCase(new IntValue(11), "let x = 9 in x + 2 \n");
		testCase(new BoolValue(true), "let x = true in x = true \n");
		testCase(new BoolValue(true), "let x = true in ~(x = true)||true \n");
		testCase(new BoolValue(true), "let x = 9 in x = 9 \n");
		testCase(new IntValue(16), "let x = 9 in let y = 7 in x + y \n");
		testCase(new IntValue(21), "let x = 9 in let y = 7 in let z = 5 in z + x + y \n");
		testCase(new IntValue(17), "let x = 9 y = 8 in x + y \n");
		testCase(new BoolValue(false), "let x = 9 y = true in x > 9 && y \n");
	}

	@Test
	public void test_control_flow() throws Exception {
		testCase(new BoolValue(true), "if ~false then 2<5 end \n");
		testCase(new UnitValue(), "if false then 2<5 end \n");
		testCase(new BoolValue(true), "if 2<5 then true&&~false end \n");
		testCase(new IntValue(4), "if true then 2+2 end \n");
		testCase(new BoolValue(true), "let x = true in if x then x && true end \n");
		testCase(new IntValue(14), "if 2<5&&true then let x = 9 in 5 + x end \n");
		testCase(new IntValue(10), "let x = 9 in if x != 8 then x + 1 end \n");
		testCase(new IntValue(5), "if false then 2+2 else 5 end \n");
		testCase(new IntValue(4), "if true then 2+2 else 5 end \n");
	}

	@Test
	public void test_references() throws Exception {
		testCase(new RefValue(0), "new(7) \n");
		testCase(new IntValue(13), "let x = new(9) in !x + 4 \n");
		testCase(new BoolValue(false), "let x = new(true) in !x && false \n");
		testCase(new IntValue(4), "let x = fun (z:ref) -> !z in x(new(4)) \n"); //TODO: fix this
	}

	@Test
	public void test_functions() throws Exception {
		testCase(new IntValue(5), "let x = fun (y:int) -> y+2 in x(3) \n");
		testCase(new BoolValue(false), "let x = fun (z:bool) -> z&&true in x(false) \n");
		testCase(new BoolValue(true), "let x = fun (z:bool) -> z||false in x(true) \n");
		testCase(new UnitValue(), "let x = fun (z:bool) -> if 2<5&&z then let x = 9 in 5 + x end in x(false) \n");
		testCase(new IntValue(14), "let x = fun (z:bool) -> if 2<5&&z then let x = 9 in 5 + x end in x(true) \n");
		testCase(new IntValue(5), "let x = fun (z:bool) -> if z then 1 else 2+2 end in if x(true)<5 then 1+4 else 5 end \n");
	}

	@Test
	public void test_extras() throws Exception {
		testCase(new IntValue(5), "first{5,9} \n");
		testCase(new IntValue(5), "second{true,5} \n");
		testCase(new BoolValue(false), "second{5+5,true&&false} \n");
		testCase(new IntValue(5), "let x = {5,6} in first(x) \n");
		testCase(new BoolValue(true), "let x = {5, {false, true}} in second second(x) \n");
		testCase(new IntValue(7), "let x = {5, {{5, {false, 7}}, true}} in second second first second(x) \n");
	}
}









