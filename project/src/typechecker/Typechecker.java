package typechecker;

import ast.*;
import ast.arithmetic.ASTAdd;
import ast.arithmetic.ASTDiv;
import ast.arithmetic.ASTMult;
import ast.arithmetic.ASTSub;
import ast.logical.*;
import interpreter.Env;
import types.*;
import values.Value;

public class Typechecker implements Exp.Visitor<Type, Env<Type>> { //TODO: same Env as interpreter?
    private static Env<Type> env;

    public static Type typeCheck(Exp e, Env<Type> env) {
        Typechecker t = new Typechecker();
        Typechecker.env = env;
        return e.accept(t);
    }

    private static Type getIntBoolType(Exp arg2, Exp arg1) {
        Type arg1Type = typeCheck(arg1, env);
        Type arg2Type = typeCheck(arg2, env);
        if (arg1Type instanceof IntType && arg2Type instanceof IntType) {
            return BoolType.getBoolType();
        } else {
            return NoneType.getNoneType();
        }
    }

    private static Type getBoolType(Exp arg2, Exp arg1) {
        Type arg1Type = typeCheck(arg1, env);
        Type arg2Type = typeCheck(arg2, env);
        if (arg1Type instanceof BoolType && arg2Type instanceof BoolType) {
            return BoolType.getBoolType();
        } else {
            return NoneType.getNoneType();
        }
    }

    private static Type getNumType(Exp arg2, Exp arg1) {
        Type arg1Type = typeCheck(arg1, env);
        Type arg2Type = typeCheck(arg2, env);
        if (arg1Type instanceof IntType && arg2Type instanceof IntType) {
            return IntType.getIntType();
        } else {
            return NoneType.getNoneType();
        }
    }

    private static Type getEqType(Exp arg2, Exp arg1) {
        Type arg1Type = typeCheck(arg1, env);
        Type arg2Type = typeCheck(arg2, env);
        if ((arg1Type instanceof IntType && arg2Type instanceof IntType)
                || (arg1Type instanceof BoolType && arg2Type instanceof BoolType)) {
            return BoolType.getBoolType();
        } else {
            return NoneType.getNoneType();
        }
    }

    @Override
    public Type visit(ASTAdd i) {
        return getNumType(i.arg1, i.arg2);
    }

    @Override
    public Type visit(ASTDiv i) {
        return getNumType(i.arg1, i.arg2);
    }

    @Override
    public Type visit(ASTInt i) {
        return IntType.getIntType();
    }

    @Override
    public Type visit(ASTMult e) {
        return getNumType(e.arg1, e.arg2);
    }

    @Override
    public Type visit(ASTSub e) {
        return getNumType(e.arg1, e.arg2);
    }

    @Override
    public Type visit(ASTEq astEq) {
        return getEqType(astEq.e1, astEq.e2);
    }

    @Override
    public Type visit(ASTAnd astAnd) {
        return getBoolType(astAnd.e1, astAnd.e2);
    }

    @Override
    public Type visit(ASTOr astOr) {
        return getBoolType(astOr.e1, astOr.e2);
    }

    @Override
    public Type visit(ASTGr astGr) {
        return getIntBoolType(astGr.e1, astGr.e2);
    }

    @Override
    public Type visit(ASTLt astLt) {
        return getIntBoolType(astLt.e1, astLt.e2);
    }

    @Override
    public Type visit(ASTGrOrEq astGrOrEq) {
        return getIntBoolType(astGrOrEq.e1, astGrOrEq.e2);
    }

    @Override
    public Type visit(ASTLTOrEq astltOrEq) {
        return getIntBoolType(astltOrEq.e1, astltOrEq.e2);
    }

    @Override
    public Type visit(ASTNEq astneq) {
        return getEqType(astneq.e1, astneq.e2);
    }

    @Override
    public Type visit(ASTBool astbool) {
        return BoolType.getBoolType();
    }

    @Override
    public Type visit(ASTNeg astNeg) {
        Type t = typeCheck(astNeg.e, env);
        if (t instanceof BoolType) {
            return BoolType.getBoolType();
        }
        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTIdentifier astIdentifier) { //TODO: identifiers
        return env.find(astIdentifier.getName());
    }

    @Override
    public Type visit(ASTNew astNew) { //TODO: references
        return null;
    }

    @Override
    public Type visit(ASTLet astLet) { //TODO: identifiers
        Type t1 = typeCheck(astLet.variableValue, env);
        env = env.beginScope();
        env.bind(astLet.variableName, t1);
        Type t = typeCheck(astLet.body, env);
        env = env.endScope();
        return t;
    }
}
