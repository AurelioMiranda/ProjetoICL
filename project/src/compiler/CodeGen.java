package compiler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;

import ast.tuples.*;
import ast.references.*;
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
import target.control_flow.GoTo;
import target.control_flow.Label;
import target.control_flow.NOP;
import target.identifiers.*;
import target.logical.IAnd;
import target.logical.IOr;
import target.memory.IAlloc;
import target.memory.ILoad;
import target.memory.IStore;


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
		block.addInstruction(new INEq());
		return getComparisonWithLabels();
	}

	@Override
	public Void visit(ASTGr astGr) {
		astGr.e1.accept(this);
		astGr.e2.accept(this);
		block.addInstruction(new IGr());
		return getComparisonWithLabels();
	}

	@Override
	public Void visit(ASTLt astLt) {
		astLt.e1.accept(this);
		astLt.e2.accept(this);
		block.addInstruction(new ILt());
		return getComparisonWithLabels();
	}

	@Override
	public Void visit(ASTGrOrEq astGrOrEq) {
		astGrOrEq.e1.accept(this);
		astGrOrEq.e2.accept(this);
		block.addInstruction(new IGrOrEq());
		return getComparisonWithLabels();
	}

	@Override
	public Void visit(ASTLTOrEq astLtOrEq) {
		astLtOrEq.e1.accept(this);
		astLtOrEq.e2.accept(this);
		block.addInstruction(new ILtOrEq());
		return getComparisonWithLabels();
	}

	@Override
	public Void visit(ASTNEq astNEq) {
		astNEq.e1.accept(this);
		astNEq.e2.accept(this);
		block.addInstruction(new INEq());
		return getComparisonWithLabels();
	}

	@Override
	public Void visit(ASTNeg astNeg) {
		astNeg.e.accept(this);
		Label L1 = new Label();
		Label L2 = new Label();
		block.addInstruction(new SIPush(0));
		block.addInstruction(new INEq());
		return getLabelInstructions(L1, L2);
	}


	@Override
	public Void visit(ASTIdentifier astIdentifier) {
		block.addInstruction(new ALoad(0/*astIdentifier.getName()*/)); //TODO: fix this
		return null;
	}

	@Override
	public Void visit(ASTLet astLet) {
		String frameId = generateFrameId();
		block.addInstruction(new New(frameId));
		block.addInstruction(new Dup());
		block.addInstruction(new InvokeSpecial(frameId + "/<init>()V"));
		block.addInstruction(new Dup());
		block.addInstruction(new ALoad(0));
		block.addInstruction(new PutField(frameId + "/SL", "Lframe_prev;"));
		block.addInstruction(new AStore(0));

		int i = 0;
		for (Map.Entry<String, Exp> entry : astLet.variables.entrySet()) {
			entry.getValue().accept(this);
			block.addInstruction(new ALoad(0));
			block.addInstruction(new PutField(frameId + "/loc_" + i, getType(entry.getValue())));
			i++;
		}

		astLet.body.accept(this);

		block.addInstruction(new ALoad(0));
		block.addInstruction(new CheckCast(frameId));
		block.addInstruction(new GetField(frameId + "/SL", "Lframe_prev;"));
		block.addInstruction(new AStore(0));
		return null;
	}

	@Override
	public Void visit(ASTNew astNew) {
		astNew.expression.accept(this);
		block.addInstruction(new IAlloc());
		block.addInstruction(new IStore());

		return null;
	}


	@Override
	public Void visit(ASTDeref astDeref) {
		astDeref.expression.accept(this);
		block.addInstruction(new ILoad());

		return null;
	}


	@Override
	public Void visit(ASTPair astPair) {
		return null;
	}

	@Override
	public Void visit(ASTFirst astFirst) {
		return null;
	}

	@Override
	public Void visit(ASTSecond astSecond) {
		return null;
	}

	@Override
	public Void visit(ASTLast astLast) {
		return null;
	}

	@Override
	public Void visit(ASTMatch astMatch) {
		return null;
	}

	@Override
	public Void visit(ASTIf astIf) {
		astIf.condition.accept(this);
		Label endLabel = new Label();
		//block.addInstruction(new IIfFalse(endLabel));
		astIf.body.accept(this);
		block.addInstruction(endLabel);
		return null;
	}

	@Override
	public Void visit(ASTElse astElse) {
		astElse.condition.accept(this);
		Label elseLabel = new Label();
		Label endLabel = new Label();
		//block.addInstruction(new IIfFalse(elseLabel)); // Jump to elseLabel if condition is false
		astElse.ifBody.accept(this);
		block.addInstruction(new GoTo(endLabel));
		block.addInstruction(elseLabel);
		astElse.elseBody.accept(this);
		block.addInstruction(endLabel);
		return null;
	}


	@Override
	public Void visit(ASTWhile astWhile) {
		return null;
	}

	@Override
	public Void visit(ASTClosure astClosure) {
		return null;
	}

	@Override
	public Void visit(ASTCall astCall) {
		return null;
	}

	@Override
	public Void visit(ASTAssign astAssign) {
		astAssign.getRhs().accept(this);
		astAssign.getLhs().accept(this);
		block.addInstruction(new IStore()); 

		return null;
	}


	public static BasicBlock codeGen(Exp e) {
		CodeGen cg = new CodeGen();
		e.accept(cg);
		System.out.println(cg.block);
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

	private Void getComparisonWithLabels() {
		Label L1 = new Label();
		Label L2 = new Label();
		return getLabelInstructions(L1, L2);
	}

	private Void getLabelInstructions(Label l1, Label l2) {
		block.addInstruction(new SIPush(1));
		block.addInstruction(new GoTo(l2));
		block.addInstruction(l1);
		block.addInstruction(new SIPush(0));
		block.addInstruction(l2);
		block.addInstruction(new NOP());
		return null;
	}

	private String generateFrameId() {
		return "frame_" + System.currentTimeMillis();
	}

	private String getType(Exp exp) {
		if (exp instanceof ASTInt) {
			return "I";
		} else if (exp instanceof ASTBool) {
			return "Z";
		}
		return "L";
	}
	
	public static void writeToFile(Exp e, String filename) throws FileNotFoundException {
	    StringBuilder sb = genPreAndPost(codeGen(e));
	    PrintStream out = new PrintStream(new FileOutputStream(filename));
	    out.print(sb.toString());
	    out.close();
		
	}

}
