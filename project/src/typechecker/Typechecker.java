package typechecker;

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
import ast.references.ASTAssign;
import ast.references.ASTDeref;
import ast.references.ASTNew;
import interpreter.Env;
import types.*;


public class Typechecker implements Exp.Visitor<Type, Env<Type>> {
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
    public Type visit(ASTIdentifier astIdentifier) {
        return env.find(astIdentifier.getName());
    }

    @Override
    public Type visit(ASTNew astNew) { //TODO: references
        // Type check the expression E
        Type exprType = typeCheck(astNew.expression, env);

        // Return a RefType of the expression's type
        return new RefType(exprType);
    }

    @Override
    public Type visit(ASTDeref astDeref) {
        // Type check the expression E
        Type exprType = typeCheck(astDeref.expression, env);

        // Ensure the expression is a RefType
        if (exprType instanceof RefType) {
            // Return the referenced type
            return ((RefType) exprType).getReferencedType();
        } else {
            // Dereferencing a non-reference type is an error
            throw new RuntimeException("Dereference operation requires a reference type.");
        }    }

    @Override
    public Type visit(ASTLet astLet) {
        Type t1 = typeCheck(astLet.variableValue, env);
        env = env.beginScope();
        env.bind(astLet.variableName, t1);
        Type t = typeCheck(astLet.body, env);
        env = env.endScope();
        return t;
    }

    @Override
    public Type visit(ASTIf astIf) {
        Type cond = typeCheck(astIf.condition, env);
        if (cond instanceof BoolType){
            return typeCheck(astIf.body, env);
        }
        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTElse astElse) {
        Type cond = typeCheck(astElse.condition, env);
        if (cond instanceof BoolType){
            Type ifBody = typeCheck(astElse.ifBody, env);
            Type elseBody = typeCheck(astElse.elseBody, env);
            if (ifBody == elseBody){
                return ifBody;
            }
            return UnitType.getUnitType();
        }
        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTWhile astWhile) {
        Type cond = typeCheck(astWhile.condition, env);
        if (cond instanceof BoolType){
            return typeCheck(astWhile.body, env);
        }
        return NoneType.getNoneType();
    }

    public Type visit(ASTClosure astClosure){
        Env<Type> env0 = env.beginScope();
        for (int i = 0; i < astClosure.params.size(); i++){
            env0.bind(astClosure.params.get(i).identifier, astClosure.params.get(i).type);
        }
        Type t1 = typeCheck(astClosure.code, env0);
        env0.endScope();
        if(t1 instanceof NoneType)
            return t1;
        else
            return new FunType(astClosure.params.get(0).type,t1);
    }

    @Override
    public Type visit(ASTParameter astParameter) {
        Type idType = env.find(astParameter.identifier);
        Type paramType = astParameter.type;
        if (idType.equals(paramType)){
            return idType;
        }
        return NoneType.getNoneType();
    }


    //TODO: let x = fun (z:bool) -> if z then true else 2+2 end in if x(true) then 1+4 else 5 end
    //Type: None
    //Non computable.
    @Override
    public Type visit(ASTCall astCall) {
        Type funcType = typeCheck(astCall.identifier, env);
        if (funcType == typeCheck(astCall.identifier, env)) { //TODO: fix (is Fun(T0,TR)))
            Type eType = typeCheck(astCall.arguments, env);
            if (eType != eType) { //TODO: fix (!= T0)
                return NoneType.getNoneType();
            }
            return funcType;
        }
        return NoneType.getNoneType();
    }



    @Override
    public Type visit(ASTAssign astAssign) {
        // Type check the LHS expression E1
        Type lhsType = typeCheck(astAssign.getLhs(), env);

        // Type check the RHS expression E2
        Type rhsType = typeCheck(astAssign.getRhs(), env);

        // Ensure the LHS is a RefType
        if (lhsType instanceof RefType) {
            // Ensure the RHS type matches the type referenced by the LHS
            if (rhsType.equals(((RefType) lhsType).getReferencedType())) {
                // Return the RHS type as the result of the assignment
                return rhsType;
            } else {
                throw new RuntimeException("Type mismatch in assignment: " + rhsType + " cannot be assigned to " + lhsType);
            }
        } else {
            // LHS must be a reference type
            throw new RuntimeException("Left-hand side of assignment must be a reference type.");
        }

    }
}
