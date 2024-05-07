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
import target.arithmetic.IAdd;
import target.arithmetic.IDiv;
import target.arithmetic.IMul;
import target.arithmetic.ISub;
import target.compare.*;
import target.logical.IAnd;
import target.logical.IOr;


public class CodeGen implements ast.Exp.Visitor<Void, Env<Void>> {
	
	BasicBlock block = new BasicBlock();
	
	
	@Override
	public Void visit(ASTInt i) {
		block.addInstruction(new SIPush(i.value) );
		return null;
	}

	@Override
	public Void visit(ASTAdd e) {
		e.arg1.accept(this);
		e.arg2.accept(this);
		block.addInstruction(new IAdd());
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
		e.arg1.accept(this);
		e.arg2.accept(this);
		block.addInstruction(new ISub());
		return null;
	}

	@Override
	public Void visit(ASTDiv e) {
		e.arg1.accept(this);
		e.arg2.accept(this);
		block.addInstruction(new IDiv());
		return null;
	}

	@Override
	public Void visit(ASTBool astbool) {
		int value = astbool.value ? 1 : 0;
		block.addInstruction(new SIPush(value) );
		return null;
	}

	@Override
	public Void visit(ASTAnd astAnd) {
		astAnd.e1.accept(this);
		astAnd.e2.accept(this);
		block.addInstruction(new IAnd());
		return null;
	}

	@Override
	public Void visit(ASTOr astOr) {
		astOr.e1.accept(this);
		astOr.e2.accept(this);
		block.addInstruction(new IOr());
		return null;
	}

	@Override
	public Void visit(ASTEq astEq) {
		astEq.e1.accept(this);
		astEq.e2.accept(this);
		block.addInstruction(new IEq());
		return null;
	}

	@Override
	public Void visit(ASTGr astGr) {
		astGr.e1.accept(this);
		astGr.e2.accept(this);
		block.addInstruction(new IGr());
		return null;
	}

	@Override
	public Void visit(ASTLt astLt) {
		astLt.e1.accept(this);
		astLt.e2.accept(this);
		block.addInstruction(new ILt());
		return null;
	}

	@Override
	public Void visit(ASTGrOrEq astGrOrEq) {
		astGrOrEq.e1.accept(this);
		astGrOrEq.e2.accept(this);
		block.addInstruction(new IGrOrEq());
		return null;
	}

	@Override
	public Void visit(ASTLTOrEq astltOrEq) {
		astltOrEq.e1.accept(this);
		astltOrEq.e2.accept(this);
		block.addInstruction(new ILtOrEq());
		return null;
	}

	@Override
	public Void visit(ASTNEq astneq) {
		astneq.e1.accept(this);
		astneq.e2.accept(this);
		block.addInstruction(new INEq());
		return null;
	}

	@Override
	public Void visit(ASTNeg astNeg) {
		return null;
	}

	@Override
	public Void visit(ASTIdentifier astIdentifier) {
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
