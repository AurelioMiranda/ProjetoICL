package typechecker;

import ast.*;
import ast.arithmetic.ASTAdd;
import ast.arithmetic.ASTDiv;
import ast.arithmetic.ASTMult;
import ast.arithmetic.ASTSub;
import ast.control_flow.ASTElse;
import ast.control_flow.ASTIf;
import ast.control_flow.ASTWhile;
import ast.print.ASTPrint;
import ast.print.ASTPrintln;
import ast.string.ASTConcat;
import ast.string.ASTString;
import ast.tuples.*;
import ast.identifiers.ASTIdentifier;
import ast.identifiers.ASTLet;
import ast.logical.*;
import ast.references.ASTAssign;
import ast.references.ASTDeref;
import ast.references.ASTNew;
import interpreter.Env;
import types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
        try {
            Type t = env.find(astIdentifier.getName());
        } catch (NullPointerException e) {
            return NoneType.getNoneType();
        }
        return env.find(astIdentifier.getName());
    }

    @Override
    public Type visit(ASTNew astNew) {
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
        }
    }

    @Override
    public Type visit(ASTPair astPair) {

        List<Type> types = new ArrayList<>();

        for (Exp tupleValue : astPair.tupleList) {

            Type t1 = typeCheck(tupleValue, env);

            if (t1 instanceof NoneType) {
                return NoneType.getNoneType();
            }
            types.add(t1);
        }
        return new TupleType(types);
    }

    @Override
    public Type visit(ASTFirst astFirst) {
        Type t1 = typeCheck(astFirst.tuple, env);

        if (t1 instanceof IntType || t1 instanceof BoolType || t1 instanceof RefType
                || t1 instanceof FunType || t1 instanceof TupleType) {
            return t1;
        }

        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTSecond astSecond) {
        Type t1 = typeCheck(astSecond.tuple, env);

        if (t1 instanceof IntType || t1 instanceof BoolType || t1 instanceof RefType
                || t1 instanceof FunType || t1 instanceof TupleType) {
            return t1;
        }

        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTLast astLast) {
        Type t1 = typeCheck(astLast.tuple, env);

        if (t1 instanceof IntType || t1 instanceof BoolType || t1 instanceof RefType
                || t1 instanceof FunType || t1 instanceof TupleType) {
            return t1;
        }

        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTMatch astMatch) {
        Type tupleType = env.find(astMatch.tupleId);

        if ((tupleType instanceof TupleType tuple)) {
            if (tuple.getElementTypes().size() == astMatch.tupleIds.size()) {
                env = env.beginScope();
                for (int i = 0; i < astMatch.tupleIds.size(); i++) {
                    String varName = astMatch.tupleIds.get(i);
                    Type varType = tuple.getElementTypes().get(i);
                    env.bind(varName, varType);
                }
                Type bodyType = typeCheck(astMatch.body, env);
                env = env.endScope();

                return bodyType;
            }
        }
        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTString astString) {
        return StringType.getStringType();
    }

    @Override
    public Type visit(ASTConcat astConcat) {

        Type t1 = typeCheck(astConcat.arg1, env);
        if (t1 instanceof StringType) {
            Type t2 = typeCheck(astConcat.arg2, env);
            if (t2 instanceof StringType s2) {
                return s2;
            }
        }

        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTPrint astPrint) {
        Type t2 = typeCheck(astPrint.exp, env);
        if (t2 instanceof NoneType) {
            return NoneType.getNoneType();
        }
        return UnitType.getUnitType();
    }

    @Override
    public Type visit(ASTPrintln astPrintln) {
        Type t2 = typeCheck(astPrintln.exp, env);
        if (t2 instanceof NoneType) {
            return NoneType.getNoneType();
        }
        return UnitType.getUnitType();
    }

    @Override
    public Type visit(ASTSeq astSeq) {
        typeCheck(astSeq.first, env);
        return typeCheck(astSeq.second, env);
    }

    @Override
    public Type visit(ASTLet astLet) {
        env = env.beginScope();
        for (Map.Entry<String, Exp> entry : astLet.variables.entrySet()) {
            String variableName = entry.getKey();
            Exp variableValue = entry.getValue();
            Type t1 = typeCheck(variableValue, env);
            env.bind(variableName, t1);
        }
        Type t = typeCheck(astLet.body, env);
        env = env.endScope();
        return t;
    }

    @Override
    public Type visit(ASTIf astIf) {
        Type cond = typeCheck(astIf.condition, env);
        if (cond instanceof BoolType) {
            return typeCheck(astIf.body, env);
        }
        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTElse astElse) {
        Type cond = typeCheck(astElse.condition, env);
        if (cond instanceof BoolType) {
            Type ifBody = typeCheck(astElse.ifBody, env);
            Type elseBody = typeCheck(astElse.elseBody, env);
            if (ifBody == elseBody) {
                return ifBody;
            }
            return NoneType.getNoneType();
        }
        return NoneType.getNoneType();
    }

    @Override
    public Type visit(ASTWhile astWhile) {
        Env<Type> oldEnv = env;
        Type t1 = typeCheck(astWhile.condition, env);
        if (!(t1 instanceof BoolType)) {
            env = oldEnv;
            return NoneType.getNoneType();
        }
        Type t2 = typeCheck(astWhile.body, env);
        env = oldEnv;
        if (t2 instanceof NoneType) {
            return NoneType.getNoneType();
        }
        return UnitType.getUnitType();
    }

    @Override
    public Type visit(ASTClosure astClosure) {
        Env<Type> env0 = env.beginScope();
        for (int i = 0; i < astClosure.params.size(); i++) {
            env0.bind(astClosure.params.get(i).identifier, astClosure.params.get(i).type);
        }
        Type t1 = typeCheck(astClosure.code, env0);
        env0.endScope();
        if (t1 instanceof NoneType)
            return t1;
        else
            return new FunType(astClosure.params.get(0).type, t1);
    }


    @Override
    public Type visit(ASTCall astCall) {
        Type funcType = typeCheck(astCall.identifier, env);
        if (funcType instanceof FunType funType) {
            Type eType = typeCheck(astCall.arguments, env);
            if (eType != funType.parameterType) {
                return NoneType.getNoneType();
            }
            return funType.type;
        }
        return NoneType.getNoneType();
    }


    @Override
    public Type visit(ASTAssign astAssign) {
        Type lhsType = typeCheck(astAssign.getLhs(), env);

        Type rhsType = typeCheck(astAssign.getRhs(), env);

        if (lhsType instanceof RefType) {
            if (rhsType.equals(((RefType) lhsType).getReferencedType())) {
                return rhsType;
            } else {
                throw new RuntimeException("Type mismatch in assignment: " + rhsType + " cannot be assigned to " + lhsType);
            }
        } else {
            throw new RuntimeException("Left-hand side of assignment must be a reference type.");
        }

    }
}
