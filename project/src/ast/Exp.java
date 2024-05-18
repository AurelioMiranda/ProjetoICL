package ast;


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

public interface Exp {
    public interface Visitor<T, E> {
        T visit(ASTAdd i);

        T visit(ASTDiv i);

        T visit(ASTInt i);

        T visit(ASTMult e);

        T visit(ASTSub e);

        T visit(ASTEq astEq);

        T visit(ASTAnd astAnd);

        T visit(ASTOr astOr);

        T visit(ASTGr astGr);

        T visit(ASTLt astLt);

        T visit(ASTGrOrEq astGrOrEq);

        T visit(ASTLTOrEq astltOrEq);

        T visit(ASTNEq astneq);

        T visit(ASTBool astbool);

        T visit(ASTNeg astNeg);

        T visit(ASTIdentifier astIdentifier);

        T visit(ASTNew astNew);

        T visit(ASTLet astLet);

        T visit(ASTIf astIf);

        T visit(ASTElse astElse);

        T visit(ASTWhile astWhile);

        T visit(ASTClosure astClosure);

        T visit(ASTParameter astParameter);

        T visit(ASTCall astCall);

        T visit(ASTAssign astAssign);
    }

    <T> T accept(Visitor<T, Env<T>> v);

}

