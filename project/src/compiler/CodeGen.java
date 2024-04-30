package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


import ast.*;
import ast.arithmetic.ASTAdd;
import ast.arithmetic.ASTDiv;
import ast.arithmetic.ASTMult;
import ast.arithmetic.ASTSub;
import ast.control_flow.ASTElse;
import ast.control_flow.ASTIf;
import ast.control_flow.ASTWhile;
import ast.identifiers.ASTIdentifier;
import ast.identifiers.ASTLet;
import ast.logical.*;
import interpreter.Env;
import target.*;


public class CodeGen implements ast.Exp.Visitor<Void, Env<Void>> {
	
	BasicBlock block = new BasicBlock();
	
	
	@Override
	public Void visit(ASTInt i) {
		block.addInstruction(new SIPush(i.value) );
		return null;
	}


	@Override
	public Void visit(ASTMult e) {
	    e.arg1.accept(this);
	    e.arg2.accept(this);
	    block.addInstruction(new IMul());
		return null;
	}

	@Override
	public Void visit(ASTSub e) {
		return null;
	}

	@Override
	public Void visit(ASTEq astEq) {
		return null;
	}

	@Override
	public Void visit(ASTAnd astAnd) {
		return null;
	}

	@Override
	public Void visit(ASTOr astOr) {
		return null;
	}

	@Override
	public Void visit(ASTGr astGr) {
		return null;
	}

	@Override
	public Void visit(ASTLt astLt) {
		return null;
	}

	@Override
	public Void visit(ASTGrOrEq astGrOrEq) {
		return null;
	}

	@Override
	public Void visit(ASTLTOrEq astltOrEq) {
		return null;
	}

	@Override
	public Void visit(ASTNEq astneq) {
		return null;
	}

	@Override
	public Void visit(ASTBool astbool) {
		return null;
	}

	@Override
	public Void visit(ASTNeg astNeg) {
		return null;
	}

	@Override
	public Void visit(ASTIdentifier astIdentifier) { //TODO: teste
		return null;
	}

	@Override
	public Void visit(ASTNew astNew) {
		return null;
	}

	@Override
	public Void visit(ASTLet astLet) { //TODO: teste
		return null;
	}

	@Override
	public Void visit(ASTIf astIf) {
		return null;
	}

	@Override
	public Void visit(ASTElse astElse) {
		return null;
	}

	@Override
	public Void visit(ASTWhile astWhile) {
		return null;
	}

	@Override
	public Void visit(ASTAdd e) {
		//e.arg1().accept(this);
		//e.arg2().accept(this);
		//block.addInstruction(new IAdd());
		return null;
	}

	@Override
	public Void visit(ASTDiv e) {
		return null;
	}
	
	public static BasicBlock codeGen(Exp e) {
		CodeGen cg = new CodeGen();
		e.accept(cg);
		return cg.block;
	}
	
	
	private static StringBuilder genPreAndPost(BasicBlock block) {
		String preamble = """
					  .class public Demo
					  .super java/lang/Object 
					  .method public <init>()V
					     aload_0
					     invokenonvirtual java/lang/Object/<init>()V
					     return
					  .end method
					  .method public static main([Ljava/lang/String;)V
					   .limit locals 10
					   .limit stack 256
					   ; setup local variables:
					   ;    1 - the PrintStream object held in java.lang.out
					  getstatic java/lang/System/out Ljava/io/PrintStream;					          
				   """;
		String footer = 
				"""
				invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
				invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
				return
				.end method
				""";
		StringBuilder sb = new StringBuilder(preamble);
		block.build(sb);
		sb.append(footer);
		return sb;
			
	}
	
	public static void writeToFile(Exp e, String filename) throws FileNotFoundException {
	    StringBuilder sb = genPreAndPost(codeGen(e));
	    PrintStream out = new PrintStream(new FileOutputStream(filename));
	    out.print(sb.toString());
	    out.close();
		
	}

}
