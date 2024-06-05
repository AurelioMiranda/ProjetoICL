package main;

import java.io.*;

import ast.Exp;

import compiler.CodeGen;
import interpreter.*;
import parser.*;
import parser.Parser;
import typechecker.Typechecker;
import types.BoolType;
import types.FunType;
import types.IntType;
import types.Type;
import values.Value;

public class GenerateCode {

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        Parser parser;

        parser = new Parser(System.in);

        while (true) {
            try {
                Exp e = parser.Start();
                Type t = Typechecker.typeCheck(e, new Env<>());
                System.out.println("Type: " + t);
                if (!t.toString().equals("None")) {
                    CodeGen.writeToFile(e, "result.txt");
                    System.out.println("Written to file successfully");
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
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
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
