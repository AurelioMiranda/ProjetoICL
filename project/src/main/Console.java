package main;

import java.io.ByteArrayInputStream;

import ast.Exp;

import interpreter.*;
import parser.*;
import parser.Parser;
import typechecker.Typechecker;
import types.Type;
import values.Value;

public class Console {

    @SuppressWarnings("static-access")
    public static void main(String args[]) {
        Parser parser = new Parser(System.in);

        while (true) {
            try {
                Exp e = parser.Start();
                Type t = Typechecker.typeCheck(e);
                System.out.println("Type: " + t);
                if (true) {
                    Value v = Interpreter.interpret(e, new Env<>());
                    System.out.println("Result: " + v);
                } else {
                    System.out.println("Non computable.");
                }
            } catch (TokenMgrError e) {
                System.out.println("Lexical Error!");
                e.printStackTrace();
                parser.ReInit(System.in);
            } catch (ParseException e) {
                System.out.println("Syntax Error!");
                e.printStackTrace();
                parser.ReInit(System.in);
            }
        }
    }

    public static boolean accept(String s) throws ParseException {
        Parser parser = new Parser(new ByteArrayInputStream(s.getBytes()));
        try {
            parser.Start();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
