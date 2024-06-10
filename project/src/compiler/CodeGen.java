package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;

import ast.print.ASTPrint;
import ast.print.ASTPrintln;
import ast.string.ASTConcat;
import ast.string.ASTSplit;
import ast.string.ASTString;
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
import target.control_flow.*;
import target.getters.SoutPrintStream;
import target.identifiers.*;
import target.invokes.Print;
import target.invokes.PrintLn;
import target.invokes.typeToString;
import target.logical.IAnd;
import target.logical.IOr;
import target.memory.IAlloc;
import target.memory.ILoad;
import target.memory.IStore;
import typechecker.Typechecker;
import types.BoolType;
import types.IntType;
import types.Type;


public class CodeGen implements ast.Exp.Visitor<Void, Env<Void>> {

    BasicBlock block = new BasicBlock();

    public CompEnv env;

    @Override
    public Void visit(ASTInt i) {
        block.addInstruction(new SIPush(i.value));
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
        block.addInstruction(new SIPush(value));
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
        String l1 = NameGenerator.genLabel();
        Label L1 = new Label(l1);
        block.addInstruction(new INEq(L1));
        return addCompInstruction(L1);
    }

    @Override
    public Void visit(ASTGr astGr) {
        astGr.e1.accept(this);
        astGr.e2.accept(this);
        String l1 = NameGenerator.genLabel();
        Label L1 = new Label(l1);
        block.addInstruction(new IGr(l1));
        return addCompInstruction(L1);
    }

    @Override
    public Void visit(ASTLt astLt) {
        astLt.e1.accept(this);
        astLt.e2.accept(this);
        String l1 = NameGenerator.genLabel();
        Label L1 = new Label(l1);
        block.addInstruction(new ILt(L1));
        return addReverseCompInstruction(L1);
    }

    @Override
    public Void visit(ASTGrOrEq astGrOrEq) {
        astGrOrEq.e1.accept(this);
        astGrOrEq.e2.accept(this);
        String l1 = NameGenerator.genLabel();
        Label L1 = new Label(l1);
        block.addInstruction(new IGrOrEq(L1));
        return addReverseCompInstruction(L1);
    }

    @Override
    public Void visit(ASTLTOrEq astLtOrEq) {
        astLtOrEq.e1.accept(this);
        astLtOrEq.e2.accept(this);
        String l1 = NameGenerator.genLabel();
        Label L1 = new Label(l1);
        block.addInstruction(new ILtOrEq(L1));
        return addReverseCompInstruction(L1);
    }

    @Override
    public Void visit(ASTNEq astNEq) {
        astNEq.e1.accept(this);
        astNEq.e2.accept(this);
        String l1 = NameGenerator.genLabel();
        Label L1 = new Label(l1);
        block.addInstruction(new IEq(L1));
        return addCompInstruction(L1);
    }

    @Override
    public Void visit(ASTNeg astNeg) {
        astNeg.e.accept(this);
        block.addInstruction(new SIPush(-1));
        block.addInstruction(new IXor());
        block.addInstruction(new SIPush(1));
        block.addInstruction(new IAnd());
        return null;
    }

    @Override
    public Void visit(ASTIf astIf) {
        astIf.condition.accept(this);
        block.addInstruction(new SIPush(1));
        String l0 = NameGenerator.genLabel();
        Label L0 = new Label(l0);
        block.addInstruction(new INEq(L0));
        astIf.body.accept(this);
        block.addInstruction(L0);
        block.addInstruction(new NOP());
        return null;
    }

    @Override
    public Void visit(ASTElse astElse) {
        astElse.condition.accept(this);
        block.addInstruction(new SIPush(0));
        String l0 = NameGenerator.genLabel();
        Label L0 = new Label(l0);
        block.addInstruction(new INEq(L0));
        astElse.elseBody.accept(this);
        String l1 = NameGenerator.genLabel();
        Label L1 = new Label(l1);
        block.addInstruction(new GoTo(L1));
        block.addInstruction(L0);
        astElse.ifBody.accept(this);
        block.addInstruction(L1);
        return null;
    }

    @Override
    public Void visit(ASTIdentifier astIdentifier) {
        //block.addInstruction(new ALoad(0/*astIdentifier.getName()*/)); //TODO: fix this
        return null;
    }

    @Override
    public Void visit(ASTLet astLet) {
        String frameId = generateFrameId();
        String type = getType(astLet.type);

        try {
            generateFrameFile(frameId, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String newFrameId = "frame_" + frameId;

        block.addInstruction(new New(newFrameId));
        block.addInstruction(new Dup());
        block.addInstruction(new InvokeSpecial(newFrameId + "/<init>()V"));
        block.addInstruction(new Dup());
        block.addInstruction(new ALoad(0));
        block.addInstruction(new PutField(newFrameId + "/SL", "Ljava/lang/Object;"));
        block.addInstruction(new AStore(0));


        int i = 0;
        for (Map.Entry<String, Exp> entry : astLet.variables.entrySet()) {
            block.addInstruction(new ALoad(0));
            entry.getValue().accept(this);
            block.addInstruction(new PutField(newFrameId + "/loc_" + i, type));
            i++;
        }

        block.addInstruction(new ALoad(0));
        block.addInstruction(new GetField(newFrameId + "/loc_0", type));

        astLet.body.accept(this);

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
    public Void visit(ASTPrint astPrint) {
        String type = "I";//getType(astPrint.exp);
        astPrint.exp.accept(this);
        block.addInstruction(new typeToString(type));
        block.addInstruction(new Print());
        return null;
    }

    @Override
    public Void visit(ASTPrintln astPrintln) {
        String type = getType(astPrintln.type);
        astPrintln.exp.accept(this);
        block.addInstruction(new PrintLn(type));
        return null;
    }

    @Override
    public Void visit(ASTSeq astSeq) {
        return null;
    }

    @Override
    public Void visit(ASTSplit astSplit) {
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
    public Void visit(ASTString astString) {
        return null;
    }

    @Override
    public Void visit(ASTConcat astConcat) {
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
                        return
                        .end method
                        """;
        StringBuilder sb = new StringBuilder(preamble);
        block.build(sb);
        sb.append(footer);
        return sb;

    }

    private Void addCompInstruction(Label l1) {
        block.addInstruction(new SIPush(1));
        String l2 = NameGenerator.genLabel();
        Label L2 = new Label(l2);
        block.addInstruction(new GoTo(L2));
        block.addInstruction(l1);
        block.addInstruction(new SIPush(0));
        block.addInstruction(L2);
        block.addInstruction(new NOP());
        return null;
    }

    private Void addReverseCompInstruction(Label l1) {
        block.addInstruction(new SIPush(0));
        String l2 = NameGenerator.genLabel();
        Label L2 = new Label(l2);
        block.addInstruction(new GoTo(L2));
        block.addInstruction(l1);
        block.addInstruction(new SIPush(1));
        block.addInstruction(L2);
        block.addInstruction(new NOP());
        return null;
    }

    private String generateFrameId() {
        return "0";// + System.currentTimeMillis();
    }

    private String getType(Type t) {
        if (t instanceof IntType) {
            return "I";
        } else if (t instanceof BoolType) {
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

    public void generateFrameFile(String id, String type) throws FileNotFoundException {
        String directoryPath = System.getProperty("user.dir");

        String frame = ".class public frame_" + id + "\n" +
                ".super java/lang/Object\n" +
                ".field public SL Ljava/lang/Object;\n" +
                ".field public loc_0 " + type +
                "\n.method public <init>()V\n" +
                "aload_0\n" +
                "invokenonvirtual java/lang/Object/<init>()V\n" +
                "return\n" +
                ".end method\n";

        String filePath = directoryPath + File.separator + "frame_0.txt";
        PrintStream out = new PrintStream(new FileOutputStream(filePath));
        out.print(frame);
        out.close();
    }

}
