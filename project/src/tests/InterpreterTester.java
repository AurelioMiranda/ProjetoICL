package tests;

import org.junit.Assert;
import org.junit.Test;

import main.Console;
import parser.ParseException;
import values.*;


public class InterpreterTester {

    private void testCase(Value expected, String expression) throws ParseException {
        if (expected instanceof UnitValue) {
            if (Console.accept(expression) instanceof UnitValue) {
                Assert.assertTrue(true);
                return;
            }
            Assert.fail();
        }
        Assert.assertEquals(expected, Console.accept(expression));
    }

    private void testNegativeCase(String expression) throws ParseException {
        Assert.assertEquals(Console.accept(expression), new BoolValue(false));
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
        testNegativeCase("let x = 9 in x + y \n");
        testNegativeCase("let x = 9 x + 2 \n");
        testNegativeCase("let( x = 9 in x + 2 ) \n");
        testNegativeCase("let 1variable = 9 in 1variable + 2 \n");
        testNegativeCase("let x = 9 in x || 2 \n");
        testNegativeCase("let x = in x + 2 \n");
        testNegativeCase("let x = 9 x + 2 \n");
        testNegativeCase("let x = 9 \n");
        testNegativeCase("let x = 9 in let y = 7 x + y \n");
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
        testNegativeCase("if true then ~5 end \n");
        testNegativeCase("let x = 5 in x && true \n");
        testNegativeCase("if 2 + true then 3 end \n");
        testNegativeCase("if false then 2 + 2 else ~5 end \n");
        testNegativeCase("if 3 < 2 then true + false end \n");
        testNegativeCase("let x = true in x + 1 \n");
        testNegativeCase("if 2 < 5 then 3 && 4 end \n");
        testNegativeCase("if true then let x = 5 in x && true end \n");
        testNegativeCase("if 2 < 5 then let x = 9 in 5 && x end \n");
        testNegativeCase("let x = false in if x != 8 then x + 1 end \n");
        testNegativeCase("if false then 2 + 2 else true && 5 end \n");
        testNegativeCase("if true then 2 < 5 else 5 + false end \n");
    }

    @Test
    public void test_references() throws Exception {
        testCase(new RefValue(2), "new(7) \n");
        testCase(new IntValue(5), "!(new(5)) \n");
        testCase(new IntValue(13), "let x = new(9) in !x + 4 \n");
        testCase(new BoolValue(false), "let x = new(true) in !x && false \n");
        testCase(new IntValue(3), "let x = new(5) in x:=3 \n");
        //testCase(new IntValue(4), "let x = fun (z:ref) -> !z in x(new(4)) \n"); // references + functions = bad
        testNegativeCase("!(5) \n");
        testNegativeCase("let x = 9 in !x + 4 \n");
        testNegativeCase("let x = new(5) in x + true \n");
        testNegativeCase("let x = new(5) in !x && 4 \n");
        testNegativeCase("let x = new(true) in !x + 5 \n");
        testNegativeCase("new(true) + 3 \n");
        testNegativeCase("new(5) && false \n");
    }

    @Test
    public void test_prints() throws Exception {
        testCase(new UnitValue(), "print(5) \n");
        testCase(new UnitValue(), "println(5) \n");
        testCase(new UnitValue(), "let x = 10 in print(x + 3) \n");
        testCase(new UnitValue(), "let x = new(true) in println(!x) \n");
        testCase(new UnitValue(), "println(\"hello\") \n");
        testCase(new UnitValue(), "let x = 3 in print(x * 2) \n");
        testCase(new UnitValue(), "let x = 5 in let y = 10 in println(x + y) \n");
        testCase(new UnitValue(), "println(new(\"not a number\")) \n");
        testCase(new UnitValue(), "print(if true then false end) \n");
        testNegativeCase("print(\"hello\" + 5) \n");
        testNegativeCase("println(true && 10) \n");
        testNegativeCase("let x = 5 in print(x + true) \n");
        testNegativeCase("let x = new(10) in println(!x + \"text\") \n");
        testNegativeCase("print(if false then 2 else \"wrong\" end) \n");
        testNegativeCase("let x = \"string\" in print(x + 2) \n");
    }

    @Test
    public void test_functions() throws Exception {
        testCase(new IntValue(5), "let x = fun (y:int) -> y+2 in x(3) \n");
        testCase(new BoolValue(false), "let x = fun (z:bool) -> z&&true in x(false) \n");
        testCase(new BoolValue(true), "let x = fun (z:bool) -> z||false in x(true) \n");
        testCase(new UnitValue(), "let x = fun (z:bool) -> if 2<5&&z then let x = 9 in 5 + x end in x(false) \n");
        testCase(new IntValue(14), "let x = fun (z:bool) -> if 2<5&&z then let x = 9 in 5 + x end in x(true) \n");
        testCase(new IntValue(5), "let x = fun (z:bool) -> if z then 1 else 2+2 end in if x(true)<5 then 1+4 else 5 end \n");
        testNegativeCase("let x = fun (y:int) -> y + true in x(3) \n");
        testNegativeCase("let x = fun (z:bool) -> z + 1 in x(false) \n");
        testNegativeCase("let x = fun (z:int) -> z && true in x(3) \n");
        testNegativeCase("let x = fun (y:int) -> y + 2 in x(true) \n");
        testNegativeCase("let x = fun (y:int) -> y + 2 in x(3) && false \n");
    }

    @Test
    public void test_tuples() throws Exception {
        testCase(new IntValue(5), "first{5,9} \n");
        testCase(new IntValue(5), "second{true,5} \n");
        testCase(new BoolValue(false), "second{5+5,true&&false} \n");
        testCase(new IntValue(5), "let x = {5,6} in first(x) \n");
        testCase(new BoolValue(true), "let x = {5, {false, true}} in second second(x) \n");
        testCase(new IntValue(7), "let x = {5, {{5, {false, 7}}, true}} in second second first second(x) \n");
        testCase(new IntValue(9), "let x = {2, 3, 4} in match x with y, d, c -> y + d + c \n");
        testCase(new IntValue(8), "let x = {2, 3, 4} in match x with y, d, c -> if y < d then c + c end \n");
        testCase(new IntValue(-1), "let x = {2, 3, false} in match x with y, d, c -> if c then y + d else y - d end \n");
        testCase(new IntValue(5), "let x = {2, 3, true} in match x with y, d, c -> if c then y + d else y - d end \n");
        testCase(new IntValue(5), "let x = {2, 3, true} in match x with y, d, c -> if c then y + d else y - d end \n");
        testNegativeCase("let x = {5, {false, true}} in first(second(x)) \n");
        testNegativeCase("let x = {5, {{5, {false, 7}}, true}} in first(second(first(second(x)))) \n");
        testNegativeCase("let x = {2, true, 4} in match x with y, d, c -> y + d + c \n");
        testNegativeCase("let x = {2, 3, 4} in match x with y, d -> y + d \n");
    }

    @Test
    public void test_strings() throws Exception {
        testCase(new StringValue("this might be a string idk #$%&(%=%/(/*-+;:3748 59 2"),
                "\"this might be a string idk #$%&(%=%/(/*-+;:3748 59 2\" \n");
        testCase(new StringValue("thisthat"), "concat(\"this\", \"that\") \n");
        testCase(new StringValue("this"), "let x = \"this\" in x \n");
        testCase(new StringValue("thisdat"), "let x = \"this\" in concat(x,\"dat\") \n");
        testCase(new StringValue("thisnotthat"), "let x = fun (z:string) -> concat(z,\"notthat\") in x(\"this\") \n");
        testCase(new StringValue("disthat"), "let x = {\"dis\", \"that\"} in match x with y, z -> concat(y, z) \n");
        TupleValue tv = new TupleValue();
        tv.addValue(new StringValue("this"));
        tv.addValue(new StringValue("is"));
        tv.addValue(new StringValue("not"));
        tv.addValue(new StringValue("a"));
        tv.addValue(new StringValue("string"));
        testCase(tv, "let x = \"this is not a string\" in split(x, \" \") \n");
        testNegativeCase("concat(\"this\", 5) \n");
        testNegativeCase("concat(true, \"that\") \n");
        testNegativeCase("let x = 5 in concat(x, \"dat\") \n");
        testNegativeCase("let x = fun (z:int) -> concat(z,\"notthat\") in x(3) \n");
        testNegativeCase("split(\"stringy string\", 1) \n");
    }
}









