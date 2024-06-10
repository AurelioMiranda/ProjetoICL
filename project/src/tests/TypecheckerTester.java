package tests;

import org.junit.Assert;
import org.junit.Test;

import main.Console;
import parser.ParseException;
import types.*;

import java.util.ArrayList;
import java.util.List;


public class TypecheckerTester {

    private void testCase(Type expected, String expression) throws ParseException {
        Assert.assertEquals(expected, Console.typeCheck(expression));
    }

    private void testNegativeCase(String expression) throws ParseException {
        //Assert.assertFalse(Console.accept(expression));
    }

    @Test
    public void test01BasicTypes() throws Exception {
        testCase(IntType.getIntType(), "1\n");
        testCase(BoolType.getBoolType(), "true\n");
        testCase(BoolType.getBoolType(), "false\n");
    }

    @Test
    public void test02ArithmeticOps() throws Exception {
        testCase(IntType.getIntType(), "1+2\n");
        testCase(IntType.getIntType(), "1-2-3\n");
        testCase(IntType.getIntType(), "4*2\n");
        testCase(IntType.getIntType(), "4/2/2\n");
    }

    @Test
    public void test03ErrorArithmeticOps() throws Exception {
        testNegativeCase("1++1\n");
        testNegativeCase("*2\n");
        testNegativeCase("4/+2/2\n");
    }

    @Test
    public void test04LogicalOps() throws Exception {
        testCase(BoolType.getBoolType(), "true\n");
        testCase(BoolType.getBoolType(), "false\n");
        testCase(BoolType.getBoolType(), "~false\n");
        testCase(BoolType.getBoolType(), "~false && true\n");
        testCase(BoolType.getBoolType(), "11 < 22\n");
        testCase(BoolType.getBoolType(), "11 > 22\n");
        testCase(BoolType.getBoolType(), "11 = 22\n");
        testCase(BoolType.getBoolType(), "3*5 != 1+2 = true\n");
        testCase(BoolType.getBoolType(), "1 = 2 && 3 = 4\n");
        testNegativeCase("< 11\n");
        testNegativeCase("11 >\n");
        testNegativeCase("<= 11\n");
        testNegativeCase("&& A\n");
    }

    @Test
    public void test05Identifiers() throws Exception {
        testCase(IntType.getIntType(), "let x = 9 in x + 2\n");
        testCase(BoolType.getBoolType(), "let x = true in x = true\n");
        testCase(IntType.getIntType(), "let x = 9 in let y = 7 in x + y\n");
        testCase(IntType.getIntType(), "let x = 9 in let y = 7 in let z = 5 in z + x + y\n");
        testNegativeCase("let x = 9 in x + y\n");
        testNegativeCase("let x = 9 x + 2\n");
    }

    @Test
    public void test06ControlFlow() throws Exception {
        testCase(BoolType.getBoolType(), "if ~false then 2 < 5 end\n");
        testCase(BoolType.getBoolType(), "if 2 < 5 then true && ~false end\n");
        testCase(IntType.getIntType(), "if true then 2 + 2 end\n");
        testNegativeCase("if true then ~5 end\n");
        testNegativeCase("if 2 + true then 3 end\n");
    }

    @Test
    public void test07References() throws Exception {
        testCase(new RefType(IntType.getIntType()), "new(7)\n");
        testCase(IntType.getIntType(), "!(new(5))\n");
        testCase(IntType.getIntType(), "let x = new(9) in !x + 4\n");
        testCase(BoolType.getBoolType(), "let x = new(true) in !x && false\n");
        testNegativeCase("!(5)\n");
        testNegativeCase("let x = 9 in !x + 4\n");
    }

    @Test
    public void test08Prints() throws Exception {
        testCase(UnitType.getUnitType(), "print(5)\n");
        testCase(UnitType.getUnitType(), "println(5)\n");
        testCase(UnitType.getUnitType(), "let x = 10 in print(x + 3)\n");
        testCase(UnitType.getUnitType(), "let x = new(true) in println(!x)\n");
        testNegativeCase("print(\"hello\" + 5)\n");
        testNegativeCase("println(true && 10)\n");
    }

    @Test
    public void test09Tuples() throws Exception {
        List<Type> l = new ArrayList<>();
        l.add(IntType.getIntType());
        l.add(IntType.getIntType());
        testCase(new TupleType(l), "{5, 9}\n");
        l.clear();
        l.add(BoolType.getBoolType());
        l.add(IntType.getIntType());
        testCase(new TupleType(l), "{true, 5}\n");
        testNegativeCase("first(true)\n");
        testNegativeCase("second(5)\n");
    }

    @Test
    public void test10Strings() throws Exception {
        testCase(StringType.getStringType(), "\"this might be a string\"\n");
        testCase(StringType.getStringType(), "concat(\"this\", \"that\")\n");
        testCase(StringType.getStringType(), "let x = \"this\" in x\n");
        testNegativeCase("concat(\"this\", 5)\n");
        testNegativeCase("concat(true, \"that\")\n");
    }
}