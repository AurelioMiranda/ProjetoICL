package typechecker;

import ast.*;
import types.*;

public class Typechecker {  //TODO: verify this

    public static Type typeCheck(Exp e) {
        if (e instanceof ASTInt) {
            return IntType.getIntType();
        } else if (e instanceof ASTAdd) {
            Exp arg2 = ((ASTAdd) e).arg2;
            Exp arg1 = ((ASTAdd) e).arg1;
            return getNumType(arg2, arg1);
        } else if (e instanceof ASTAnd) {
            Exp arg2 = ((ASTAnd) e).e1;
            Exp arg1 = ((ASTAnd) e).e2;
            return getBoolType(arg2, arg1);
        } else if (e instanceof ASTDiv) {
            Exp arg2 = ((ASTDiv) e).arg2;
            Exp arg1 = ((ASTDiv) e).arg1;
            return getNumType(arg2, arg1);
        } else if (e instanceof ASTEq) {
            Exp arg2 = ((ASTEq) e).e1;
            Exp arg1 = ((ASTEq) e).e2;
            return getEqType(arg2, arg1);
        } else if (e instanceof ASTGr) {
            Exp arg2 = ((ASTGr) e).e1;
            Exp arg1 = ((ASTGr) e).e2;
            return getIntBoolType(arg2, arg1);
        } else if (e instanceof ASTGrOrEq) {
            Exp arg2 = ((ASTGrOrEq) e).e1;
            Exp arg1 = ((ASTGrOrEq) e).e2;
            return getIntBoolType(arg2, arg1);
        } else if (e instanceof ASTLt) {
            Exp arg2 = ((ASTLt) e).e1;
            Exp arg1 = ((ASTLt) e).e2;
            return getIntBoolType(arg2, arg1);
        } else if (e instanceof ASTLTOrEq) {
            Exp arg2 = ((ASTLTOrEq) e).e1;
            Exp arg1 = ((ASTLTOrEq) e).e2;
            return getIntBoolType(arg2, arg1);
        } else if (e instanceof ASTMult) {
            Exp arg2 = ((ASTMult) e).arg2;
            Exp arg1 = ((ASTMult) e).arg1;
            return getNumType(arg2, arg1);
        } else if (e instanceof ASTNEq) {
            Exp arg2 = ((ASTNEq) e).e1;
            Exp arg1 = ((ASTNEq) e).e2;
            return getEqType(arg2, arg1);
        } else if (e instanceof ASTOr) {
            Exp arg2 = ((ASTOr) e).e1;
            Exp arg1 = ((ASTOr) e).e2;
            return getBoolType(arg2, arg1);
        } else if (e instanceof ASTSub) {
            Exp arg2 = ((ASTSub) e).arg2;
            Exp arg1 = ((ASTSub) e).arg1;
            return getNumType(arg2, arg1);
        }
        return NoneType.getNoneType();
    }

    private static Type getIntBoolType(Exp arg2, Exp arg1) {
        Type arg1Type = typeCheck(arg1);
        Type arg2Type = typeCheck(arg2);
        if (arg1Type instanceof IntType && arg2Type instanceof IntType) {
            return BoolType.getBoolType();
        } else {
            return NoneType.getNoneType();
        }
    }

    private static Type getBoolType(Exp arg2, Exp arg1) {
        Type arg1Type = typeCheck(arg1);
        Type arg2Type = typeCheck(arg2);
        if (arg1Type instanceof BoolType && arg2Type instanceof BoolType) {
            return BoolType.getBoolType();
        } else {
            return NoneType.getNoneType();
        }
    }

    private static Type getNumType(Exp arg2, Exp arg1) {
        Type arg1Type = typeCheck(arg1);
        Type arg2Type = typeCheck(arg2);
        if (arg1Type instanceof IntType && arg2Type instanceof IntType) {
            return IntType.getIntType();
        } else {
            return NoneType.getNoneType();
        }
    }

    private static Type getEqType(Exp arg2, Exp arg1) {
        Type arg1Type = typeCheck(arg1);
        Type arg2Type = typeCheck(arg2);
        if (arg1Type instanceof IntType && arg2Type instanceof IntType
                || arg1Type instanceof BoolType && arg2Type instanceof BoolType) {
            return BoolType.getBoolType();
        } else {
            return NoneType.getNoneType();
        }
    }
}
