package typechecker;

import ast.*;
import types.*;

public class Typechecker {

    public static Type typeCheck(Exp e) {
        if (e instanceof ASTInt) {
            return IntType.getIntType();
        } else if (e instanceof ASTAnd || e instanceof ASTOr) { // this doesn't seem right (ex. eq is bool..)
            return BoolType.getBoolType(); //TODO: fix this s#$t (both arguments are needed........)
        }// else if (e instanceof ASTAdd || e instanceof ASTSub || e instanceof ASTMult || e instanceof ASTDiv
        //        || e instanceof ASTEq || e instanceof ASTGr || e instanceof ASTGrOrEq || e instanceof ASTLt
        //        || e instanceof ASTLTOrEq || e instanceof ASTNEq) {
        //    Exp arg2 = e.arg2;
        //    Exp arg1 = e.arg1;
        //    Type arg1Type = typeCheck(arg1);
        //    Type arg2Type = typeCheck(arg2);
        //    if (arg1Type instanceof IntType && arg2Type instanceof IntType) {
        //        return IntType.getIntType();
        //    } else {
        //        throw new ArithmeticException("Arithmetic operation requires operands of the same numeric type.");
        //    }
        //}
        return NoneType.getNoneType();
    }
}
