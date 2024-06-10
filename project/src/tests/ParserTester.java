package tests;

import org.junit.Assert;
import org.junit.Test;

import main.Console;
import parser.ParseException;


public class ParserTester {

    private void testCase(String expression) throws ParseException {
        Assert.assertNotNull(Console.parse(expression));
    }

    private void testNegativeCase(String expression) throws ParseException {
        Assert.assertNull(Console.parse(expression));
    }

    @Test
    public void test01BasicExpressions() throws Exception {
        testCase("1\n");
        testCase("true\n");
        testCase("false\n");
    }

    @Test
    public void test02ArithmeticOps() throws Exception {
        testCase("1+2\n");
        testCase("1-2-3\n");
        testCase("4*2\n");
        testCase("4/2/2\n");
    }

    @Test
    public void test03ErrorArithmeticOps() throws Exception {
        testNegativeCase("1++1\n");
        testNegativeCase("*2\n");
        testNegativeCase("4/+2/2\n");
    }

    @Test
    public void test04LogicalOps() throws Exception {
        testCase("true\n");
        testCase("false\n");
        testCase("~false\n");
        testCase("true && false\n");
        testCase("11 < 22\n");
        testCase("11 > 22\n");
        testCase("11 = 22\n");
        testCase("3*5 != 1+2 = true\n");
        testCase("1 = 2 && 3 = 4\n");
        testNegativeCase("< 11\n");
        testNegativeCase("11 >\n");
        testNegativeCase("<= 11\n");
        testNegativeCase("&& A\n");
    }

    @Test
    public void test05Identifiers() throws Exception {
        testCase("let x = 9 in x + 2\n");
        testCase("let x = true in x = true\n");
        testCase("let x = 9 in let y = 7 in x + y\n");
        testCase("let x = 9 y = 7 in x + y\n");
        testCase("let x = 9 in let y = 7 in let z = 5 in z + x + y\n");
        testNegativeCase("let x = 9, y in x + y\n");
        testNegativeCase("let x = 9 x + 2\n");
    }

    @Test
    public void test06ControlFlow() throws Exception {
        testCase("if ~false then 2 < 5 end\n");
        testCase("if false then 2 < 5 end\n");
        testCase("if 2 < 5 then true && ~false end\n");
        testCase("if true then 2 + 2 end\n");
    }

    @Test
    public void test07References() throws Exception {
        testCase("new(7)\n");
        testCase("!(new(5))\n");
        testCase("let x = new(9) in !x + 4\n");
        testCase("let x = new(true) in !x && false\n");
    }

    @Test
    public void test08Prints() throws Exception {
        testCase("print(5)\n");
        testCase("println(5)\n");
        testCase("let x = 10 in print(x + 3)\n");
        testCase("let x = new(true) in println(!x)\n");
        testNegativeCase("print(\"hello\", 6)\n");
    }

    @Test
    public void test09Functions() throws Exception {
        testCase("fun (y:int) -> y + 2\n");
        testCase("fun (z:bool) -> z && true\n");
        testNegativeCase("fun y -> y + true\n");
        testNegativeCase("fun (z:bool, y:int) -> z + 1\n");
    }

    @Test
    public void test10Tuples() throws Exception {
        testCase("{5, 9}\n");
        testCase("{true, 5}\n");
        testCase("first{5, 9}\n");
        testCase("second{true, 5}\n");
        testNegativeCase("second(5, 6)\n");
    }

    @Test
    public void test11Strings() throws Exception {
        testCase("\"this might be a string\"\n");
        testCase("concat(\"this\", \"that\")\n");
        testCase("let x = \"this\" in x\n");
        testNegativeCase("concat(\"this\", \"no\", \"yes\")\n");
    }

    @Test
    public void test12ComplexExpressions() throws Exception {
        testCase("let x = 10 in let y = 20 in if x < y then x + y else x - y end\n");
        testCase("let f = fun (x:int) -> x + 1 in f(10)\n");
        testNegativeCase("let p = {3, 4} in first(p) + second(p) + third(p)\n");
    }
}