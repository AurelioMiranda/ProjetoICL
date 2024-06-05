package main;

import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ast.Exp;

import interpreter.*;
import parser.*;
import parser.Parser;
import typechecker.Typechecker;
import types.BoolType;
import types.FunType;
import types.IntType;
import types.Type;
import values.BoolValue;
import values.Value;

public class Console {

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        Parser parser;
        if (args.length > 0) {
            String filePath = args[0];
            String code;
            try {
                code = readFile(filePath);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
                return;
            }
            parser = new Parser(new ByteArrayInputStream(code.getBytes()));
        } else {
            parser = new Parser(System.in);
        }

        while (true) {
            try {
                Exp e = parser.Start();
                Type t = Typechecker.typeCheck(e, new Env<>());
                System.out.println("Type: " + t);
                if (!t.toString().equals("None")) {
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

    private static String readFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder code = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                code.append(line).append("\n");
            }
            return code.toString();
        }
    }

    public static Value accept(String s) throws ParseException {
        Parser parser = new Parser(new ByteArrayInputStream(s.getBytes()));
        try {
            Exp e = parser.Start();
            Type t = Typechecker.typeCheck(e, new Env<>());
            if (!t.toString().equals("None")) {
                return Interpreter.interpret(e, new Env<>());
            } else {
                return new BoolValue(false);
            }
        } catch (Exception e) {
            return new BoolValue(false);
        }
    }
}
