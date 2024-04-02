package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


import ast.*;
import target.*;


public class CodeGen implements ast.Exp.Visitor<Void> {
	
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
