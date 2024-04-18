package ast;


public interface Exp {
    public interface Visitor<T> {
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
    }

    <T> T accept(Visitor<T> v);

}

